package ar.com.caeldev.bsacore.validations.rules

import ar.com.caeldev.bsacore.domain.Role
import ar.com.caeldev.bsacore.commons.domain.{ Category, Success, Error }
import ar.com.caeldev.bsacore.services.{ Service, RoleService }

object RoleExists {

  val get = { id: Long =>
    val service: Service[Role] = new RoleService()

    service.get(id) == null match {
      case true  => { Right(Error.create(1001, Category.rules)) }
      case false => { Left(Success.create()) }
    }
  }

}
