package ar.com.caeldev.bsacore.domain

import reactivemongo.bson.{BSONDateTime, BSONDocumentReader, BSONDocument, BSONDocumentWriter}
import org.joda.time.DateTime

case class Notification(
                         id: Option[Int],
                         sender: Member,
                         receivers: List[Member],
                         message: String,
                         createdAt: Option[DateTime],
                         sentAt: Option[DateTime]
                         )

object Notification {

  implicit object NotificationWriter extends BSONDocumentWriter[Notification] {
    def write(notification: Notification): BSONDocument = BSONDocument(
      "id" -> notification.id,
      "sender" -> notification.sender,
      "receivers" -> notification.receivers,
      "message" -> notification.message,
      "createdAt" -> notification.createdAt.map(date => BSONDateTime(date.getMillis)),
      "sentAt" -> notification.sentAt.map(date => BSONDateTime(date.getMillis))
    )
  }

  implicit object notificationReader extends BSONDocumentReader[Notification] {
    def read(doc: BSONDocument): Notification = {
      Notification(
        doc.getAs[Int]("id"),
        doc.getAs[Member]("sender").get,
        doc.getAs[List[Member]]("receivers").get,
        doc.getAs[String]("message").get,
        doc.getAs[BSONDateTime]("createdAt").map(dt => new DateTime(dt.value)),
        doc.getAs[BSONDateTime]("sentAt").map(dt => new DateTime(dt.value))
      )
    }
  }

}
