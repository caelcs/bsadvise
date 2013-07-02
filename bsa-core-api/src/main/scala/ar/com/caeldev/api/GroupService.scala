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
import ar.com.caeldev.bsacore.domain.Group

class GroupService(groupActor: ActorRef)(implicit executionContext: ExecutionContext) extends Directives with CommonConcurrentFeature with CommonJson4sSerializationFeature with Json4sSupport {

  val route =
    post {
      path(ResourceMap.group) {
        entity(as[Group]) {
          group =>
            val resultAdd = ask(groupActor, Add(group)).mapTo[Group]
            val result = Await.result(resultAdd, timeout.duration)
            complete {
              result
            }
        }
      }
    } ~
      put {
        path(ResourceMap.group) {
          entity(as[Group]) {
            group =>
              val resultPut = ask(groupActor, Update(group)).mapTo[Group]
              val result = Await.result(resultPut, timeout.duration)
              complete {
                result
              }
          }
        }
      } ~
      delete {
        path(ResourceMap.group / IntNumber) { id =>
          groupActor ? Delete(id)
          complete {
            StatusCodes.Success
          }
        }
      } ~
      get {
        path(ResourceMap.group / IntNumber) { id =>
          complete {
            val resultGet = ask(groupActor, Get(id)).mapTo[Group]
            val result = Await.result(resultGet, timeout.duration)
            result
          }
        } ~
          path(ResourceMap.groups) {
            complete {
              val resultGetAll = ask(groupActor, GetAll).mapTo[List[Group]]
              val result = Await.result(resultGetAll, timeout.duration)
              result
            }
          }
      }
}
