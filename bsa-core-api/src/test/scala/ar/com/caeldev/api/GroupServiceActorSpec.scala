package ar.com.caeldev.api

import akka.actor.ActorSystem
import akka.testkit.{ ImplicitSender, TestKit }
import ar.com.caeldev.bsacore.domain.{ Group, ContactInfo, Member, Role }
import ar.com.caeldev.core.ActorOperations._
import ar.com.caeldev.core.ActorOperations.Add
import ar.com.caeldev.core.ActorOperations.Delete
import ar.com.caeldev.core.ActorOperations.GetAll
import ar.com.caeldev.core.ActorOperations.Update
import ar.com.caeldev.core.CoreActors
import akka.pattern.ask
import org.specs2.mutable.SpecificationLike
import org.joda.time.DateTime
import scala.concurrent.Await

class GroupServiceActorSpec extends TestKit(ActorSystem()) with ImplicitSender with SpecificationLike with CoreActors with CommonConcurrentFeature {

  sequential

  "GroupActor should" >> {

    "persist a new Group " in {
      val role1: Role = new Role(2000, "test1000")
      val resultRole = Await.result(roleActor ? Add(role1), timeout.duration)
      assert(resultRole === role1)

      val member1: Member = new Member(2000, 2000, "Dido", "Tramp", "adolfoecs@gmail.com", new ContactInfo("Fake St. 123"), new DateTime(), new DateTime())
      val resultMember = Await.result(memberActor ? Add(member1), timeout.duration)
      assert(resultMember === member1)

      val group1: Group = new Group(2002, "Avengers", List(2000))
      val resultGroup = Await.result(groupActor ? Add(group1), timeout.duration)
      assert(resultGroup === group1)

      Await.result(groupActor ? Delete(group1.id), timeout.duration)
      Await.result(roleActor ? Delete(role1.id), timeout.duration)

      success
    }

    "update an existing Group " in {
      val role1: Role = new Role(2100, "test1000")
      val resultRole = Await.result(roleActor ? Add(role1), timeout.duration)
      assert(resultRole === role1)

      val member1: Member = new Member(2001, 2100, "Dido", "Tramp", "adolfoecs@gmail.com", new ContactInfo("Fake St. 123"), new DateTime(), new DateTime())
      val resultMember = Await.result(memberActor ? Add(member1), timeout.duration)
      assert(resultMember === member1)

      val group1: Group = new Group(2003, "Avengers", List(2001))
      val resultGroup = Await.result(groupActor ? Add(group1), timeout.duration)
      assert(resultGroup === group1)

      val group2: Group = new Group(2003, "The Avengers", List(2001))
      val resultGroup2 = Await.result(groupActor ? Update(group2), timeout.duration)
      assert(resultGroup2 === group2)

      Await.result(groupActor ? Delete(group1.id), timeout.duration)
      Await.result(roleActor ? Delete(role1.id), timeout.duration)

      success
    }

    "get one Group persisted " in {
      val role1: Role = new Role(2101, "test1000")
      val resultRole = Await.result(roleActor ? Add(role1), timeout.duration)
      assert(resultRole === role1)

      val member1: Member = new Member(2002, 2101, "Dido", "Tramp", "adolfoecs@gmail.com", new ContactInfo("Fake St. 123"), new DateTime(), new DateTime())
      val resultMember = Await.result(memberActor ? Add(member1), timeout.duration)
      assert(resultMember === member1)

      val group1: Group = new Group(2004, "Avengers", List(2002))
      val resultGroup = Await.result(groupActor ? Add(group1), timeout.duration)
      assert(resultGroup === group1)

      val resultGroupGet = Await.result(groupActor ? Get(group1.id), timeout.duration)
      assert(resultGroupGet === group1)

      Await.result(groupActor ? Delete(group1.id), timeout.duration)
      Await.result(roleActor ? Delete(role1.id), timeout.duration)

      success
    }

    "get all the members persisted " in {
      val role1: Role = new Role(2120, "test1000")
      val resultRole = Await.result(roleActor ? Add(role1), timeout.duration)
      assert(resultRole === role1)

      val member1: Member = new Member(2003, 2120, "Dido", "Tramp", "adolfoecs@gmail.com", new ContactInfo("Fake St. 123"), new DateTime(), new DateTime())
      val resultMember = Await.result(memberActor ? Add(member1), timeout.duration)
      assert(resultMember === member1)

      val group1: Group = new Group(2005, "Avengers", List(2003))
      val resultGroup = Await.result(groupActor ? Add(group1), timeout.duration)
      assert(resultGroup === group1)

      val resultGroup1 = Await.result(groupActor ? GetAll, timeout.duration)
      assert(resultGroup1.asInstanceOf[List[Group]].find(x => x === group1).size === 1)

      Await.result(groupActor ? Delete(group1.id), timeout.duration)
      Await.result(roleActor ? Delete(role1.id), timeout.duration)

      success
    }
  }
}
