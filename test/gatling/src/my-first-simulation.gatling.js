/*
 * See https://docs.gatling.io/tutorials/scripting-intro-js/#install-gatling
 */

import {
    scenario,
    simulation,
    constantUsersPerSec
} from "@gatling.io/core";
import { http } from "@gatling.io/http";


// The simulation function takes the setUp function as an argument, which is used to write a script.
export default simulation((setUp) => {
    const httpProtocol =
        http.baseUrl("https://computer-database.gatling.io")
            .acceptHeader("text/html");

      const myScenario = scenario("My Scenario")
        .exec(http("Request 1").get("/computers/"));

    // Run the scenario with 2 users per second for 60 seconds
    setUp(myScenario.injectOpen(constantUsersPerSec(2).during(60)))
        .protocols(httpProtocol);
});
