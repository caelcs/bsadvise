package ar.com.caeldev

import akka.actor.ActorSystem

/** Contains useful type aliases that are to be members of the entire ``core`` package.
 */
package object core {

  /** Core is a structural type containing the ``system: ActorSystem`` member.
   */
  type Core = { def system: ActorSystem }

}
