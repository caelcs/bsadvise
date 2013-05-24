package ar.com.caeldev.bsacore.domain

import org.joda.time.DateTime

case class MessageTemplate(
  id: Long,
  message: String,
  createdBy: Member,
  createdAt: DateTime)
