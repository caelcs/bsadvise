package ar.com.caeldev.bsacore.services

import org.scalatest.{ GivenWhenThen, FunSpec }
import ar.com.caeldev.bsacore.domain.{ Member, DomainSamples, Role }
import ar.com.caeldev.bsacore.services.exceptions.ServiceException
import ar.com.caeldev.bsacore.services.role.RoleService
import ar.com.caeldev.bsacore.services.common.Service
import ar.com.caeldev.bsacore.services.member.MemberService
import ar.com.caeldev.bsacore.config.ConfigContext

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
      val thrown = intercept[ServiceException] {
        roleService.add(entity)
      }

      Then("the exception message should match for 1000 code")
      val appConfigContext: ConfigContext = new ConfigContext("errors.conf")
      assert(thrown.getMessage === appConfigContext.get("errors.rules.1000.description"))

    }

    it("Should update an entity to the backend") {
      Given("a Role Entity persisted")
      val entity: Role = DomainSamples.roles(1000)
      val roleService: Service[Role] = new RoleService()
      roleService.add(entity)

      And("update the Role entity with new data")
      val toBeUpdateEntity: Role = DomainSamples.roles(1012)

      When("update the entity Role")
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

      When("delete the entity Role")
      roleService.delete(entity.id)

      Then("should be successfully deleted")
      val roleFromDB: Role = roleService.get(1000)
      assert(roleFromDB == null)
    }

    it("Should not delete an entity Role who is referenced by a Member") {
      Given("a Role Entity persisted")
      val entity: Role = DomainSamples.roles(1000)
      val roleService: Service[Role] = new RoleService()
      roleService.add(entity)

      And("a Member Entity which reference to the Role Entity already persisted")
      val memberEntity: Member = DomainSamples.members(1001)
      val memberService: Service[Member] = new MemberService()
      memberService.add(memberEntity)

      When("try delete the entity Role raise a ServiceException")
      val thrown = intercept[ServiceException] {
        roleService.delete(entity.id)
      }

      Then("the exception message should match for 1002 code")
      val appConfigContext: ConfigContext = new ConfigContext("errors.conf")
      assert(thrown.getMessage === appConfigContext.get("errors.rules.1002.description"))

      memberService.delete(memberEntity.id)
      roleService.delete(entity.id)
    }
  }
}
