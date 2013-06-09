package ar.com.caeldev.bsacore.domain

import org.joda.time.DateTime

case class Notification(
    id: Long,
    sender_id: Long,
    receivers: List[Long],
    message: String,
    createdAt: DateTime) {

  var sentAt: DateTime = _
  var status: String = _
}

object Status extends Enumeration {
  type Status = Value
  val draft = Value("draft")
  val sending = Value("sending")
  val sent = Value("sent")
}
