package practice.load.balancer.server

import kotlinx.serialization.Serializable

@Serializable
data class NodeInfo(
    val host: String,
    val port: Int,
)