package ar.com.caeldev.api

import ar.com.caeldev.core
import core.{ CoreActors, Core }
import akka.actor.Props
import spray.routing.RouteConcatenation

/** !
 *  The REST API layer. It exposes the REST services, but does not provide any
 *  web server interface.<br/>
 *  Notice that it requires to be mixed in with ``core.Core``, which provides access
 *  to the top-level actors that make up the system.
 */
trait Api extends RouteConcatenation {
  this: CoreActors with Core =>

  private implicit val _ = system.dispatcher

  val routes =
    new RoleService(roleActor).route ~
      new MemberService(memberActor).route ~
      new GroupService(groupActor).route ~
      new NotificationService(notificationActor).route

  val serviceActor = system.actorOf(Props(new RoutedHttpService(routes)))

}
