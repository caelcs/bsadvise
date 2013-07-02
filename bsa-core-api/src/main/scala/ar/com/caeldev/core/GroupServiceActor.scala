package ar.com.caeldev.core

import akka.actor.{ ActorLogging, Actor }
import ar.com.caeldev.core.ActorOperations._
import ar.com.caeldev.bsacore.services.{ GroupService, Service }
import ar.com.caeldev.core.ActorOperations.Get
import ar.com.caeldev.core.ActorOperations.Add
import ar.com.caeldev.bsacore.domain.Group
import ar.com.caeldev.core.ActorOperations.Delete
import ar.com.caeldev.core.ActorOperations.Update

class GroupServiceActor extends Actor with ActorLogging {

  val groupService: Service[Group] = new GroupService()

  def receive = {
    case Add(group: Group) =>
      log.debug("Add Service Actor")
      sender ! groupService.add(group)
    case Update(group: Group) =>
      log.debug("Update Service Actor")
      sender ! groupService.update(group)
    case Delete(id: Any) =>
      log.debug("Delete Service Actor")
      sender ! groupService.delete(id)
    case Get(id: Any) =>
      log.debug("Get Service Actor")
      sender ! groupService.get(id)
    case GetAll =>
      log.debug("GetAll Service Actor")
      sender ! groupService.getAll
  }
}
