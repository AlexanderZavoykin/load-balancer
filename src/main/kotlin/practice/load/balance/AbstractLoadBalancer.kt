package practice.load.balance

import java.util.concurrent.locks.ReentrantReadWriteLock

abstract class AbstractLoadBalancer<T : Node>(
    nodeList: List<T>,
) : LoadBalancer<T> {

    protected val nodes: MutableList<T> = ArrayList(nodeList)

    private val readWriteLock = ReentrantReadWriteLock()
    protected val writeLock: ReentrantReadWriteLock.WriteLock = readWriteLock.writeLock()
    protected val readLock: ReentrantReadWriteLock.ReadLock = readWriteLock.readLock()

    init {
        require(nodes.isNotEmpty()) { " Load balancer should be constructed with at least 1 node" }
    }

    override fun register(node: T): Boolean {
        writeLock.lock()
        try {
            return nodes.add(node)
        } finally {
            writeLock.unlock()
        }
    }

    override fun unregister(node: T): Boolean {
        writeLock.lock()
        try {
            return nodes.remove(node)
        } finally {
            writeLock.unlock()
        }
    }

    override fun getNodes(): List<T> {
        readLock.lock()
        try {
            return nodes.toList()
        } finally {
            readLock.unlock()
        }
    }

}