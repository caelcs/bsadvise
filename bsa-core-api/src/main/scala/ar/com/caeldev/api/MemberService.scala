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
import ar.com.caeldev.bsacore.domain.Member
import ar.com.caeldev.core.ActorOperations.Delete
import spray.http.StatusCodes

class MemberService(memberActor: ActorRef)(implicit executionContext: ExecutionContext) extends Directives with CommonConcurrentFeature with CommonJson4sSerializationFeature with Json4sSupport {

  val route =
    post {
      path(ResourceMap.member) {
        entity(as[Member]) {
          member =>
            println("Post - MemberService")
            val resultAdd = ask(memberActor, Add(member)).mapTo[Member]
            val result = Await.result(resultAdd, timeout.duration)
            complete {
              result
            }
        }
      }
    } ~
      put {
        path(ResourceMap.member) {
          entity(as[Member]) {
            member =>
              println("Put - MemberService")
              val resultPut = ask(memberActor, Update(member)).mapTo[Member]
              val result = Await.result(resultPut, timeout.duration)
              complete {
                result
              }
          }
        }
      } ~
      delete {
        path(ResourceMap.member / IntNumber) { id =>
          memberActor ? Delete(id)
          complete {
            println("Delete - MemberService")
            StatusCodes.Success
          }
        }
      } ~
      get {
        path(ResourceMap.member / IntNumber) { id =>
          complete {
            println("Get - MemberService")
            val resultGet = ask(memberActor, Get(id)).mapTo[Member]
            val result = Await.result(resultGet, timeout.duration)
            result
          }
        } ~
          path(ResourceMap.members) {
            complete {
              println("GetAll - MemberService")
              val resultGetAll = ask(memberActor, GetAll).mapTo[List[Member]]
              val result = Await.result(resultGetAll, timeout.duration)
              result
            }
          }
      }
}
