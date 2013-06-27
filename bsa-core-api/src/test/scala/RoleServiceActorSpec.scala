import akka.actor.ActorSystem
import akka.testkit.{ ImplicitSender, TestKit }
import ar.com.caeldev.api.CommonConcurrentFeature
import ar.com.caeldev.bsacore.domain.Role
import ar.com.caeldev.core.ActorOperations.{ Update, Delete, Add, GetAll }
import ar.com.caeldev.core.CoreActors
import org.specs2.mutable.SpecificationLike
import akka.pattern.ask

class RoleServiceActorSpec extends TestKit(ActorSystem()) with ImplicitSender with SpecificationLike with CoreActors with CommonConcurrentFeature {

  sequential

  "RoleActor should" >> {

    /*"Should persist a new Role " in {
      val role1: Role = new Role(1000, "test1000")
      val resultAdd = ask(roleActor, Add(role1)).mapTo[Role]
      val result = Await.result(resultAdd, timeout.duration)
      println(result)
      ask(roleActor, Delete(role1.id))
      success
    }*/

    "persist a new Role " in {
      val role1: Role = new Role(1000, "test1000")
      roleActor ! Add(role1)
      expectMsgType[Role]
      //roleActor ? Delete(role1.id)
      success
    }

    "update an existing Role " in {
      val role: Role = new Role(1100, "test1000")
      roleActor ! Add(role)
      expectMsgType[Role]

      val roleUpdated: Role = new Role(1100, "test100011as")
      roleActor ! Update(roleUpdated)
      expectMsgType[Role]

      roleActor ? Delete(roleUpdated.id)
      success
    }

    "get one role persisted " in {
      val role1: Role = new Role(1110, "test1000")
      roleActor ! Add(role1)
      expectMsgType[Role]

      roleActor ! GetAll
      expectMsgType[List[Role]]

      roleActor ? Delete(role1.id)
      success
    }

    "get all the roles persisted " in {
      val role1: Role = new Role(1120, "test1000")
      val role2: Role = new Role(1121, "test1000")
      roleActor ! Add(role1)
      expectMsgType[Role]

      roleActor ! Add(role2)
      expectMsgType[Role]

      roleActor ! GetAll
      expectMsgType[List[Role]]

      roleActor ? Delete(role1.id)
      roleActor ? Delete(role2.id)
      success
    }
  }
}
