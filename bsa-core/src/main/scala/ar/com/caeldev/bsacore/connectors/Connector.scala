package ar.com.caeldev.bsacore.connectors

import ar.com.caeldev.bsacore.domain.Notification
import ar.com.caeldev.bsacore.commons.domain.{ Success, Error }

trait Connector {

  def connect(notification: Notification): Either[Success, Error]

}

