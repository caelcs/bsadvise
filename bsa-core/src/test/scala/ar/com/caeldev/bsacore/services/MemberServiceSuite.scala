package ar.com.caeldev.bsacore.services

import org.scalatest.{ GivenWhenThen, FunSpec }
import ar.com.caeldev.bsacore.domain.{ Member, Role, DomainSamples }
import ar.com.caeldev.bsacore.services.common.Service
import ar.com.caeldev.bsacore.services.role.RoleService
import ar.com.caeldev.bsacore.services.member.MemberService
import ar.com.caeldev.bsacore.services.exceptions.ServiceException

class MemberServiceSuite extends FunSpec with GivenWhenThen {

  describe("A Member Service") {
    it("Should add a new valid Member") {
      Given("a Role that exists and it was persisted")
      val role: Role = DomainSamples.roles(1000)
      val roleService: Service[Role] = new RoleService()
      roleService.add(role)

      And("a valid Member")
      val member: Member = DomainSamples.members(1001)

      When("try to persist the member")
      val memberService: Service[Member] = new MemberService()
      val persistedMember = memberService.add(member)

      Then("should pass successfully")
      assert(persistedMember.id === member.id)
      assert(persistedMember.email === member.email)
      assert(persistedMember.lastName === member.lastName)
      assert(persistedMember.firstName === member.firstName)
      assert(persistedMember.role_id === member.role_id)
    }

    it("Should not add a new not valid Member") {
      Given("a not valid Member with an invalid role")
      val member: Member = DomainSamples.members(1011)

      When("try to persist the member")
      Then("should pass successfully")
      val memberService: Service[Member] = new MemberService()
      intercept[ServiceException] {
        memberService.add(member)
      }
    }

  }

}