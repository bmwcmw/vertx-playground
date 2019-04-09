package gatling.scenarios

import com.databerries.gatling.Constants
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}

object JsonEndpoint {
  private val name = "JSON POST"

  val uri = "/json"

  val req: ChainBuilder = exec(
    http(name)
      .post(uri)
      .body(ElFileBody("request.json")).asJSON
  )

  val scenario: ScenarioBuilder = Constants.createBidderScenario(name, req)
}
