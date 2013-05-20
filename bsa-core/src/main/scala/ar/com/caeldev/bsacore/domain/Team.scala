package ar.com.caeldev.bsacore.domain

case class Team(
                 id: Long,
                 name: String,
                 members: List[Member]
                 )