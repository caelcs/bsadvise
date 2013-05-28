package ar.com.caeldev.bsacore.domain

import org.joda.time.DateTime

case class Notification(
  id: Long,
  sender_id: Long,
  receivers: List[Long],
  message: String,
  createdAt: DateTime,
  sentAt: DateTime)
