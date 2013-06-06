package ar.com.caeldev.bsacore.services.member

import ar.com.caeldev.bsacore.domain.{ Member }
import ar.com.caeldev.bsacore.services.common.{ Operation, Service }
import ar.com.caeldev.bsacore.services.validations.Rule
import ar.com.caeldev.bsacore.services.common.rules.NotEmpty
import ar.com.caeldev.bsacore.services.role.rules.RoleExists
import scala.util.control.Exception.catching
import ar.com.caeldev.bsacore.services.exceptions.{ ServiceException, ValidationException }
import ar.com.caeldev.bsacore.config.ConfigContext

class MemberService(implicit val mot: Manifest[Member]) extends Service[Member] {

  def add(entity: Member): Member = {
    validate(entity, Operation.add.toString)
    dao.save(entity)
    dao.findById(entity.id)
  }

  def delete(id: Long) {
    val entityToDelete: Member = dao.findById(id)
    validate(entityToDelete, Operation.delete.toString)
    dao.remove(entityToDelete)
  }

  def update(entity: Member): Member = {
    validate(entity, Operation.update.toString)
    val entityToDelete: Member = dao.findById(entity.id)
    dao.remove(entityToDelete)
    dao.save(entity)
    dao.findById(entity.id)
  }

  def get(id: Long): Member = {
    dao.findById(id)
  }

  def applyRulesFor(entity: Member, operation: String): List[Rule[_]] = {
    MemberRules.get(entity, operation)
  }
}

object MemberRules {

  val appConfigContext: ConfigContext = new ConfigContext("errors.conf")
  val operationCatch = catching(classOf[NoSuchElementException]).withApply(e => throw new ServiceException(appConfigContext.get("errors.services.1100.description")))

  def get(entity: Member, operation: String): List[Rule[_]] = {
    var results: List[Rule[_]] = Nil
    operationCatch {
      Operation.withName(operation) match {
        case Operation.add => {
          results = List(
            new Rule[String](List(entity.id.toString, entity.role_id.toString, entity.firstName, entity.lastName, entity.email), NotEmpty.get),
            new Rule[Long](entity.role_id, RoleExists.get))
        }
        case Operation.update => {
          results = List(
            new Rule[String](List(entity.id.toString, entity.role_id.toString, entity.firstName, entity.lastName, entity.email), NotEmpty.get),
            new Rule[Long](entity.role_id, RoleExists.get))
        }
        case Operation.delete => { results = List() }
      }
    }
    results
  }
}