package ar.com.caeldev.api

import akka.actor.ActorRef
import akka.pattern.ask
import scala.concurrent.{ Await, ExecutionContext }
import spray.routing.Directives
import spray.httpx.Json4sSupport
import ar.com.caeldev.core.ActorOperations._
import ar.com.caeldev.core.ActorOperations.Add
import ar.com.caeldev.core.ActorOperations.Update
import ar.com.caeldev.core.ActorOperations.Get
import ar.com.caeldev.core.ActorOperations.Delete
import spray.http.StatusCodes
import ar.com.caeldev.bsacore.domain.Notification

class NotificationService(notificationActor: ActorRef)(implicit executionContext: ExecutionContext) extends Directives with CommonConcurrentFeature with CommonJson4sSerializationFeature with Json4sSupport {

  val route =
    post {
      path(ResourceMap.notification) {
        entity(as[Notification]) {
          notification =>
            val resultAdd = ask(notificationActor, Add(notification)).mapTo[Notification]
            val result = Await.result(resultAdd, timeout.duration)
            complete {
              result
            }
        }
      }
    } ~
      delete {
        path(ResourceMap.notification / IntNumber) { id =>
          notificationActor ? Delete(id)
          complete {
            StatusCodes.Success
          }
        }
      } ~
      get {
        path(ResourceMap.notification / IntNumber) { id =>
          complete {
            val resultGet = ask(notificationActor, Get(id)).mapTo[Notification]
            val result = Await.result(resultGet, timeout.duration)
            result
          }
        } ~
          path(ResourceMap.notifications) {
            complete {
              val resultGetAll = ask(notificationActor, GetAll).mapTo[List[Notification]]
              val result = Await.result(resultGetAll, timeout.duration)
              result
            }
          }
      }
}
