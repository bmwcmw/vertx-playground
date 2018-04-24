package gatling.scenarios

import com.databerries.gatling.Constants
import io.gatling.core.Predef._
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
import io.gatling.http.Predef._

import scala.util.Random

object WrongRequest {
  val endPoint = List(EndpointJson.uri, EndpointRaw.uri)

  val req: ChainBuilder = exec(
    http("wrongdevice_request")
      .post(Random.shuffle(endPoint).head)
      .body(ElFileBody("request_bad.json")).asJSON
  )

  val scenario: ScenarioBuilder = Constants.createBidderScenario("Wrong Device", req)
}
