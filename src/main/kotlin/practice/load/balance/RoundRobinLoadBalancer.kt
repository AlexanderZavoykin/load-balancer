package practice.load.balance

class RoundRobinLoadBalancer<T : Node>(
    nodeList: List<T>,
) : AbstractLoadBalancer<T>(nodeList) {

    private var carry = 0

    override fun getNextNode(): T {
        writeLock.lock()
        try {
            carry++
            if (carry == nodes.size) carry = 0
            return nodes[carry]
        } finally {
            writeLock.unlock()
        }
    }

    override fun getStrategy(): String = "ROUND ROBIN"

}