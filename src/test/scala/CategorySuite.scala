package scala

import org.scalatest.FunSuite

class CategorySuite extends FunSuite {

  test("create a category instance"){
    def category = new Category(1, "demo")
    assert(category.id === 1)
    assert(category.description === "demo")
  }

  test("create a category instance with empty description"){
    def category = new Category(1, "")
    assert(category.id === 1)
    assert(category.description === "")
  }

  test("create a category instance with null description"){
    def category = new Category(1, null)
    assert(category.id === 1)
    assert(category.description === null)
  }

}
