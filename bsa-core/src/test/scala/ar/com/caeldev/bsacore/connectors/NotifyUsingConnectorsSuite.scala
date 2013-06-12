package ar.com.caeldev.bsacore.connectors

import org.scalatest.{ GivenWhenThen, FunSpec }
import ar.com.caeldev.bsacore.domain._
import ar.com.caeldev.bsacore.services._
import ar.com.caeldev.bsacore.domain.Group
import ar.com.caeldev.bsacore.domain.Role
import ar.com.caeldev.bsacore.domain.Member
import ar.com.caeldev.bsacore.domain.Notification

class NotifyUsingConnectorsSuite extends FunSpec with GivenWhenThen with NotifyUsingConnectors {

  describe("A NotifyUsingConnector") {
    it("Should Notify through all the connectors the notification.") {
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

      When("try to add a new notification")
      val errors = notify(notification)

      Then("should not be errors at all")
      assert(errors.size === 0)

      notificationService.delete(notification.id)
      groupService.delete(group.id)
      roleService.delete(role.id)
    }

    it("Should fail when try to Notify through all the connectors the notification.") {
      Given("a Role")
      val role: Role = DomainSamples.roles(1000)
      val roleService: Service[Role] = new RoleService()
      roleService.add(role)

      And("a Member")
      val member: Member = DomainSamples.members(1001)
      val memberService: Service[Member] = new MemberService()
      memberService.add(member)

      And("a Group with a valid member")
      val group: Group = DomainSamples.groups(1006)
      val groupService: Service[Group] = new GroupService()
      groupService.add(group)

      And("a Notification")
      val notification: Notification = DomainSamples.notifications(1007)

      When("try to add a new notification")
      val errors = notify(notification)

      Then("should had failed")
      assert(errors.size > 0)

      groupService.delete(group.id)
      memberService.delete(member.id)
      roleService.delete(role.id)
    }
  }

}
