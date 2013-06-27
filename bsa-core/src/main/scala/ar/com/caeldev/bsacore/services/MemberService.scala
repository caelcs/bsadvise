package ar.com.caeldev.bsacore.services

import ar.com.caeldev.bsacore.domain.Member
import ar.com.caeldev.bsacore.validations.{ Operation, Rule, Validation }
import ar.com.caeldev.bsacore.validations.rules.{ RoleExists, NotEmpty }
import org.slf4j.LoggerFactory

class MemberService(implicit val mot: Manifest[Member]) extends Service[Member] with Validation[Member] {

  def add(entity: Member): Member = {
    logger.info("Enter Add method")
    validate(entity, Operation.add)
    dao.save(entity)
    dao.findById(entity.id)
  }

  def delete(id: Any) {
    logger.info("Enter Delete method")
    val entityToDelete: Member = dao.findById(id)
    validate(entityToDelete, Operation.delete)
    catcher {
      dao.remove(entityToDelete)
    }
  }

  def update(entity: Member): Member = {
    logger.info("Enter Update method")
    validate(entity, Operation.update)
    val entityToDelete: Member = dao.findById(entity.id)
    dao.remove(entityToDelete)
    dao.save(entity)
    dao.findById(entity.id)
  }

  def get(id: Any): Member = {
    logger.info("Enter Get method")
    dao.findById(id)
  }

  def applyRulesFor(entity: Member, operation: Operation.Value): List[Rule[_]] = {
    MemberRules.get(entity, operation)
  }
}

object MemberRules {

  def get(entity: Member, operation: Operation.Value): List[Rule[_]] = {
    operation match {
      case Operation.add => {
        List(
          new Rule[String](List(entity.id.toString, entity.role_id.toString, entity.firstName, entity.lastName, entity.email), NotEmpty.get),
          new Rule[Long](entity.role_id, RoleExists.get))
      }
      case Operation.update => {
        List(
          new Rule[String](List(entity.id.toString, entity.role_id.toString, entity.firstName, entity.lastName, entity.email), NotEmpty.get),
          new Rule[Long](entity.role_id, RoleExists.get))
      }
      case Operation.delete => { List.empty }
    }
  }
}