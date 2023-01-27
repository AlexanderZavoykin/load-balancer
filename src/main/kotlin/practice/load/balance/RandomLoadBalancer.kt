package practice.load.balance

class RandomLoadBalancer<T : Node>(
    nodeList: List<T>,
) : AbstractLoadBalancer<T>(nodeList) {

    override fun getNextNode(): T = nodes.random()

    override fun getStrategy(): String = "RANDOM"

}