package ar.com.caeldev.bsacore.domain

import org.joda.time.DateTime

object DomainSamples {

  val roles: Map[Long, Role] = Map(
    (1000, new Role(1000, "test1000")))

  val members: Map[Long, Member] = Map(
    (1001, new Member(1001, 1000, "Dido", "Tramp", "dido@gmail.com", new ContactInfo("Fake St. 123"), new DateTime(), new DateTime())))

  val groups: Map[Long, Group] = Map(
    (1002, new Group(1002, "Avengers", List(1001))))

  val notifications: Map[Long, Notification] = Map(
    (1004, new Notification(1004, 1001, List(1001), "Hello", new DateTime(), new DateTime())))

  val messageTemplates: Map[Long, MessageTemplate] = Map(
    (1005, new MessageTemplate(1005, "Hello", 1001, new DateTime())))

}
