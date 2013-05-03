package ar.com.caeldev.bsacore.domain

import reactivemongo.bson.{BSONDateTime, BSONDocumentReader, BSONDocument, BSONDocumentWriter}
import org.joda.time.DateTime

case class ContactInfo(address: Option[String])

object ContactInfo {

  implicit object ContactInfoWriter extends BSONDocumentWriter[ContactInfo] {
    def write(contactInfo: ContactInfo): BSONDocument = BSONDocument(
      "address" -> contactInfo.address)
  }

  implicit object ContactInfoReader extends BSONDocumentReader[ContactInfo] {
    def read(doc: BSONDocument): ContactInfo = {
      ContactInfo(
        doc.getAs[String]("address")
      )
    }
  }

}

case class Member(
                   id: Option[Int],
                   role: Role,
                   firstName: String,
                   lastName: String,
                   email: String,
                   contactInfo: Option[ContactInfo],
                   creationDate: Option[DateTime],
                   updateDate: Option[DateTime]
                   )

object Member {

  implicit object MemberWriter extends BSONDocumentWriter[Member] {
    def write(member: Member): BSONDocument = BSONDocument(
      "id" -> member.id,
      "role" -> member.role,
      "firstName" -> member.firstName,
      "lastName" -> member.lastName,
      "email" -> member.email,
      "contactInfo" -> member.contactInfo,
      "creationDate" -> member.creationDate.map(date => BSONDateTime(date.getMillis)),
      "updateDate" -> member.updateDate.map(date => BSONDateTime(date.getMillis)))
  }

  implicit object MemberReader extends BSONDocumentReader[Member] {
    def read(doc: BSONDocument): Member = {
      Member(
        doc.getAs[Int]("id"),
        doc.getAs[Role]("role").get,
        doc.getAs[String]("firstName").get,
        doc.getAs[String]("lastName").get,
        doc.getAs[String]("email").get,
        doc.getAs[ContactInfo]("contactInfo"),
        doc.getAs[BSONDateTime]("creationDate").map(dt => new DateTime(dt.value)),
        doc.getAs[BSONDateTime]("updateDate").map(dt => new DateTime(dt.value))
      )
    }
  }

}