package scala

import java.util.Date

class Product(val id: Int, val code: String, val description: String) {

  var creation_date: Date = _
  var category: Category = _

  def this(id: Int, code: String, description: String, creation_date: Date) {
    this(id, code, description)
    this.creation_date = creation_date
  }

  def this(id: Int, code: String, description: String, creation_date: Date, category: Category) {
    this(id, code, description, creation_date)
    this.category = category
  }


}

class Category(val id: Int, val description: String) {
}

class Person(val firstName: String, val lastName: String) {

  var birthDate: Date = _

  def this(firstName: String, lastName: String, birthDate: Date) {
    this(firstName, lastName)
    this.birthDate = birthDate
  }

  def completeName(): String = {
    "%s, %s" format(lastName, firstName)
  }
}
