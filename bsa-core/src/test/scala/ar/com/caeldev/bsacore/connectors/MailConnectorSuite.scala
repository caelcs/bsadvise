package ar.com.caeldev.bsacore.connectors

import org.scalatest.{ GivenWhenThen, FunSpec }
import ar.com.caeldev.bsacore.domain._
import ar.com.caeldev.bsacore.services._
import ar.com.caeldev.bsacore.commons.domain.{ Success, Error }
import ar.com.caeldev.bsacore.domain.Group
import ar.com.caeldev.bsacore.domain.Role
import ar.com.caeldev.bsacore.domain.Member
import ar.com.caeldev.bsacore.domain.Notification

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

      And("a Group with a valid member")
      val group: Group = DomainSamples.groups(1002)
      val groupService: Service[Group] = new GroupService()
      groupService.add(group)

      And("a Notification")
      val notification: Notification = DomainSamples.notifications(1004)
      val notificationService: Service[Notification] = new NotificationService()
      notificationService.add(notification)

      When("try to send the notification by email")
      val mailConnector: Connector = new MailConnector()

      val result: Either[Success, Error] = mailConnector.send(notification)

      Then("should be success")
      assert(result.isLeft)

      notificationService.delete(notification.id)
      groupService.delete(group.id)
      roleService.delete(role.id)
    }
  }

}
