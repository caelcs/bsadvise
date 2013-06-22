package ar.com.caeldev.api

import akka.actor.ActorRef
import akka.pattern.ask
import scala.concurrent.{ Future, ExecutionContext }
import spray.routing.Directives
import spray.httpx.Json4sSupport
import ar.com.caeldev.core.ActorOperations._
import ar.com.caeldev.core.ActorOperations.Add
import ar.com.caeldev.core.ActorOperations.Update
import ar.com.caeldev.core.ActorOperations.Get
import ar.com.caeldev.bsacore.domain.Member
import ar.com.caeldev.core.ActorOperations.Delete

class MemberService(memberService: ActorRef)(implicit executionContext: ExecutionContext) extends Directives with CommonConcurrentFeature with CommonJson4sSerializationFeature with Json4sSupport {

  val route =
    path(ResourceMap.member) {
      post {
        entity(as[Member]) { member =>
          val result: Future[Member] = ask(memberService, Add(member)).mapTo[Member]
          complete {
            result
          }
        }
      } ~
        put {
          entity(as[Member]) { member =>
            val result: Future[Member] = ask(memberService, Update(member)).mapTo[Member]
            complete {
              result
            }
          }
        }
    } ~
      path(ResourceMap.member / IntNumber) { id =>
        delete {
          ask(memberService, Delete(id))
          complete {
            "success"
          }

        } ~
          get {
            val result: Future[Member] = ask(memberService, Get("id", id)).mapTo[Member]
            complete {
              result
            }
          }
      } ~
      path(ResourceMap.members) {
        get {
          val result: Future[Member] = ask(memberService, GetAll).mapTo[Member]
          complete {
            result
          }
        }
      }
}
