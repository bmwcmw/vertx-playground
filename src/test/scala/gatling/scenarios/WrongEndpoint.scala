package com.databerries.gatling.scenarios

import scala.util.Random
import com.databerries.gatling.Constants
import gatling.scenarios.{EndpointJson, EndpointRaw}
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}

object WrongEndpoint {
  val endPoint = List(EndpointJson.uri, EndpointRaw.uri)
  private val name = "RAW post"


  val req: ChainBuilder = exec(
    http("wrongdevice_request")
      .post(Random.shuffle(endPoint).head)
      .body(ElFileBody("request_bad.json")).asJSON
  )

  val scenario: ScenarioBuilder = Constants.createBidderScenario("Wrong Device", req)
}
