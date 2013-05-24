package ar.com.caeldev.bsacore.domain

import org.joda.time.DateTime

case class Notification(
  id: Long,
  sender: Member,
  receivers: List[Member],
  message: String,
  createdAt: DateTime,
  sentAt: DateTime)
