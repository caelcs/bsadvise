package ar.com.caeldev.api

import spray.routing.Directives
import scala.concurrent.{ Future, ExecutionContext }
import akka.actor.ActorRef
import akka.pattern.ask
import ar.com.caeldev.core.ActorOperations._
import ar.com.caeldev.core.ActorOperations.Add
import ar.com.caeldev.core.ActorOperations.Update
import ar.com.caeldev.core.ActorOperations.Get
import ar.com.caeldev.bsacore.domain.Role
import ar.com.caeldev.core.ActorOperations.Delete
import spray.httpx.Json4sSupport

class RoleService(roleService: ActorRef)(implicit executionContext: ExecutionContext) extends Directives with CommonConcurrentFeature with CommonJson4sSerializationFeature with Json4sSupport {

  val route =
    path(ResourceMap.role) {
      post {
        entity(as[Role]) { role =>
          val result: Future[Role] = ask(roleService, Add(role)).mapTo[Role]
          complete {
            result
          }
        }
      } ~
        put {
          entity(as[Role]) { role =>
            val result: Future[Role] = ask(roleService, Update(role)).mapTo[Role]
            complete {
              result
            }

          }
        }
    } ~
      path(ResourceMap.role / IntNumber) { id =>
        delete {
          ask(roleService, Delete(id))
          complete {
            "success"
          }

        } ~
          get {
            val result: Future[Role] = ask(roleService, Get("id", id)).mapTo[Role]
            complete {
              result
            }
          }
      } ~
      path(ResourceMap.roles) {
        get {
          val result: Future[Role] = ask(roleService, GetAll).mapTo[Role]
          complete {
            result
          }
        }
      }
}