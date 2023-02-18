package practice.load.balancer

class RandomLoadBalancer<T : Node>(
    nodeList: List<T> = emptyList(),
) : AbstractLockableLoadBalancer<T>(nodeList) {

    override fun getNextNode(): T = nodeList.random()

    override fun getStrategy(): LoadBalancerStrategy = LoadBalancerStrategy.RANDOM

}