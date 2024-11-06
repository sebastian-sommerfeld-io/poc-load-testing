package io.sommerfeld.petclinic;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import java.time.Duration;

/**
 * Gatling load test for the Spring Petclinic application.
 * This test simulates a user journey through the application.
 */
public class SpringPetclinicTest extends Simulation {

    private static final Duration SIMULATION_RUNTIME = Duration.ofSeconds(20);
    private static final String BASE_URL = "http://system-under-test:8080"; // TODO base url env var from docker-compose

    /**
     * Constructor. Define the scenario to be executed.
     */
    public SpringPetclinicTest() {
        ScenarioBuilder scn = navigateAppScenario();

        {
            setUp(
                scn.injectOpen(
                    // See https://docs.gatling.io/reference/script/core/injection/#open-model
                    nothingFor(4),
                    atOnceUsers(10),
                    rampUsers(10).during(SIMULATION_RUNTIME),
                    constantUsersPerSec(20).during(SIMULATION_RUNTIME),
                    constantUsersPerSec(20).during(SIMULATION_RUNTIME).randomized(),
                    rampUsersPerSec(10).to(20).during(SIMULATION_RUNTIME),
                    rampUsersPerSec(10).to(20).during(SIMULATION_RUNTIME).randomized(),
                    stressPeakUsers(1000).during(SIMULATION_RUNTIME)
                )
            ).protocols(this.httpProtocol());
        }
    }

    /**
     * Define the HTTP protocol configuration, the base URL and the headers.
     *
     * @return the HTTP protocol configuration
     */
    private HttpProtocolBuilder httpProtocol() {
        return http.baseUrl(BASE_URL)
            .acceptHeader("text/html,application/xhtml+xml,application/xml")
            .acceptEncodingHeader("gzip, deflate")
            .acceptLanguageHeader("en-US,en;q=0.5")
            .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3");
    }

    /**
     * Define the scenario to be executed. The scenario represents a user
     * journey through the application.
     * @return the scenario
     */
    private ScenarioBuilder navigateAppScenario() {
        return scenario("navigate app")
            .exec(http("get homepage").get("/"))
            .pause(Duration.ofSeconds(5))
            .exec(http("get find owners page").get("/owners/find"))
            .pause(Duration.ofSeconds(5))
            .exec(http("get find owners page").get("/owners/find"))
            .pause(Duration.ofSeconds(8))
            .exec(http("get owners results page").get("/owners?lastName="))
            .pause(Duration.ofSeconds(12))
            .exec(http("get owner details page").get("/owners/7"))
            .exec(http("get veterinarians page").get("/vets"))
            .pause(Duration.ofSeconds(5))
            .exec(http("get veterinarians as json").get("/vets.json"))
        ;
    }
}
