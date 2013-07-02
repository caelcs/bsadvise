package ar.com.caeldev.api

import akka.actor.ActorSystem
import akka.testkit.{ ImplicitSender, TestKit }
import ar.com.caeldev.bsacore.domain._
import ar.com.caeldev.core.CoreActors
import akka.pattern.ask
import org.specs2.mutable.SpecificationLike
import org.joda.time.DateTime
import ar.com.caeldev.bsacore.domain.Group
import ar.com.caeldev.core.ActorOperations.Add
import ar.com.caeldev.bsacore.domain.ContactInfo
import ar.com.caeldev.core.ActorOperations.Get
import ar.com.caeldev.bsacore.domain.Role
import ar.com.caeldev.core.ActorOperations.GetAll
import ar.com.caeldev.bsacore.domain.Member
import ar.com.caeldev.core.ActorOperations.Delete
import scala.concurrent.Await

class NotificationServiceActorSpec extends TestKit(ActorSystem()) with ImplicitSender with SpecificationLike with CoreActors with CommonConcurrentFeature {

  sequential

  "NotificationActor should" >> {

    "persist a new Notification " in {
      val role1: Role = new Role(3000, "test1000")
      val resultRole = Await.result(roleActor ? Add(role1), timeout.duration)
      assert(resultRole === role1)

      val member1: Member = new Member(3001, 3000, "Dido", "Tramp", "adolfoecs@gmail.com", new ContactInfo("Fake St. 123"), new DateTime(), new DateTime())
      val resultMember = Await.result(memberActor ? Add(member1), timeout.duration)
      assert(resultMember === member1)

      val group1: Group = new Group(3011, "Avengers", List(3001))
      val resultGroup = Await.result(groupActor ? Add(group1), timeout.duration)
      assert(resultGroup === group1)

      val notification1: Notification = new Notification(3201, 3001, 3011, "How are you?", "Hello")
      val resultNotification = Await.result(notificationActor ? Add(notification1), timeout.duration)
      assert(resultNotification === notification1)

      Await.result(notificationActor ? Delete(notification1.id), timeout.duration)
      Await.result(groupActor ? Delete(group1.id), timeout.duration)
      Await.result(roleActor ? Delete(role1.id), timeout.duration)

      success
    }

    "get one Notification persisted " in {
      val role1: Role = new Role(3100, "test1000")
      val resultRole = Await.result(roleActor ? Add(role1), timeout.duration)
      assert(resultRole === role1)

      val member1: Member = new Member(3002, 3100, "Dido", "Tramp", "adolfoecs@gmail.com", new ContactInfo("Fake St. 123"), new DateTime(), new DateTime())
      val resultMember = Await.result(memberActor ? Add(member1), timeout.duration)
      assert(resultMember === member1)

      val group1: Group = new Group(3012, "Avengers", List(3002))
      val resultGroup = Await.result(groupActor ? Add(group1), timeout.duration)
      assert(resultGroup === group1)

      val notification1: Notification = new Notification(3202, 3002, 3012, "How are you?", "Hello")
      val resultNotification = Await.result(notificationActor ? Add(notification1), timeout.duration)
      assert(resultNotification === notification1)

      val resultNotificationGet = Await.result(notificationActor ? Get(notification1.id), timeout.duration)
      assert(resultNotificationGet === notification1)

      Await.result(notificationActor ? Delete(notification1.id), timeout.duration)
      Await.result(groupActor ? Delete(group1.id), timeout.duration)
      Await.result(roleActor ? Delete(role1.id), timeout.duration)

      success
    }

    "get all the notifications persisted " in {
      val role1: Role = new Role(3120, "test1000")
      val resultRole = Await.result(roleActor ? Add(role1), timeout.duration)
      assert(resultRole === role1)

      val member1: Member = new Member(3003, 3120, "Dido", "Tramp", "adolfoecs@gmail.com", new ContactInfo("Fake St. 123"), new DateTime(), new DateTime())
      val resultMember = Await.result(memberActor ? Add(member1), timeout.duration)
      assert(resultMember === member1)

      val group1: Group = new Group(3013, "Avengers", List(3003))
      val resultGroup = Await.result(groupActor ? Add(group1), timeout.duration)
      assert(resultGroup === group1)

      val notification1: Notification = new Notification(3203, 3003, 3013, "How are you?", "Hello")
      val resultNotification = Await.result(notificationActor ? Add(notification1), timeout.duration)
      assert(resultNotification === notification1)

      val resultNotificationGetAll = Await.result(notificationActor ? GetAll, timeout.duration)
      assert(resultNotificationGetAll.asInstanceOf[List[Notification]].find(x => x === notification1).size === 1)

      Await.result(notificationActor ? Delete(notification1.id), timeout.duration)
      Await.result(groupActor ? Delete(group1.id), timeout.duration)
      Await.result(roleActor ? Delete(role1.id), timeout.duration)

      success
    }
  }
}
