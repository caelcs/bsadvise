package ar.com.caeldev.bsacore.config

import org.scalatest.{ GivenWhenThen, FunSpec }

class ConfigContextSuite extends FunSpec with GivenWhenThen {

  describe("A ConfigContext") {
    it("should get a value passing a valid key from a conf file") {
      Given("a conf file")
      val fileName: String = "test.conf"

      And("a ConfigContext instance using the conf file")
      val config: ConfigContext = new ConfigContext(fileName)

      When("get a value using a valid key")
      val value: String = config.get("db.port")

      Then("the value must be not empty")
      assert(!value.isEmpty)

      And("must have the correct value")
      assert(value === "27017")
    }

    it("should not get a value passing an invalid key from a conf file") {
      Given("a conf file")
      val fileName: String = "test.conf"

      And("a ConfigContext instance using the conf file")
      val config: ConfigContext = new ConfigContext(fileName)

      When("get a value using an invalid key it raise ConfigException")
      intercept[com.typesafe.config.ConfigException] {
        val value: String = config.get("db1.port")
      }
    }

    it("should fail trying to get a value with a key using a wrong conf filename") {
      Given("a wrong conf file")
      val fileName: String = "test1.conf"

      When("a ConfigContext instance using the a wrong conf file")
      val config: ConfigContext = new ConfigContext(fileName)

      Then("get a value using an invalid key it raise ConfigException")
      intercept[com.typesafe.config.ConfigException] {
        val value: String = config.get("db.port")
      }
    }
  }

}
