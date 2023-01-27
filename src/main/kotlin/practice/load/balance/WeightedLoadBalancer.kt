package practice.load.balance

import java.util.concurrent.locks.ReentrantReadWriteLock

class WeightedLoadBalancer constructor(
    nodeList: List<WeightedNode>,
) : LoadBalancer<WeightedNode> {

    private val nodesWithCounters: MutableList<NodeWithCounter> =
        nodeList
            .map { NodeWithCounter(it) }
            .toMutableList()

    private var carry = 0

    private val readWriteLock = ReentrantReadWriteLock()
    private val writeLock: ReentrantReadWriteLock.WriteLock = readWriteLock.writeLock()
    private val readLock: ReentrantReadWriteLock.ReadLock = readWriteLock.readLock()

    override fun register(node: WeightedNode): Boolean {
        writeLock.lock()
        try {
            return nodesWithCounters.add(NodeWithCounter(node))
        } finally {
            writeLock.unlock()
        }
    }

    override fun unregister(node: WeightedNode): Boolean {
        writeLock.lock()
        try {
            return nodesWithCounters.remove(NodeWithCounter(node))
        } finally {
            writeLock.unlock()
        }
    }

    override fun getNextNode(): WeightedNode {
        writeLock.lock()
        try {
            return getNext()
        } finally {
            writeLock.unlock()
        }
    }

    private fun getNext(): WeightedNode {
        carry++
        if (carry == nodesWithCounters.size) carry = 0

        val nodeWithCounter = nodesWithCounters[carry]

        if (nodeWithCounter.isRunOut()) {
            if (allRunOut()) {
                setCounterStartValues()
                carry = 0
                return nodesWithCounters[carry].take()
            }

            return getNext()
        } else {
            return nodeWithCounter.take()
        }
    }

    private fun allRunOut(): Boolean = nodesWithCounters.all { it.isRunOut() }

    private fun setCounterStartValues(): Unit = nodesWithCounters.forEach { it.reset() }

    override fun getNodes(): List<WeightedNode> {
        readLock.lock()
        try {
            return nodesWithCounters.map { it.node }
        } finally {
            readLock.unlock()
        }
    }

    override fun getStrategy(): String = "WEIGHTED"

    private data class NodeWithCounter(
        val node: WeightedNode,
    ) {

        private var counter: Int = node.weight

        fun isRunOut(): Boolean = counter == 0

        fun reset(): Unit {
            counter = node.weight
        }

        fun take(): WeightedNode {
            counter--
            return node
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as NodeWithCounter

            if (node != other.node) return false

            return true
        }

        override fun hashCode(): Int {
            return node.hashCode()
        }

    }

}