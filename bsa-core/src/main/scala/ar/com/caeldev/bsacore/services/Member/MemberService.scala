package ar.com.caeldev.bsacore.services.member

import ar.com.caeldev.bsacore.domain.{ Role, Member }
import ar.com.caeldev.bsacore.dao.{ MongoDaoImpl, GenericDao }
import ar.com.caeldev.bsacore.services.exceptions.ServiceException
import ar.com.caeldev.bsacore.services.common.Service
import ar.com.caeldev.bsacore.services.validations.Rule
import ar.com.caeldev.bsacore.services.common.rules.NotEmptyRule

class MemberService(implicit val mot: Manifest[Member]) extends Service[Member] {

  def add(entity: Member): Member = {
    dao.save(entity)
    dao.findById(entity.id)
  }

  def delete(id: Long) {
    val entityToDelete: Member = dao.findById(id)
    dao.remove(entityToDelete)
  }

  def update(entity: Member): Member = {
    val entityToDelete: Member = dao.findById(entity.id)
    dao.remove(entityToDelete)
    dao.save(entity)
    dao.findById(entity.id)
  }

  def get(id: Long): Member = {
    dao.findById(id)
  }

  def validate(entity: Member) = {
    val rule: Rule[String] = new Rule[String](List(entity.firstName, entity.lastName, entity.email), NotEmptyRule.rule)
    rule.validate()
  }
}
