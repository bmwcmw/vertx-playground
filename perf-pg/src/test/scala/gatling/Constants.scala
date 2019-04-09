package com.databerries.gatling

import java.nio.file.Paths

import io.gatling.core.Predef._
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
import io.gatling.http.Predef._

import scala.concurrent.duration._

object Constants {
  val responseSucceedPercentage = 100
  val numberOfUsers: Int = System.getProperty("numberOfUsers").toInt
  val maxRequestPerSecond: Int = System.getProperty("maxRequestPerSecond").toInt
  val duration: FiniteDuration = System.getProperty("durationMinutes").toInt.minutes
  val responseTimeMs = 40

  val AcceptedRequest = 0.025
  val RefusedRequest = 1 - AcceptedRequest

  val RefusedDeviceId = 0.87
  val RefusedWrongBanner = 0.06
  val RefusedDeviceNull = 0.06
  val RefusedBidFloor = 0.01

  private val pause: FiniteDuration = System.getProperty("pauseBetweenRequestsMs").toInt.millisecond
  private val url: String = System.getProperty("host")

  val httpProtocol = http
    .baseURL(url)
    .check(status.in(200, 204))
    .header("Content-Type", "application/json")
    .header("Keep-Alive", "2000").shareConnections

  def createBidderScenario(name: String, chains: ChainBuilder*): ScenarioBuilder = {
    scenario(name)
      .feed(UuidFeeder.feeder)
      .forever() {
        pace(Constants.pause)
          .exec(chains)
      }
  }
}
