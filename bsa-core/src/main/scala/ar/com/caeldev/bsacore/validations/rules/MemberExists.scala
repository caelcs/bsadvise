package ar.com.caeldev.bsacore.validations.rules

import ar.com.caeldev.bsacore.dao.{ MongoDaoImpl, GenericDao }
import ar.com.caeldev.bsacore.domain.Member
import ar.com.caeldev.bsacore.commons.domain.{ Category, Success, Error }

object MemberExists {

  val get = { id: Long =>
    var result: Either[Success, Error] = null
    val dao: GenericDao[Member] = new MongoDaoImpl[Member]()
    val resultMember: Member = dao.findById(id)

    resultMember == null match {
      case true  => { result = Right(Error.create(1003, Category.rules)) }
      case false => { result = Left(Success.create()) }
    }
    result
  }
}
