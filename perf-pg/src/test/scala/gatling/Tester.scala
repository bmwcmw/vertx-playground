package com.databerries.gatling

import com.databerries.gatling.scenarios._
import gatling.scenarios.{JsonEndpoint, RawEndpoint, JsonWrongRequest}
import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation

import scala.concurrent.duration._

class Tester extends Simulation {
  private val rampUpTime: FiniteDuration = 10.seconds

  setUp(
    JsonEndpoint.scenario.inject(rampUsers((Constants.numberOfUsers * Constants.AcceptedRequest * 0.25).round.toInt) over rampUpTime), // 25% of accepted request
    RawEndpoint.scenario.inject(rampUsers((Constants.numberOfUsers * Constants.AcceptedRequest * 0.25).round.toInt) over rampUpTime),
    WrongEndpoint.scenario.inject(rampUsers((Constants.numberOfUsers * Constants.AcceptedRequest * 0.25).round.toInt) over rampUpTime),
    JsonWrongRequest.scenario.inject(rampUsers((Constants.numberOfUsers * Constants.AcceptedRequest * 0.25).round.toInt) over rampUpTime),

    JsonEndpoint.scenario.inject(rampUsers((Constants.numberOfUsers * Constants.RefusedRequest * Constants.RefusedWrongBanner).round.toInt) over rampUpTime),
    RawEndpoint.scenario.inject(rampUsers((Constants.numberOfUsers * Constants.RefusedRequest * Constants.RefusedDeviceId).round.toInt) over rampUpTime),
    WrongEndpoint.scenario.inject(rampUsers((Constants.numberOfUsers * Constants.RefusedRequest * Constants.RefusedDeviceNull).round.toInt) over rampUpTime),
    JsonWrongRequest.scenario.inject(rampUsers((Constants.numberOfUsers * Constants.RefusedRequest * Constants.RefusedBidFloor).round.toInt) over rampUpTime)
  )
    .throttle(reachRps(Constants.maxRequestPerSecond) in rampUpTime, holdFor(Constants.duration))
    .protocols(Constants.httpProtocol)
    .maxDuration(Constants.duration)
    .assertions(
      global.responseTime.mean.lte(Constants.responseTimeMs),
      global.responseTime.percentile1.lte(Constants.responseTimeMs),
      global.responseTime.percentile2.lte(Constants.responseTimeMs),
      global.responseTime.percentile3.lte(Constants.responseTimeMs),
      global.successfulRequests.percent.gte(Constants.responseSucceedPercentage)
    )
}