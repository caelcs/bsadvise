package ar.com.caeldev.core

import akka.actor.{ Props, ActorSystem }

/** This trait implements ``Core`` by starting the required ``ActorSystem`` and registering the
 *  termination handler to stop the system when the JVM exits.
 */
trait BootedCore {

  /** Construct the ActorSystem we will use in our application
   */
  implicit val system = ActorSystem("bsadvisee")

  /** Ensure that the constructed ActorSystem is shut down when the JVM shuts down
   */
  system.registerOnTermination(system.shutdown())

}

/** This trait contains the actors that make up our application; it can be mixed in with
 *  ``BootedCore`` for running code or ``TestKit`` for unit and integration tests.
 */
trait CoreActors {
  this: Core =>

  val roleActor = system.actorOf(Props[RoleServiceActor])
  val memberActor = system.actorOf(Props[MemberServiceActor])
  val groupActor = system.actorOf(Props[GroupServiceActor])
  val notificationActor = system.actorOf(Props[NotificationServiceActor])

}

object ActorOperations {
  case class Add(entity: Any)
  case class Update(entity: Any)
  case class Delete(id: Any)
  case class Get(id: Any)
  case class GetAll()
}
