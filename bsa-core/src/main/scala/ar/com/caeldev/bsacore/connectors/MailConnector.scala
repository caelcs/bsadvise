package ar.com.caeldev.bsacore.connectors

import ar.com.caeldev.bsacore.commons.domain.{ Category, Error, Success }
import org.apache.commons.mail.{ EmailException, SimpleEmail, DefaultAuthenticator }
import ar.com.caeldev.bsacore.config.ConfigContext
import ar.com.caeldev.bsacore.services.{ GroupService, Service, MemberService }
import scala.util.control.Exception._
import ar.com.caeldev.bsacore.domain.Group
import ar.com.caeldev.bsacore.domain.Member
import ar.com.caeldev.bsacore.domain.Notification
import ar.com.caeldev.bsacore.connectors.exceptions.ConnectorException

class MailConnector extends Connector {

  val appConfigContext: ConfigContext = new ConfigContext("errors.conf")
  val catcher = catching(classOf[EmailException]).withApply(e => throw new ConnectorException(e))

  val smtpConfig: SmtpConfig = SmtpConfig.get()
  var status: Either[Success, Error] = _

  def send(notification: Notification): Either[Success, Error] = {
    val recipients: String = buildRecipients(notification.receivers_group_id)
    val sender: String = buildSender(notification.sender_id)
    val emailMessage: EmailMessage = new EmailMessage(notification.subject, recipients, sender, notification.message, smtpConfig)
    status = sendEmailSync(emailMessage)
    status
  }

  def lastStatus(): Either[Success, Error] = {
    status
  }

  private def buildSender(id: Long): String = {
    val memberService: Service[Member] = new MemberService()
    val member: Member = memberService.get(id)
    member.email
  }

  private def buildRecipients(receiver: Long): String = {
    val groupService: Service[Group] = new GroupService()
    val group: Group = groupService.get(receiver)

    var results = List.empty[String]
    val memberService: Service[Member] = new MemberService()

    group.members.collect {
      case id: Long =>
        val member: Member = memberService.get(id)
        results :+= member.email
    }

    results.mkString(",")
  }

  private def sendEmailSync(emailMessage: EmailMessage): Either[Success, Error] = {
    val result = catcher.either {
      val email = new SimpleEmail()
      email.setSSLOnConnect(emailMessage.smtpConfig.ssl)
      email.setSmtpPort(emailMessage.smtpConfig.port)
      email.setHostName(emailMessage.smtpConfig.host)
      email.setAuthenticator(new DefaultAuthenticator(
        emailMessage.smtpConfig.user,
        emailMessage.smtpConfig.password))
      email.setMsg(emailMessage.text)
        .addTo(emailMessage.recipient)
        .setFrom(emailMessage.from)
        .setSubject(emailMessage.subject)
      email.send()
    }
    result match {
      case Left(_)  => { Right(Error.create(1300, Category.connectors)) }
      case Right(_) => { Left(Success.create()) }
    }
  }
}

case class EmailMessage(
  subject: String,
  recipient: String,
  from: String,
  text: String,
  smtpConfig: SmtpConfig,
  var deliveryAttempts: Int = 0)

case class SmtpConfig(tls: Boolean = false,
                      ssl: Boolean = false,
                      port: Int = 25,
                      host: String,
                      user: String,
                      password: String)

object SmtpConfig {

  val config: ConfigContext = new ConfigContext("environments")
  val appConfigContext: ConfigContext = new ConfigContext("application")
  val env: String = appConfigContext.get("application.environment")

  val host: String = config.get("environments."+env+".mail.host")
  val port: Int = config.get("environments."+env+".mail.port").toInt
  val ssl: Boolean = config.get("environments."+env+".mail.ssl").toBoolean
  val tls: Boolean = config.get("environments."+env+".mail.tls").toBoolean
  val user: String = config.get("environments."+env+".mail.user")
  val password: String = config.get("environments."+env+".mail.password")

  val smtpConfig: SmtpConfig = new SmtpConfig(tls, ssl, port, host, user, password)

  def get(): SmtpConfig = {
    smtpConfig
  }

}

