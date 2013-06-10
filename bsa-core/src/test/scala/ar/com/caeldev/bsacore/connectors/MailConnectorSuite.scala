package ar.com.caeldev.bsacore.connectors

import org.scalatest.{ GivenWhenThen, FunSpec }
import ar.com.caeldev.bsacore.domain.{ Notification, Member, Role, DomainSamples }
import ar.com.caeldev.bsacore.services.{ MemberService, Service, RoleService }
import ar.com.caeldev.bsacore.commons.domain.{ Success, Error }

class MailConnectorSuite extends FunSpec with GivenWhenThen {

  describe("A MailConnector") {
    it("Should send Notification by email") {
      Given("a Role")
      val role: Role = DomainSamples.roles(1000)
      val roleService: Service[Role] = new RoleService()
      roleService.add(role)

      And("a Member")
      val member: Member = DomainSamples.members(1001)
      val memberService: Service[Member] = new MemberService()
      memberService.add(member)

      And("a Notification")
      val notification: Notification = DomainSamples.notifications(1004)

      When("try to send the notification by email")
      val mailConnector: Connector = new MailConnector()

      val result: Either[Success, Error] = mailConnector.connect(notification)

      Then("should be success")
      println(result)
      assert(result.isLeft)

      memberService.delete(member.id)
      roleService.delete(role.id)
    }
  }

}
