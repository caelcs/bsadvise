package ar.com.caeldev.core

import akka.actor.Actor
import ar.com.caeldev.core.ActorOperations.{ Get, Delete, Update, Add }
import ar.com.caeldev.bsacore.domain.Member
import ar.com.caeldev.bsacore.services.{ MemberService, Service }

class MemberServiceActor extends Actor {

  val memberService: Service[Member] = new MemberService()

  def receive = {
    case Add(member: Member) =>
      memberService.add(member)
    case Update(member: Member) =>
      memberService.update(member)
    case Delete(id: Long) =>
      memberService.delete(id)
    case Get(field: String, id: Long) =>
      memberService.get(field, id)
  }
}
