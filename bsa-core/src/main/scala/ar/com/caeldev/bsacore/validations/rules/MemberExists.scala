package ar.com.caeldev.bsacore.validations.rules

import ar.com.caeldev.bsacore.dao.{ MongoDaoImpl, GenericDao }
import ar.com.caeldev.bsacore.domain.Member
import ar.com.caeldev.bsacore.commons.domain.{ Category, Success, Error }

object MemberExists {

  val get = { id: Long =>
    val dao: GenericDao[Member] = new MongoDaoImpl[Member]()
    val resultMember: Member = dao.findById(id)

    resultMember == null match {
      case true  => { Right(Error.create(1003, Category.rules)) }
      case false => { Left(Success.create()) }
    }
  }
}
