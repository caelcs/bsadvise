package ar.com.caeldev.api

import spray.routing.Directives
import scala.concurrent.{Await, Future, ExecutionContext}
import akka.actor.ActorRef
import akka.pattern.ask
import ar.com.caeldev.core.ActorOperations._
import ar.com.caeldev.core.ActorOperations.Add
import ar.com.caeldev.core.ActorOperations.Update
import ar.com.caeldev.core.ActorOperations.Get
import ar.com.caeldev.bsacore.domain.Role
import ar.com.caeldev.core.ActorOperations.Delete
import spray.httpx.Json4sSupport

class RoleService(roleActor: ActorRef)(implicit executionContext: ExecutionContext) extends Directives with CommonConcurrentFeature with CommonJson4sSerializationFeature with Json4sSupport {

  val route =
    path(ResourceMap.role) {
      post {
        entity(as[Role]) {
          role =>
            val resultAdd = ask(roleActor, Add(role)).mapTo[Role]
            val result = Await.result(resultAdd, timeout.duration)
            complete {
              result
            }
        }
      } ~
        put {
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
      path(ResourceMap.role / IntNumber) {
        id =>
          delete {
            roleActor ? Delete(id)
            complete {
              "success"
            }
          } ~
            get {
              val resultGet = ask(roleActor, Get("id", id)).mapTo[Role]
              val result = Await.result(resultGet, timeout.duration)
              complete {
                result
              }
            }
      } ~
      path(ResourceMap.roles) {
        get {
          val resultGetAll = ask(roleActor, GetAll).mapTo[List[Role]]
          val result = Await.result(resultGetAll, timeout.duration)
          complete {
            result
          }
        }
      }
}