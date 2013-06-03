package ar.com.caeldev.bsacore.services.member

import ar.com.caeldev.bsacore.domain.{ Role, Member }
import ar.com.caeldev.bsacore.dao.{ MongoDaoImpl, GenericDao }
import ar.com.caeldev.bsacore.services.exceptions.ServiceException
import ar.com.caeldev.bsacore.services.common.Service

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

  }
}
