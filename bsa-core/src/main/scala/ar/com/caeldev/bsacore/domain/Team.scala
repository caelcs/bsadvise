package ar.com.caeldev.bsacore.domain

import reactivemongo.bson.{BSONDocumentReader, BSONDocument, BSONDocumentWriter}

case class Team(
                 id: Option[Int],
                 name: String,
                 members: List[Member]
                 )

object Team {

  implicit object TeamWriter extends BSONDocumentWriter[Team] {
    def write(team: Team): BSONDocument = BSONDocument(
      "id" -> team.id,
      "name" -> team.name,
      "members" -> team.members
    )
  }

  implicit object teamReader extends BSONDocumentReader[Team] {
    def read(doc: BSONDocument): Team = {
      Team(
        doc.getAs[Int]("id"),
        doc.getAs[String]("name").get,
        doc.getAs[List[Member]]("members").get
      )
    }
  }

}