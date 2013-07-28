package ar.com.caeldev

import api.Api
import core.{ BootedCore, CoreActors }
import web.Web
import spray.servlet.WebBoot

class Rest extends WebBoot with BootedCore with CoreActors with Api with Web