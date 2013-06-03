package ar.com.caeldev.bsacore.services.role

import ar.com.caeldev.bsacore.domain.Role
import ar.com.caeldev.bsacore.services.common.Service
import ar.com.caeldev.bsacore.services.validations.{ Rule }
import ar.com.caeldev.bsacore.services.common.rules.NotEmptyRule

class RoleService(implicit val mot: Manifest[Role]) extends Service[Role] {

  def add(entity: Role): Role = {
    validate(entity)
    dao.save(entity)
    dao.findById(entity.id)
  }

  def delete(id: Long) {
    val entityToDelete: Role = dao.findById(id)
    dao.remove(entityToDelete)
  }

  def update(entity: Role): Role = {
    validate(entity)
    val entityToDelete: Role = dao.findById(entity.id)
    dao.remove(entityToDelete)
    dao.save(entity)
    dao.findById(entity.id)
  }

  def get(id: Long): Role = {
    dao.findById(id)
  }

  def applyRulesUsing(entity: Role): List[Rule[_]] = {
    val stringRules = List(new Rule[String](List(entity.description, entity.id.toString), NotEmptyRule.get))
    stringRules
  }
}
