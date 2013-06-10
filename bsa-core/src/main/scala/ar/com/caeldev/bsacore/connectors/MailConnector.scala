package ar.com.caeldev.bsacore.connectors

import ar.com.caeldev.bsacore.commons.domain.{ Category, Error, Success }
import org.apache.commons.mail.{ HtmlEmail, DefaultAuthenticator }
import ar.com.caeldev.bsacore.config.ConfigContext
import ar.com.caeldev.bsacore.services.{ Service, MemberService }
import scala.util.control.Exception._
import ar.com.caeldev.bsacore.domain.Member
import ar.com.caeldev.bsacore.domain.Notification

class MailConnector extends Connector {

  val smtpConfig: SmtpConfig = SmtpConfig.get()
  val catcher = catching(classOf[Exception]).withApply(e => e)

  def connect(notification: Notification): Either[Success, Error] = {
    var result: Either[Success, Error] = null
    var catcherResponse = catcher.either {
      val recipients: String = buildRecipients(notification.receivers)
      val sender: String = buildSender(notification.sender_id)

      val emailMessage: EmailMessage = new EmailMessage(notification.subject, recipients, sender, notification.message, "", smtpConfig)
      sendEmailSync(emailMessage)
    }

    catcherResponse match {
      case Right(_) => { result = Right(Error.create(1202, Category.commons)) }
      case Left(_)  => { result = Left(Success.create()) }
    }
    result
  }

  def buildSender(id: Long): String = {
    val memberService: Service[Member] = new MemberService()
    val member: Member = memberService.get(id)
    member.email
  }

  def buildRecipients(receivers: List[Long]): String = {
    var results = List.empty[String]
    val memberService: Service[Member] = new MemberService()

    receivers.collect {
      case id: Long =>
        val member: Member = memberService.get(id)
        results :+= member.email
    }

    results.mkString(",")
  }

  private def sendEmailSync(emailMessage: EmailMessage) {

    // Create the email message
    val email = new HtmlEmail()
    email.setStartTLSEnabled(emailMessage.smtpConfig.tls)
    email.setSSLOnConnect(emailMessage.smtpConfig.ssl)
    email.setSmtpPort(emailMessage.smtpConfig.port)
    email.setHostName(emailMessage.smtpConfig.host)
    email.setAuthenticator(new DefaultAuthenticator(
      emailMessage.smtpConfig.user,
      emailMessage.smtpConfig.password))
    email.setHtmlMsg(emailMessage.html)
      .setTextMsg(emailMessage.text)
      .addTo(emailMessage.recipient)
      .setFrom(emailMessage.from)
      .setSubject(emailMessage.subject)
      .send()
  }
}

case class EmailMessage(
  subject: String,
  recipient: String,
  from: String,
  text: String,
  html: String,
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
  val appConfigContext: ConfigContext = new ConfigContext("app")
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

