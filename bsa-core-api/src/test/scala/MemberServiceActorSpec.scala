import akka.actor.ActorSystem
import akka.testkit.{ ImplicitSender, TestKit }
import ar.com.caeldev.api.CommonConcurrentFeature
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

class MemberServiceActorSpec extends TestKit(ActorSystem()) with ImplicitSender with SpecificationLike with CoreActors with CommonConcurrentFeature {

  sequential

  "MemberActor should" >> {

    "persist a new Member " in {
      val role1: Role = new Role(1000, "test1000")
      roleActor ! Add(role1)
      expectMsgType[Role]

      val member1: Member = new Member(1001, 1000, "Dido", "Tramp", "adolfoecs@gmail.com", new ContactInfo("Fake St. 123"), new DateTime(), new DateTime())
      memberActor ! Add(member1)
      expectMsgType[Member]

      memberActor ? Delete(member1.id)
      roleActor ? Delete(role1.id)
      success
    }

    "update an existing Member " in {
      val role: Role = new Role(1100, "test1000")
      roleActor ! Add(role)
      expectMsgType[Role]

      val member1: Member = new Member(1001, 1100, "Dido", "Tramp", "adolfoecs@gmail.com", new ContactInfo("Fake St. 123"), new DateTime(), new DateTime())
      memberActor ! Add(member1)
      expectMsgType[Member]

      val memberUpdated: Member = new Member(1001, 1100, "DidoLala", "TrampLala", "adolfoecs@gmail.com", new ContactInfo("Fake St. 123"), new DateTime(), new DateTime())
      memberActor ! Update(memberUpdated)
      expectMsgType[Member]

      memberActor ? Delete(member1.id)
      roleActor ? Delete(role.id)
      success
    }

    "get one Member persisted " in {
      val role: Role = new Role(1100, "test1000")
      roleActor ! Add(role)
      expectMsgType[Role]

      val member1: Member = new Member(1001, 1100, "Dido", "Tramp", "adolfoecs@gmail.com", new ContactInfo("Fake St. 123"), new DateTime(), new DateTime())
      memberActor ! Add(member1)
      expectMsgType[Member]

      memberActor ! Get(member1.id)
      expectMsgType[Member]

      memberActor ? Delete(member1.id)
      roleActor ? Delete(role.id)
      success
    }

    "get all the members persisted " in {
      val role1: Role = new Role(1120, "test1000")
      roleActor ! Add(role1)
      expectMsgType[Role]

      val member1: Member = new Member(1001, 1120, "Dido", "Tramp", "adolfoecs@gmail.com", new ContactInfo("Fake St. 123"), new DateTime(), new DateTime())
      memberActor ! Add(member1)
      expectMsgType[Member]

      roleActor ! GetAll
      expectMsgType[List[Role]]

      memberActor ? Delete(member1.id)
      roleActor ? Delete(role1.id)
      success
    }
  }
}
