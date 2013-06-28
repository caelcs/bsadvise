package ar.com.caeldev.core

import akka.actor.{ ActorLogging, Actor }
import ar.com.caeldev.core.ActorOperations.{ Get, Delete, Update, Add }
import ar.com.caeldev.bsacore.domain.Member
import ar.com.caeldev.bsacore.services.{ MemberService, Service }

class MemberServiceActor extends Actor with ActorLogging {

  val memberService: Service[Member] = new MemberService()

  def receive = {
    case Add(member: Member) =>
      log.debug("Add Service Actor")
      sender ! memberService.add(member)
    case Update(member: Member) =>
      log.debug("Update Service Actor")
      sender ! memberService.update(member)
    case Delete(id: Any) =>
      log.debug("Delete Service Actor")
      memberService.delete(id)
    case Get(id: Any) =>
      log.debug("Get Service Actor")
      sender ! memberService.get(id)
  }
}
