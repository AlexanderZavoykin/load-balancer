package practice.load.balancer

class RoundRobinLoadBalancer<T : Node>(
    nodeList: List<T> = emptyList(),
) : AbstractLockableLoadBalancer<T>(nodeList) {

    private var carry = 0

    override fun getNextNode(): T {
        writeLock.lock()
        try {
            carry++
            if (carry == nodeList.size) carry = 0
            return nodeList[carry]
        } finally {
            writeLock.unlock()
        }
    }

    override fun getStrategy(): LoadBalancerStrategy = LoadBalancerStrategy.ROUND_ROBIN

}