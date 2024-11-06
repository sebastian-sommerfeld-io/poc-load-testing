import {
    constantUsersPerSec,
    rampUsers,
    scenario,
    exec,
    simulation
} from "@gatling.io/core";
import { http } from "@gatling.io/http";


const SECONDS = 1;
const MINUTES = 60 * SECONDS;


export default simulation((setUp) => {
    const httpProtocol =
        http.baseUrl("http://system-under-test:8080")
        .acceptHeader("text/html,application/xhtml+xml,application/xml")
        .acceptEncodingHeader("gzip, deflate")
        .acceptLanguageHeader("en-US,en;q=0.5")
        .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3")

    const navigateAppScenario =
        exec(http("homepage")
            .get("/"))
            .pause(5 * SECONDS)
        .exec(http("find owners page")
            .get("/owners/find"))
            .pause(8 * SECONDS)
        .exec(http("owners results page")
            .get("/owners?lastName="))
            .pause(12 * SECONDS)
        .exec(http("owner details page")
            .get("/owners/7"))
        .exec(http("find veterinarians page")
            .get("/vets"))
            .pause(5 * SECONDS)
        .exec(http("view veterinarians as json")
            .get("/vets.json"))
            .pause(20 * SECONDS);

    const steadyLoad = scenario("steady load").exec(navigateAppScenario);
    const increasingLoad = scenario("increasing load").exec(navigateAppScenario);

    setUp(
        steadyLoad.injectOpen(constantUsersPerSec(15).during(120 * MINUTES)),
        increasingLoad.injectOpen(rampUsers(15).during(120 * MINUTES))
    ).protocols(httpProtocol);
});
