package ar.com.caeldev.bsacore.connectors

import ar.com.caeldev.bsacore.commons.domain.{ Error, Success }
import ar.com.caeldev.bsacore.domain.Notification

trait NotifyUsingConnectors {

  val connectors: List[Connector] = List(new MailConnector())

  def notify(notification: Notification): List[Either[Success, Error]] = {
    send(notification)
    getFailedStatusFromConnectors
  }

  private def send(notification: Notification) {
    connectors.foreach { connector =>
      connector.send(notification)
    }
  }

  private def getFailedStatusFromConnectors: List[Either[Success, Error]] = {
    val status = connectors.partition((connector) => connector.lastStatus().isLeft)
    var errors: List[Either[Success, Error]] = List.empty
    status._2.foreach { connector =>
      errors = errors ::: List[Either[Success, Error]](connector.lastStatus())
    }
    errors
  }

}
