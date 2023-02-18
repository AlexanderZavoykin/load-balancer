package practice.load.balancer.server

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent
import practice.load.balancer.LoadBalancer
import practice.load.balancer.Node
import practice.load.balancer.SimpleNode

val balancer: LoadBalancer<Node> by KoinJavaComponent.inject(LoadBalancer::class.java)

fun Application.configureRoutings() {

    routing {
        route("/node") {
            post {
                val nodeInfo = call.receive<NodeInfo>()
                val node = SimpleNode(nodeInfo.host, nodeInfo.port)
                val code = if (balancer.register(node)) HttpStatusCode.OK else HttpStatusCode.Conflict
                call.respond(code)
            }

            delete {
                val nodeInfo = call.receive<NodeInfo>()
                val node = SimpleNode(nodeInfo.host, nodeInfo.port)
                val code = if (balancer.unregister(node)) HttpStatusCode.OK else HttpStatusCode.NotFound
                call.respond(code)
            }

            get {
                call.respond(HttpStatusCode.OK, balancer.getNextNode().toInfo())
            }

            get("/all") {
                call.respond(HttpStatusCode.OK, balancer.getNodes().map { it.toInfo() })
            }
        }
    }

}

private fun Node.toInfo(): NodeInfo = NodeInfo(host, port)