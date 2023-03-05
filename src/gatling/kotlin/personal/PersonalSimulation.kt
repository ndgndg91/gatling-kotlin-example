package personal

import io.gatling.javaapi.core.CoreDsl.*
import io.gatling.javaapi.core.Simulation
import io.gatling.javaapi.http.HttpDsl.http

class PersonalSimulation : Simulation() {
    private val httpProtocol1010 = http.baseUrl("http://localhost:1010").contentTypeHeader("application/json")
    private val httpProtocol2020 = http.baseUrl("http://localhost:2020").contentTypeHeader("application/json")
    private val httpProtocol3030 = http.baseUrl("http://localhost:3030").contentTypeHeader("application/json")
    private val httpProtocol4040 = http.baseUrl("http://localhost:4040").contentTypeHeader("application/json")


    //language=json
    private val body =
        "{\n  \"items\": [\n    {\n      \"productId\": 1,\n      \"title\": \"남동길\",\n      \"quantity\": 1,\n      \"price\": \"1500.0\"\n    }\n  ]\n}"

    private val post1010 = exec(
        http("PlaceOrder1010").post("/api/orders")
            .body(StringBody(body))
    )
    private val post2020 = exec(
        http("PlaceOrder2020").post("/api/orders")
            .body(StringBody(body))
    )
    private val post3030 = exec(
        http("PlaceOrder3030").post("/api/orders")
            .body(StringBody(body))
    )
    private val post4040 = exec(
        http("PlaceOrder4040").post("/api/orders")
            .body(StringBody(body))
    )

    private val placeOrderUsers1010 = scenario("placeOrderTarget1010").exec(post1010)
    private val placeOrderUsers2020 = scenario("placeOrderTarget2020").exec(post2020)
    private val placeOrderUsers3030 = scenario("placeOrderTarget3030").exec(post3030)
    private val placeOrderUsers4040 = scenario("placeOrderTarget4040").exec(post4040)


    init {
        setUp(
            placeOrderUsers1010.injectOpen(rampUsers(20).during(5)).protocols(httpProtocol1010),
            placeOrderUsers2020.injectOpen(rampUsers(20).during(5)).protocols(httpProtocol2020),
            placeOrderUsers3030.injectOpen(rampUsers(20).during(5)).protocols(httpProtocol3030),
            placeOrderUsers4040.injectOpen(rampUsers(20).during(5)).protocols(httpProtocol4040),
        )
    }
}