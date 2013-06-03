package ar.com.caeldev.bsacore.services.role.rules

import ar.com.caeldev.bsacore.services.validations.{ Error, Success }
import ar.com.caeldev.bsacore.domain.Role
import ar.com.caeldev.bsacore.dao.{ MongoDaoImpl, GenericDao }

object RoleExists {

  val get = { id: Long =>
    var result: Either[Success, Error] = null
    val dao: GenericDao[Role] = new MongoDaoImpl[Role]()
    val resultRole: Role = dao.findById(id)

    resultRole == null match {
      case true  => { result = Right(Error.create(1001)) }
      case false => { result = Left(Success.create()) }
    }
    result
  }

}
