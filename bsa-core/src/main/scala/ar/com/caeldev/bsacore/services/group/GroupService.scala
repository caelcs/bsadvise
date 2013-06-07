package ar.com.caeldev.bsacore.services.group

import ar.com.caeldev.bsacore.services.common.{ Operation, Service }
import ar.com.caeldev.bsacore.services.validations.Rule
import ar.com.caeldev.bsacore.services.common.rules.NotEmpty
import scala.util.control.Exception._
import ar.com.caeldev.bsacore.domain.Group
import ar.com.caeldev.bsacore.services.exceptions.ServiceException
import ar.com.caeldev.bsacore.config.ConfigContext

class GroupService(implicit val mot: Manifest[Group]) extends Service[Group] {

  def add(entity: Group): Group = {
    validate(entity, Operation.add.toString)
    dao.save(entity)
    dao.findById(entity.id)
  }

  def delete(id: Any) {
    val entityToDelete: Group = dao.findById(id)
    validate(entityToDelete, Operation.delete.toString)
    dao.remove(entityToDelete)
  }

  def update(entity: Group): Group = {
    validate(entity, Operation.update.toString)
    val entityToDelete: Group = dao.findById(entity.id)
    dao.remove(entityToDelete)
    dao.save(entity)
    dao.findById(entity.id)
  }

  def get(id: Any): Group = {
    dao.findById(id)
  }

  def applyRulesFor(entity: Group, operation: String): List[Rule[_]] = {
    GroupRules.get(entity, operation)
  }
}

object GroupRules {

  val appConfigContext: ConfigContext = new ConfigContext("errors.conf")
  val operationCatch = catching(classOf[NoSuchElementException]).withApply(e => throw new ServiceException(appConfigContext.get("errors.services.1100.description")))

  def get(entity: Group, operation: String): List[Rule[_]] = {
    var results: List[Rule[_]] = Nil
    operationCatch {
      Operation.withName(operation) match {
        case Operation.add => {
          results = List(
            new Rule[String](List(entity.id.toString, entity.name), NotEmpty.get))
        }
        case Operation.update => {
          results = List(
            new Rule[String](List(entity.id.toString, entity.name), NotEmpty.get))
        }
        case Operation.delete => { results = List() }
      }
    }
    results
  }

}