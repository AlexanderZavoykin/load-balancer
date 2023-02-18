package practice.load.balancer

class LoadBalancerFactory(
    val nodes: List<SimpleNode>,
) {

    fun getLoadBalancer(strategy: LoadBalancerStrategy): LoadBalancer<*> =
        when (strategy) {
            LoadBalancerStrategy.RANDOM -> RandomLoadBalancer(nodes)
            LoadBalancerStrategy.ROUND_ROBIN -> RoundRobinLoadBalancer(nodes)
            LoadBalancerStrategy.WEIGHTED -> WeightedLoadBalancer(nodes.map { it.toWeightedNode() })
        }

    private fun SimpleNode.toWeightedNode(): WeightedNode = WeightedNode(host, port, 0)

}