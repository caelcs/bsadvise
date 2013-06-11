package ar.com.caeldev.bsacore.validations.rules

import ar.com.caeldev.bsacore.domain.Member
import ar.com.caeldev.bsacore.commons.domain.{ Category, Success, Error }
import ar.com.caeldev.bsacore.services.{ Service, MemberService }

object RoleWithMemberReferencesExits {

  val get = { id: Long =>
    val service: Service[Member] = new MemberService()

    service.get("role_id", id).size == 0 match {
      case true  => { Left(Success.create()) }
      case false => { Right(Error.create(1002, Category.rules)) }
    }
  }
}
