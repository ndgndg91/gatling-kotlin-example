package openapi

import io.gatling.javaapi.core.CoreDsl
import io.gatling.javaapi.core.Simulation
import io.gatling.javaapi.http.HttpDsl.http

class V1PublicApiSimulation : Simulation() {

    private val getOrderBooks = CoreDsl.exec(
        http("GetBTCOrderBook").get("/orderbook")
    ).pause(1)
        .exec(
            http("GetETHOrderBook").get("/orderbook?currency=ETH")
        ).pause(1)
    private val getTickers = CoreDsl.exec(
        http("GetBTCTicker").get("/ticker")
    ).pause(1)
        .exec(
            http("GetETHTicker").get("/ticker")
        ).pause(1)
    private val getUtcTickers = CoreDsl.exec(
        http("GetBTCUtcTicker").get("/ticker_utc")
    ).pause(1)
        .exec(
            http("GetETHUtcTicker").get("/ticker_utc?currency=ETH")
        ).pause(1)
    private val getRecentTrades = CoreDsl.exec(
        http("GetBTCRecentTrade").get("/trades")
    ).pause(1)
        .exec(
            http("GetETHRecentTrade").get("/trades?currency=ETH")
        ).pause(1)
    private val httpProtocol =
        http.baseUrl("http://api.coindev.co.kr")

    private val orderBookUsers = CoreDsl.scenario("orderBookUsers").exec(getOrderBooks)
    private val tickerUsers = CoreDsl.scenario("tickerUsers").exec(getTickers)
    private val tickerUtcUsers = CoreDsl.scenario("tickerUtcUsers").exec(getUtcTickers)
    private val recentTradeUsers = CoreDsl.scenario("recentTradeUsers").exec(getRecentTrades)

    init {
        setUp(
            orderBookUsers.injectOpen(CoreDsl.rampUsers(20).during(10)),
            tickerUsers.injectOpen(CoreDsl.rampUsers(20).during(10)),
            tickerUtcUsers.injectOpen(CoreDsl.rampUsers(20).during(10)),
            recentTradeUsers.injectOpen(CoreDsl.rampUsers(20).during(10))
        ).protocols(httpProtocol)
    }
}