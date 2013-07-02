package ar.com.caeldev.core

import akka.actor.{ ActorLogging, Actor }
import ar.com.caeldev.core.ActorOperations._
import ar.com.caeldev.bsacore.services.{ NotificationService, Service }
import ar.com.caeldev.core.ActorOperations.Get
import ar.com.caeldev.core.ActorOperations.Add
import ar.com.caeldev.bsacore.domain.Notification
import ar.com.caeldev.core.ActorOperations.Delete

class NotificationServiceActor extends Actor with ActorLogging {

  val notificationService: Service[Notification] = new NotificationService()

  def receive = {
    case Add(notification: Notification) =>
      log.debug("Add Service Actor")
      sender ! notificationService.add(notification)
    case Delete(id: Any) =>
      log.debug("Delete Service Actor")
      sender ! notificationService.delete(id)
    case Get(id: Any) =>
      log.debug("Get Service Actor")
      sender ! notificationService.get(id)
    case GetAll =>
      log.debug("GetAll Service Actor")
      sender ! notificationService.getAll
  }
}
