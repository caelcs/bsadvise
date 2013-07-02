package ar.com.caeldev.api

import akka.actor.ActorSystem
import akka.testkit.{ ImplicitSender, TestKit }
import ar.com.caeldev.bsacore.domain.{ ContactInfo, Member, Role }
import ar.com.caeldev.core.ActorOperations._
import ar.com.caeldev.core.ActorOperations.Add
import ar.com.caeldev.core.ActorOperations.Delete
import ar.com.caeldev.core.ActorOperations.GetAll
import ar.com.caeldev.core.ActorOperations.Update
import ar.com.caeldev.core.CoreActors
import org.joda.time.DateTime
import org.specs2.mutable.SpecificationLike
import akka.pattern.ask
import scala.concurrent.Await

class MemberServiceActorSpec extends TestKit(ActorSystem()) with ImplicitSender with SpecificationLike with CoreActors with CommonConcurrentFeature {

  sequential

  "MemberActor should" >> {

    "persist a new Member " in {
      val role1: Role = new Role(1220, "test1000")
      val resultRole = Await.result(roleActor ? Add(role1), timeout.duration)
      assert(resultRole === role1)

      val member1: Member = new Member(1001, 1220, "Dido", "Tramp", "adolfoecs@gmail.com", new ContactInfo("Fake St. 123"), new DateTime(), new DateTime())
      val resultMember = Await.result(memberActor ? Add(member1), timeout.duration)
      assert(resultMember === member1)

      Await.result(memberActor ? Delete(member1.id), timeout.duration)
      Await.result(roleActor ? Delete(role1.id), timeout.duration)

      success
    }

    "update an existing Member " in {
      val role1: Role = new Role(1221, "test1000")
      val resultRole = Await.result(roleActor ? Add(role1), timeout.duration)
      assert(resultRole === role1)

      val member1: Member = new Member(1002, 1221, "Dido", "Tramp", "adolfoecs@gmail.com", new ContactInfo("Fake St. 123"), new DateTime(), new DateTime())
      val resultMember = Await.result(memberActor ? Add(member1), timeout.duration)
      assert(resultMember === member1)

      val memberUpdated: Member = new Member(1002, 1221, "DidoLala", "TrampLala", "adolfoecs@gmail.com", new ContactInfo("Fake St. 123"), new DateTime(), new DateTime())
      val resultMemberUpdated = Await.result(memberActor ? Update(memberUpdated), timeout.duration)
      assert(resultMemberUpdated === memberUpdated)

      Await.result(memberActor ? Delete(member1.id), timeout.duration)
      Await.result(roleActor ? Delete(role1.id), timeout.duration)

      success
    }

    "get one Member persisted " in {
      val role1: Role = new Role(1222, "test1000")
      val resultRole = Await.result(roleActor ? Add(role1), timeout.duration)
      assert(resultRole === role1)

      val member1: Member = new Member(1102, 1222, "Dido", "Tramp", "adolfoecs@gmail.com", new ContactInfo("Fake St. 123"), new DateTime(), new DateTime())
      val resultMember = Await.result(memberActor ? Add(member1), timeout.duration)
      assert(resultMember === member1)

      val resultMember1 = Await.result(memberActor ? Get(member1.id), timeout.duration)
      assert(resultMember1 === member1)

      Await.result(memberActor ? Delete(member1.id), timeout.duration)
      Await.result(roleActor ? Delete(role1.id), timeout.duration)

      success
    }

    "get all the members persisted " in {
      val role1: Role = new Role(1223, "test1000")
      val resultRole = Await.result(roleActor ? Add(role1), timeout.duration)
      assert(resultRole === role1)

      val member1: Member = new Member(1103, 1223, "Dido", "Tramp", "adolfoecs@gmail.com", new ContactInfo("Fake St. 123"), new DateTime(), new DateTime())
      val resultMember = Await.result(memberActor ? Add(member1), timeout.duration)
      assert(resultMember === member1)

      val resultMember1 = Await.result(memberActor ? GetAll, timeout.duration)
      assert(resultMember1.asInstanceOf[List[Member]].find(x => x === member1).size === 1)

      Await.result(memberActor ? Delete(member1.id), timeout.duration)
      Await.result(roleActor ? Delete(role1.id), timeout.duration)

      success
    }
  }
}
