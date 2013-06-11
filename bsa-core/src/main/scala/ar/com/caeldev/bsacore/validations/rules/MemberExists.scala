package ar.com.caeldev.bsacore.validations.rules

import ar.com.caeldev.bsacore.domain.Member
import ar.com.caeldev.bsacore.commons.domain.{ Category, Success, Error }
import ar.com.caeldev.bsacore.services.{ Service, MemberService }

object MemberExists {

  val get = { id: Long =>
    val service: Service[Member] = new MemberService()

    service.get(id) == null match {
      case true  => { Right(Error.create(1003, Category.rules)) }
      case false => { Left(Success.create()) }
    }
  }
}
