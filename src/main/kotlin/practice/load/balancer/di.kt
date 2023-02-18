package practice.load.balancer

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.koin.dsl.module
import practice.load.balancer.config.ApplicationConfig
import practice.load.balancer.config.IConfig
import practice.load.balancer.server.configureRoutings
import practice.money.transfer.server.configureSerialization
import practice.money.transfer.server.configureValidation

val appConfig = IConfig.load<ApplicationConfig>()

val serviceModule = module {
    val strategy = LoadBalancerStrategy.valueOf(appConfig.balancerStrategy)

    val nodes = appConfig.nodes
        .map { it.split(":") }
        .filter { it.size == 2 }
        .map { SimpleNode(it[0], it[1].toInt()) }

    single {
        LoadBalancerFactory(nodes)
            .getLoadBalancer(strategy)
    }
}

val serverModule = module {
    val server = embeddedServer(Netty, appConfig.port) {
        configureSerialization()
        configureValidation()
        configureRoutings()
    }
    single { server }
}

