package ar.com.caeldev.core

import akka.actor.{ ActorLogging, Actor }
import ar.com.caeldev.bsacore.services.{ Service, RoleService }
import ar.com.caeldev.core.ActorOperations._
import ar.com.caeldev.core.ActorOperations.Get
import ar.com.caeldev.core.ActorOperations.Add
import ar.com.caeldev.bsacore.domain.Role
import ar.com.caeldev.core.ActorOperations.Delete
import ar.com.caeldev.core.ActorOperations.Update

class RoleServiceActor extends Actor with ActorLogging {

  val roleService: Service[Role] = new RoleService()

  def receive = {
    case Add(role: Role) =>
      log.debug("Role Actor Add Method")
      sender ! roleService.add(role)
    case Update(role: Role) =>
      log.debug("Role Actor Update Method")
      sender ! roleService.update(role)
    case Delete(id: Long) =>
      log.debug("Role Actor Delete Method")
      roleService.delete(id)
    case Get(id: Long) =>
      log.debug("Role Actor Get Method")
      sender ! roleService.get(id)
    case GetAll =>
      log.debug("Role Actor GetAll Method")
      sender ! roleService.getAll
  }

}

