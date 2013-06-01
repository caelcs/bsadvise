package ar.com.caeldev.bsacore.services

import ar.com.caeldev.bsacore.domain.{ Role, Member }
import ar.com.caeldev.bsacore.dao.{ MongoDaoImpl, GenericDao }
import ar.com.caeldev.bsacore.services.exceptions.ServiceException

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
    var isValid: Boolean = true
    isValid = !entity.firstName.isEmpty
    isValid = !entity.lastName.isEmpty
    isValid = entity.role_id != null
    val daoRole: GenericDao[Role] = new MongoDaoImpl[Role]()
    val role: Role = daoRole.findById(entity.role_id)
    isValid = role != null

    if (!isValid) {
      throw new ServiceException("Validation Error.")
    }
  }
}
