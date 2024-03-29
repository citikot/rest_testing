package it.discovery.rest.performance.gatling;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.atOnceUsers;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.http;

public class BookControllerSimulation extends Simulation {

    HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8080");

    ScenarioBuilder scn = scenario("BookControllerSimulation")
            .exec(http("request_1")
                    .get("/api/books"))
            .pause(0, 3);

    {
        setUp(
                scn.injectOpen(atOnceUsers(2500))
                //TODO add close work load
        ).protocols(httpProtocol);
    }

}
