package ar.com.caeldev.bsacore.services

import scala.util.control.Exception._
import ar.com.caeldev.bsacore.domain.Group
import ar.com.caeldev.bsacore.services.exceptions.ServiceException
import ar.com.caeldev.bsacore.config.ConfigContext
import ar.com.caeldev.bsacore.validations.rules.{ MemberExists, NotEmpty }
import ar.com.caeldev.bsacore.validations.{ Operation, Validation, Rule }

class GroupService(implicit val mot: Manifest[Group]) extends Service[Group] with Validation[Group] {

  def add(entity: Group): Group = {
    validate(entity, Operation.add)
    dao.save(entity)
    dao.findById(entity.id)
  }

  def delete(id: Any) {
    val entityToDelete: Group = dao.findById(id)
    validate(entityToDelete, Operation.delete)

    val memberService: MemberService = new MemberService()

    entityToDelete.members.foreach { member_id =>
      memberService.delete(member_id)
    }
    catcher {
      dao.remove(entityToDelete)
    }
  }

  def update(entity: Group): Group = {
    validate(entity, Operation.update)
    val entityToDelete: Group = dao.findById(entity.id)
    dao.remove(entityToDelete)
    dao.save(entity)
    dao.findById(entity.id)
  }

  def get(id: Any): Group = {
    dao.findById(id)
  }

  def applyRulesFor(entity: Group, operation: Operation.Value): List[Rule[_]] = {
    GroupRules.get(entity, operation)
  }
}

object GroupRules {

  def get(entity: Group, operation: Operation.Value): List[Rule[_]] = {
    var results: List[Rule[_]] = Nil
    operation match {
      case Operation.add => {
        results = List(
          new Rule[String](List(entity.id.toString, entity.name), NotEmpty.get),
          new Rule[Long](entity.members, MemberExists.get))

      }
      case Operation.update => {
        results = List(
          new Rule[String](List(entity.id.toString, entity.name), NotEmpty.get),
          new Rule[Long](entity.members, MemberExists.get))
      }
      case Operation.delete => { results = List() }
    }
    results
  }
}