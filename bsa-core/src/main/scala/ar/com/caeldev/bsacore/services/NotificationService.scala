package ar.com.caeldev.bsacore.services

import ar.com.caeldev.bsacore.domain.{ Notification }
import ar.com.caeldev.bsacore.validation.{ Rule, Validation }
import ar.com.caeldev.bsacore.config.ConfigContext
import scala.util.control.Exception._
import ar.com.caeldev.bsacore.services.exceptions.ServiceException
import ar.com.caeldev.bsacore.validation.rules.{ ListNotEmpty, NotNull, NotEmpty }
import org.joda.time.DateTime

class NotificationService(implicit val mot: Manifest[Notification]) extends Service[Notification] with Validation[Notification] {

  def add(entity: Notification): Notification = {
    validate(entity, Operation.add.toString)
    dao.save(entity)
    dao.findById(entity.id)
  }

  def delete(id: Any) {
    val entityToDelete: Notification = dao.findById(id)
    validate(entityToDelete, Operation.delete.toString)
    catcher {
      dao.remove(entityToDelete)
    }
  }

  def update(entity: Notification): Notification = {
    throw new UnsupportedOperationException()
  }

  def get(id: Any): Notification = {
    dao.findById(id)
  }

  def applyRulesFor(entity: Notification, operation: String): List[Rule[_]] = {
    NotificationRules.get(entity, operation)
  }
}

object NotificationRules {

  val appConfigContext: ConfigContext = new ConfigContext("errors.conf")
  val operationCatch = catching(classOf[NoSuchElementException]).withApply(e => throw new ServiceException(appConfigContext.get("errors.services.1100.description"), e))

  def get(entity: Notification, operation: String): List[Rule[_]] = {
    var results: List[Rule[_]] = Nil

    operationCatch {
      Operation.withName(operation) match {
        case Operation.add => {
          results = List(
            new Rule[String](List(entity.message, entity.id.toString, entity.sender_id.toString), NotEmpty.get),
            new Rule[DateTime](entity.createdAt, NotNull.get),
            new Rule[List[_]](entity.receivers, NotNull.get),
            new Rule[List[_]](entity.receivers, ListNotEmpty.get))
        }
        case Operation.update => {
          results = List.empty
        }
        case Operation.delete => {
          results = List.empty
        }
      }
    }
    results
  }

}
