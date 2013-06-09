package ar.com.caeldev.bsacore.validation.rules

import ar.com.caeldev.bsacore.dao.{ MongoDaoImpl, GenericDao }
import ar.com.caeldev.bsacore.domain.Member
import ar.com.caeldev.bsacore.validation.Success
import ar.com.caeldev.bsacore.validation

object MemberExists {

  val get = { id: Long =>
    var result: Either[Success, validation.Error] = null
    val dao: GenericDao[Member] = new MongoDaoImpl[Member]()
    val resultMember: Member = dao.findById(id)

    resultMember == null match {
      case true  => { result = Right(validation.Error.create(1003)) }
      case false => { result = Left(Success.create()) }
    }
    result
  }
}
