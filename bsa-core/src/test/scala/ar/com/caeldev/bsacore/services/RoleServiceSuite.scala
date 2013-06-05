package ar.com.caeldev.bsacore.services

import org.scalatest.{ GivenWhenThen, FunSpec }
import ar.com.caeldev.bsacore.domain.{ DomainSamples, Role }
import ar.com.caeldev.bsacore.services.exceptions.ServiceException
import ar.com.caeldev.bsacore.services.role.RoleService
import ar.com.caeldev.bsacore.services.common.Service

class RoleServiceSuite extends FunSpec with GivenWhenThen {

  describe("A Role Service") {
    it("Should add a new entity to the backend") {
      Given("a Role Entity")
      val entity: Role = DomainSamples.roles(1000)
      And("a Role Service")
      val roleService: Service[Role] = new RoleService()

      When("try to add the entity to the backend")
      val entitySaved: Role = roleService.add(entity)

      Then("should get it back from backend.")
      assert(entitySaved.equals(entity))

      roleService.delete(entitySaved.id)
      val deletedEntity: Role = roleService.get(entitySaved.id)
      assert(deletedEntity == null)
    }

    it("Should not add a new entity to the backend") {
      Given("a Role Entity with an empty description")
      val entity: Role = DomainSamples.roles(1001)
      And("a Role Service")
      val roleService: Service[Role] = new RoleService()

      When("try to add the entity to the backend")
      Then("should raise a ServiceException")
      intercept[ServiceException] {
        roleService.add(entity)
      }
    }

    it("Should update an entity to the backend") {
      Given("a Role Entity persisted")
      val entity: Role = DomainSamples.roles(1000)
      val roleService: Service[Role] = new RoleService()
      roleService.add(entity)

      And("update the Role entity with new data")
      val toBeUpdateEntity: Role = DomainSamples.roles(1012)

      When("update the entity Member")
      val updatedRole = roleService.update(toBeUpdateEntity)

      Then("should be successfully updated")
      assert(updatedRole.id == entity.id)
      assert(updatedRole.description != entity.description)

      roleService.delete(updatedRole.id)
      val deletedEntity: Role = roleService.get(updatedRole.id)
      assert(deletedEntity == null)

    }

    it("Should be delete an entity from the backend") {
      Given("a Role Entity persisted")
      val entity: Role = DomainSamples.roles(1000)
      val roleService: Service[Role] = new RoleService()
      roleService.add(entity)

      When("delete the entity Member")
      roleService.delete(entity.id)

      Then("should be successfully deleted")
      val roleFromDB: Role = roleService.get(1000)
      assert(roleFromDB == null)
    }

  }
}
