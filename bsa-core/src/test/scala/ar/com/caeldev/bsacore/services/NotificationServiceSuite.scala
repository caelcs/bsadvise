package ar.com.caeldev.bsacore.services

import org.scalatest.{ GivenWhenThen, FunSpec }
import ar.com.caeldev.bsacore.domain._
import ar.com.caeldev.bsacore.domain.Role
import ar.com.caeldev.bsacore.domain.Member
import ar.com.caeldev.bsacore.domain.Notification

class NotificationServiceSuite extends FunSpec with GivenWhenThen {
  describe("A Notification Service") {
    it("Should notify a message to different connectors.") {
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

      When("try to add a new notification")
      val notificationService: Service[Notification] = new NotificationService()
      val result = notificationService.add(notification)

      Then("should get from backend the same notification")
      assert(result.id === notification.id)
      assert(result.message === notification.message)
      assert(result.receivers_group_id === notification.receivers_group_id)
      assert(result.sender_id === notification.sender_id)
      assert(result.status === notification.status)
      assert(result.subject === notification.subject)

      notificationService.delete(notification.id)
      groupService.delete(group.id)
      roleService.delete(role.id)

    }

  }
}
