package ar.com.caeldev.bsacore.domain

import org.joda.time.DateTime

object DomainSamples {

  val roles: Map[Long, Role] = Map(
    (1000, new Role(1000, "test1000")),
    (1012, new Role(1000, "test1012")),
    (1001, new Role(1001, "")))

  val members: Map[Long, Member] = Map(
    (1001, new Member(1001, 1000, "Dido", "Tramp", "adolfoecs@gmail.com", new ContactInfo("Fake St. 123"), new DateTime(), new DateTime())),
    (1011, new Member(1011, 1002, "DidoFake", "TrampFake", "didofake@gmail.com", new ContactInfo("Fake St. 123"), new DateTime(), new DateTime())),
    (1013, new Member(1001, 1000, "DidoFake", "TrampFake", "didofake@gmail.com", new ContactInfo("Fake St. 123"), new DateTime(), new DateTime())))

  val groups: Map[Long, Group] = Map(
    (1002, new Group(1002, "Avengers", List(1001))),
    (1003, new Group(1002, "The Avengers", List(1001))),
    (1006, new Group(1002, "The Avengers", List.empty)))

  val notifications: Map[Long, Notification] = Map(
    (1004, new Notification(1004, 1001, 1002, "How are you?", "Hello")),
    (1007, new Notification(1004, 1001, 1002, "How are you?", "Hello")))

  val messageTemplates: Map[Long, MessageTemplate] = Map(
    (1005, new MessageTemplate(1005, "Hello", 1001, new DateTime())))

}
