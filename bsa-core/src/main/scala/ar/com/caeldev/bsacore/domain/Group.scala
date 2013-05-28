package ar.com.caeldev.bsacore.domain

case class Group(
  id: Long,
  name: String,
  members: List[Long])