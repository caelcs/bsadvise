package ar.com.caeldev.bsacore.domain

import reactivemongo.bson.{BSONDocumentReader, BSONDocument, BSONDocumentWriter}

case class Role(
                 id: Option[Int],
                 description: String
                 )

object Role {

  implicit object RoleWriter extends BSONDocumentWriter[Role] {
    def write(role: Role): BSONDocument = BSONDocument(
      "id" -> role.id,
      "description" -> role.description
    )
  }

  implicit object RoleReader extends BSONDocumentReader[Role] {
    def read(doc: BSONDocument): Role = {
      Role(
        doc.getAs[Int]("id"),
        doc.getAs[String]("description").get
      )
    }
  }

}