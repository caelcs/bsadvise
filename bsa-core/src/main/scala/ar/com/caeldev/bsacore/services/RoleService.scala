package ar.com.caeldev.bsacore.services

import ar.com.caeldev.bsacore.domain.Role
import ar.com.caeldev.bsacore.validations.rules.{ RoleWithMemberReferencesExits, NotEmpty }
import ar.com.caeldev.bsacore.validations.{ Operation, Validation, Rule }

class RoleService(implicit val mot: Manifest[Role]) extends Service[Role] with Validation[Role] {

  def add(entity: Role): Role = {
    validate(entity, Operation.add)
    dao.save(entity)
    dao.findById(entity.id)
  }

  def delete(id: Any) {
    val entityToDelete: Role = dao.findById(id)
    validate(entityToDelete, Operation.delete)
    catcher {
      dao.remove(entityToDelete)
    }
  }

  def update(entity: Role): Role = {
    validate(entity, Operation.update)
    val entityToDelete: Role = dao.findById(entity.id)
    dao.remove(entityToDelete)
    dao.save(entity)
    dao.findById(entity.id)
  }

  def get(id: Any): Role = {
    dao.findById(id)
  }

  def applyRulesFor(entity: Role, operation: Operation.Value): List[Rule[_]] = {
    RoleRules.get(entity, operation)
  }
}

object RoleRules {

  def get(entity: Role, operation: Operation.Value): List[Rule[_]] = {
    operation match {
      case Operation.add => {
        List(
          new Rule[String](List(entity.description, entity.id.toString), NotEmpty.get))
      }
      case Operation.update => {
        List(
          new Rule[String](List(entity.description, entity.id.toString), NotEmpty.get))
      }
      case Operation.delete => {
        List(
          new Rule[Long](entity.id, RoleWithMemberReferencesExits.get))
      }
    }
  }

}
