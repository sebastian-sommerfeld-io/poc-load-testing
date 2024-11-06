import {
    scenario,
    simulation,
    nothingFor,
    atOnceUsers,
    rampUsers,
    constantUsersPerSec,
    rampUsersPerSec,
    stressPeakUsers
} from "@gatling.io/core"
import { http } from "@gatling.io/http"


const SECONDS = 1
const MINUTES = 60 * SECONDS
const SIMULATION_RUNTIME = 10 * SECONDS


// Define the HTTP protocol configuration, the base URL and the headers.
function protocolConfig() {
    const url = "http://system-under-test:8080" // TODO make dynamic somehow (env var and reading file seems to not work)

    if (!url) {
        throw new Error("Cannot determine target system - BASE_URL environment variable seems to be missing")
    }

    return http.baseUrl(url)
        .acceptHeader("text/html,application/xhtml+xml,application/xml")
        .acceptEncodingHeader("gzip, deflate")
        .acceptLanguageHeader("en-US,en;q=0.5")
        .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3")
}


// Define the scenario to be executed. The scenario represents a user journey
// through the application.
function navigateAppScenario() {
    return scenario("navigate app")
        .exec(http("get homepage")
            .get("/"))
        .pause(5 * SECONDS)
        .exec(http("get find owners page")
            .get("/owners/find"))
        .pause(8 * SECONDS)
        .exec(http("get owners results page")
            .get("/owners?lastName="))
        .pause(12 * SECONDS)
        .exec(http("get owner details page")
            .get("/owners/7"))
        .exec(http("get veterinarians page")
            .get("/vets"))
        .pause(5 * SECONDS)
        .exec(http("get veterinarians as json")
            .get("/vets.json"))
}


// The simulation function defines the simulation to be executed. The simulation
// is the entry point to the Gatling simulation and represents the load test.
export default simulation((setUp) => {
    const scn = navigateAppScenario()

    setUp(
        scn.injectOpen(
            // constantUsersPerSec(1).during(SIMULATION_RUNTIME),
            // rampUsers(1).during(SIMULATION_RUNTIME)

            nothingFor(4),
            atOnceUsers(10),
            rampUsers(10).during(SIMULATION_RUNTIME),
            constantUsersPerSec(20).during(SIMULATION_RUNTIME),
            constantUsersPerSec(20).during(SIMULATION_RUNTIME).randomized(),
            rampUsersPerSec(10).to(20).during(SIMULATION_RUNTIME),
            rampUsersPerSec(10).to(20).during(SIMULATION_RUNTIME).randomized(),
            stressPeakUsers(1000).during(SIMULATION_RUNTIME)
        )
    ).protocols(protocolConfig())
})
