package com.databerries.gatling.scenarios

import com.databerries.gatling.Constants
import io.gatling.core.Predef._
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
import io.gatling.http.Predef._

object WrongEndpoint {
  private val name = "Wrong endpoint"

  val uri = "/invalid"

  val req: ChainBuilder = exec(
    http(name)
      .post(uri)
      .body(StringBody("nothing"))
  )

  val scenario: ScenarioBuilder = Constants.createBidderScenario(name, req)
}
