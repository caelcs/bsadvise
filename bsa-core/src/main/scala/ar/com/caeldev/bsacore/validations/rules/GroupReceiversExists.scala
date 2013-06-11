package ar.com.caeldev.bsacore.validations.rules

import ar.com.caeldev.bsacore.services.{ GroupService, Service }
import ar.com.caeldev.bsacore.domain.Group
import ar.com.caeldev.bsacore.commons.domain.{ Success, Category, Error }

object GroupReceiversExists {

  val get = { id: Long =>
    val serviceGroup: Service[Group] = new GroupService()
    val group: Group = serviceGroup.get(id)

    ListNotEmpty.get.apply(group.members) match {
      case Right(_) => { Right(Error.create(1007, Category.rules)) }
      case Left(_)  => { Left(Success.create()) }
    }
  }

}
