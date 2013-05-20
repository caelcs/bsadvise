package ar.com.caeldev.bsacore.config

import com.typesafe.config._

// we have a constructor allowing the app to provide a custom Config
class ConfigContext(config: Config) {

  // This uses the standard default Config, if none is provided,
  // which simplifies apps willing to use the defaults
  def this() {
    this(ConfigFactory.load())
  }

  def this(configName: String) {
    this(ConfigFactory.load(configName))
  }

  def printSetting(path: String) {
    println("The setting '" + path + "' is: " + config.getString(path))
  }

  def get(key: String): String = {
    config.getString(key)
  }
}
