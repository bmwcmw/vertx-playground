package gatling.scenarios

import com.databerries.gatling.Constants
import io.gatling.core.Predef._
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
import io.gatling.http.Predef._

object RawEndpoint {
  private val name = "RAW post"

  val uri = "/raw"

  val req: ChainBuilder = exec(
    http(name)
      .post(uri)
      .body(StringBody("ok"))
  )

  val scenario: ScenarioBuilder = Constants.createBidderScenario(name, req)
}
