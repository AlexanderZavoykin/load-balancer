package practice.load.balancer

import io.ktor.server.netty.*
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.inject

object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        startKoin {
            modules(
                serviceModule,
                serverModule,
            )
        }

        val server: NettyApplicationEngine by inject(NettyApplicationEngine::class.java)
        server.start(wait = true)
    }

}
