package ar.com.caeldev.api

import akka.actor.ActorSystem
import akka.testkit.{ ImplicitSender, TestKit }
import ar.com.caeldev.bsacore.domain.Role
import ar.com.caeldev.core.ActorOperations._
import ar.com.caeldev.core.ActorOperations.Add
import ar.com.caeldev.core.ActorOperations.Delete
import ar.com.caeldev.core.ActorOperations.GetAll
import ar.com.caeldev.core.ActorOperations.Update
import ar.com.caeldev.core.CoreActors
import org.specs2.mutable.SpecificationLike
import akka.pattern.ask
import scala.concurrent.Await

class RoleServiceActorSpec extends TestKit(ActorSystem()) with ImplicitSender with SpecificationLike with CoreActors with CommonConcurrentFeature {

  sequential

  "RoleActor should" >> {

    "persist a new Role " in {
      val role1: Role = new Role(1111, "test1000")
      val resultRole = Await.result(roleActor ? Add(role1), timeout.duration)
      assert(resultRole === role1)

      Await.result(roleActor ? Delete(role1.id), timeout.duration)
      success
    }

    "update an existing Role " in {
      val role: Role = new Role(1112, "test1000")
      val resultRole = Await.result(roleActor ? Add(role), timeout.duration)
      assert(resultRole === role)

      val roleUpdated: Role = new Role(1112, "test100011as")
      val resultRoleUpdated = Await.result(roleActor ? Update(roleUpdated), timeout.duration)
      assert(resultRoleUpdated === roleUpdated)

      Await.result(roleActor ? Delete(role.id), timeout.duration)
      success
    }

    "get one role persisted " in {
      val role1: Role = new Role(1113, "test1000")
      val resultRole = Await.result(roleActor ? Add(role1), timeout.duration)
      assert(resultRole === role1)

      val resultRoleGet = Await.result(roleActor ? Get(role1.id), timeout.duration)
      assert(resultRoleGet === role1)

      Await.result(roleActor ? Delete(role1.id), timeout.duration)
      success
    }

    "get all the roles persisted " in {
      val role1: Role = new Role(1114, "test1000")
      val resultRole = Await.result(roleActor ? Add(role1), timeout.duration)
      assert(resultRole === role1)

      val resultRoleGetAll = Await.result(roleActor ? GetAll, timeout.duration)
      assert(resultRoleGetAll.asInstanceOf[List[Role]].find(x => x.id == role1.id).size === 1)

      Await.result(roleActor ? Delete(role1.id), timeout.duration)
      success
    }
  }
}
