package ar.com.caeldev.bsacore.validation.rules

import ar.com.caeldev.bsacore.dao.{ MongoDaoImpl, GenericDao }
import ar.com.caeldev.bsacore.domain.Member
import ar.com.caeldev.bsacore.validation.Success
import ar.com.caeldev.bsacore.validation

object RoleWithMemberReferencesExits {

  val get = { id: Long =>
    var result: Either[Success, validation.Error] = null
    val dao: GenericDao[Member] = new MongoDaoImpl[Member]()
    val results: List[Member] = dao.findBy("role_id", id)

    results.size == 0 match {
      case true  => { result = Left(Success.create()) }
      case false => { result = Right(validation.Error.create(1002)) }
    }
    result
  }
}