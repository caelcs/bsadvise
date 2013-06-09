package ar.com.caeldev.bsacore.validation.rules

import ar.com.caeldev.bsacore.domain.Role
import ar.com.caeldev.bsacore.dao.{ MongoDaoImpl, GenericDao }
import ar.com.caeldev.bsacore.validation.Success
import ar.com.caeldev.bsacore.validation

object RoleExists {

  val get = { id: Long =>
    var result: Either[Success, validation.Error] = null
    val dao: GenericDao[Role] = new MongoDaoImpl[Role]()
    val resultRole: Role = dao.findById(id)

    resultRole == null match {
      case true  => { result = Right(validation.Error.create(1001)) }
      case false => { result = Left(Success.create()) }
    }
    result
  }

}
