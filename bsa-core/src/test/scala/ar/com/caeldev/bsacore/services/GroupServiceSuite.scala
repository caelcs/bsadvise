package ar.com.caeldev.bsacore.services

import org.scalatest.{ GivenWhenThen, FunSpec }
import ar.com.caeldev.bsacore.domain.{ Group, Role, Member, DomainSamples }

class GroupServiceSuite extends FunSpec with GivenWhenThen {

  describe("A GroupService") {
    it("Should create a group with members successfully") {
      Given("a valid role")
      val role: Role = DomainSamples.roles(1000)
      val roleService: Service[Role] = new RoleService()
      roleService.add(role)

      And("a valid member which has previous role")
      val member: Member = DomainSamples.members(1001)
      val memberService: Service[Member] = new MemberService()
      memberService.add(member)

      And("a new group Entity to be persist")
      val group: Group = DomainSamples.groups(1002)

      When("try to persist the Group and get it back from DB")
      val groupService: Service[Group] = new GroupService()
      val result: Group = groupService.add(group)

      Then("should contains valid data")
      assert(result.id == group.id)
      assert(result.name == group.name)
      assert(result.members == group.members)

      groupService.delete(result.id)
      roleService.delete(role.id)
    }

    it("Should create a group with no members successfully") {
      Given("a new group with no members")
      val group: Group = DomainSamples.groups(1006)

      When("try to persist the Group and get it back from DB")
      val groupService: Service[Group] = new GroupService()
      val result: Group = groupService.add(group)

      Then("should contains valid data")
      assert(result.id == group.id)
      assert(result.name == group.name)
      assert(result.members.size === 0)

      groupService.delete(result.id)
    }

    it("Should update a group with members successfully") {
      Given("a valid role")
      val role: Role = DomainSamples.roles(1000)
      val roleService: Service[Role] = new RoleService()
      roleService.add(role)

      And("a valid member which has previous role")
      val member: Member = DomainSamples.members(1001)
      val memberService: Service[Member] = new MemberService()
      memberService.add(member)

      And("a valid group")
      val group: Group = DomainSamples.groups(1002)
      val groupService: Service[Group] = new GroupService()
      groupService.add(group)

      When("try to update the Group")
      val groupToUpdate: Group = DomainSamples.groups(1003)
      val result: Group = groupService.update(groupToUpdate)

      Then("should have updated")
      assert(result.id == groupToUpdate.id)
      assert(result.name == groupToUpdate.name)
      assert(result.members == groupToUpdate.members)

      groupService.delete(result.id)
      roleService.delete(role.id)
    }

    it("Should delete a group with members successfully") {
      Given("a valid role")
      val role: Role = DomainSamples.roles(1000)
      val roleService: Service[Role] = new RoleService()
      roleService.add(role)

      And("a valid member which has previous role")
      val member: Member = DomainSamples.members(1001)
      val memberService: Service[Member] = new MemberService()
      memberService.add(member)

      And("a valid group")
      val group: Group = DomainSamples.groups(1002)
      val groupService: Service[Group] = new GroupService()
      groupService.add(group)

      When("try to delete the Group")
      groupService.delete(group.id)

      Then("it should have deleted")
      val result: Group = groupService.get(group.id)
      assert(result == null)

      roleService.delete(role.id)
    }
  }

}
