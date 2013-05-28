package ar.com.caeldev.bsacore.domain

import org.joda.time.DateTime

case class ContactInfo(address: String)

case class Member(
  id: Long,
  role_id: Long,
  firstName: String,
  lastName: String,
  email: String,
  contactInfo: ContactInfo,
  creationDate: DateTime,
  updateDate: DateTime)