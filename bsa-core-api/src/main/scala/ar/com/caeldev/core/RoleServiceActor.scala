package ar.com.caeldev.core

import akka.actor.Actor
import ar.com.caeldev.bsacore.services.{ Service, RoleService }
import ar.com.caeldev.core.ActorOperations._
import ar.com.caeldev.core.ActorOperations.Get
import ar.com.caeldev.core.ActorOperations.Add
import ar.com.caeldev.bsacore.domain.Role
import ar.com.caeldev.core.ActorOperations.Delete
import ar.com.caeldev.core.ActorOperations.Update

class RoleServiceActor extends Actor {

  val roleService: Service[Role] = new RoleService()

  def receive = {
    case Add(role: Role) =>
      roleService.add(role)
    case Update(role: Role) =>
      roleService.update(role)
    case Delete(id: Long) =>
      roleService.delete(id)
    case Get(field: String, id: Long) =>
      roleService.get(field, id)
    case GetAll =>
      roleService.getAll
  }

}

