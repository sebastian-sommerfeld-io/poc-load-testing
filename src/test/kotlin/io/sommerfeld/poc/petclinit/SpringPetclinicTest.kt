package io.sommerfeld.poc.petclinit

import io.gatling.javaapi.core.CoreDsl.* // ktlint-disable no-wildcard-imports
import io.gatling.javaapi.core.Simulation
import io.gatling.javaapi.http.HttpDsl.* // ktlint-disable no-wildcard-imports
import java.time.Duration

/**
 * Gatling load test for the Spring Petclinic application. This test simulates a user journey
 * through the application.
 */
class SpringPetclinicTest : Simulation() {

    val SIMULATION_RUNTIME = Duration.ofSeconds(20)
    val BASE_URL = "http://system-under-test:8080"

    /**
     * The HTTP protocol configuration for the test. This configuration sets the base URL and
     * headers for the HTTP requests.
     */
    val httpProtocol =
        http.baseUrl(BASE_URL)
            .acceptHeader("text/html,application/xhtml+xml,application/xml")
            .acceptEncodingHeader("gzip, deflate")
            .acceptLanguageHeader("en-US,en;q=0.5")
            .userAgentHeader(
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3",
            )

    /**
     * The scenario that simulates a user journey through the application.
     */
    val navigateAppScenario =
        scenario("navigate app")
            .exec(
                http("get homepage")
                    .get("/"),
            )
            .pause(Duration.ofSeconds(5))
            .exec(
                http("get find owners page")
                    .get("/owners/find"),
            )
            .pause(Duration.ofSeconds(5))
            .exec(
                http("get owners results page")
                    .get("/owners?lastName="),
            )
            .pause(Duration.ofSeconds(8))
            .exec(
                http("get owner details page")
                    .get("/owners/7"),
            )
            .pause(Duration.ofSeconds(12))
            .exec(
                http("get veterinarians page")
                    .get("/vets"),
            )
            .pause(Duration.ofSeconds(5))
            .exec(
                http("get veterinarians as json")
                    .get("/vets.json"),
            )

    /**
     * The test setup that configures the load with wich the scenario is executed.
     */
    init {
        setUp(
            navigateAppScenario.injectOpen(
                nothingFor(4),
                atOnceUsers(10),
                rampUsers(10)
                    .during(SIMULATION_RUNTIME),
                constantUsersPerSec(20)
                    .during(SIMULATION_RUNTIME),
                constantUsersPerSec(20)
                    .during(SIMULATION_RUNTIME)
                    .randomized(),
                rampUsersPerSec(10).to(20)
                    .during(SIMULATION_RUNTIME),
                rampUsersPerSec(10).to(20)
                    .during(SIMULATION_RUNTIME)
                    .randomized(),
                stressPeakUsers(1000)
                    .during(SIMULATION_RUNTIME),
            ),
        )
            .protocols(httpProtocol)
    }
}
