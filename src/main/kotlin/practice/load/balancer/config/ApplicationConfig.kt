package practice.load.balancer.config

data class ApplicationConfig(
    val port: Int,
    val nodes: List<String>,
    val balancerStrategy: String,
) : IConfig {

    init {
        require(nodes.isNotEmpty()) { "Load balancer must be started with at least 1 node" }
    }

}
