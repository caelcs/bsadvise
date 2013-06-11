package ar.com.caeldev.bsacore.validations.rules

import ar.com.caeldev.bsacore.domain.Group
import ar.com.caeldev.bsacore.commons.domain.{ Success, Category, Error }
import ar.com.caeldev.bsacore.services.{ Service, GroupService }

object GroupExists {

  val get = { id: Long =>
    val serviceGroup: Service[Group] = new GroupService()

    serviceGroup.get(id) == null match {
      case true  => { Right(Error.create(1006, Category.rules)) }
      case false => { Left(Success.create()) }
    }
  }
}
