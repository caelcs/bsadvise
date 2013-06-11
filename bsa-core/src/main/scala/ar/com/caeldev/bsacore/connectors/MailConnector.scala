package ar.com.caeldev.bsacore.connectors

import ar.com.caeldev.bsacore.commons.domain.{ Category, Error, Success }
import org.apache.commons.mail.{ SimpleEmail, DefaultAuthenticator }
import ar.com.caeldev.bsacore.config.ConfigContext
import ar.com.caeldev.bsacore.services.{ Service, MemberService }
import ar.com.caeldev.bsacore.domain.Member
import ar.com.caeldev.bsacore.domain.Notification

class MailConnector extends Connector {

  val smtpConfig: SmtpConfig = SmtpConfig.get()

  def connect(notification: Notification): Either[Success, Error] = {

    val recipients: String = buildRecipients(notification.receivers)
    val sender: String = buildSender(notification.sender_id)
    val emailMessage: EmailMessage = new EmailMessage(notification.subject, recipients, sender, notification.message, "", smtpConfig)
    val resultSent = sendEmailSync(emailMessage)

    resultSent != null && !resultSent.isEmpty match {
      case false => { Right(Error.create(1202, Category.commons)) }
      case true  => { Left(Success.create()) }
    }
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

  private def sendEmailSync(emailMessage: EmailMessage): String = {

    // Create the email message
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
    val result: String = email.send()
    result
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

