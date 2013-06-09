package ar.com.caeldev.bsacore.validations.rules

import ar.com.caeldev.bsacore.domain.Role
import ar.com.caeldev.bsacore.dao.{ MongoDaoImpl, GenericDao }
import ar.com.caeldev.bsacore.commons.domain.{ Category, Success, Error }

object RoleExists {

  val get = { id: Long =>
    var result: Either[Success, Error] = null
    val dao: GenericDao[Role] = new MongoDaoImpl[Role]()
    val resultRole: Role = dao.findById(id)

    resultRole == null match {
      case true  => { result = Right(Error.create(1001, Category.rules)) }
      case false => { result = Left(Success.create()) }
    }
    result
  }

}
