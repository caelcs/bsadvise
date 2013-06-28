package ar.com.caeldev.api

import spray.routing.Directives
import scala.concurrent.{ Await, ExecutionContext }
import akka.actor.ActorRef
import akka.pattern.ask
import ar.com.caeldev.core.ActorOperations._
import ar.com.caeldev.core.ActorOperations.Add
import ar.com.caeldev.core.ActorOperations.Update
import ar.com.caeldev.core.ActorOperations.Get
import ar.com.caeldev.bsacore.domain.Role
import ar.com.caeldev.core.ActorOperations.Delete
import spray.httpx.Json4sSupport
import spray.http.StatusCodes

class RoleService(roleActor: ActorRef)(implicit executionContext: ExecutionContext) extends Directives with CommonConcurrentFeature with CommonJson4sSerializationFeature with Json4sSupport {

  val route =
    post {
      path(ResourceMap.role) {
        entity(as[Role]) {
          role =>
            val resultAdd = ask(roleActor, Add(role)).mapTo[Role]
            val result = Await.result(resultAdd, timeout.duration)
            complete {
              result
            }
        }
      }
    } ~
      put {
        path(ResourceMap.role) {
          entity(as[Role]) {
            role =>
              val resultPut = ask(roleActor, Update(role)).mapTo[Role]
              val result = Await.result(resultPut, timeout.duration)
              complete {
                result
              }
          }
        }
      } ~
      delete {
        path(ResourceMap.role / IntNumber) { id =>
          roleActor ? Delete(id)
          complete {
            StatusCodes.Success
          }
        }
      } ~
      get {
        path(ResourceMap.role / IntNumber) { id =>
          complete {
            val resultGet = ask(roleActor, Get(id)).mapTo[Role]
            val result = Await.result(resultGet, timeout.duration)
            result
          }
        } ~
          path(ResourceMap.roles) {
            complete {
              val resultGetAll = ask(roleActor, GetAll).mapTo[List[Role]]
              val result = Await.result(resultGetAll, timeout.duration)
              result
            }
          }
      }
}