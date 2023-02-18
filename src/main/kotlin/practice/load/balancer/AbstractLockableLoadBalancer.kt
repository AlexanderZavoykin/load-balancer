package practice.load.balancer

import java.util.*
import java.util.concurrent.locks.ReentrantReadWriteLock

abstract class AbstractLockableLoadBalancer<T : Node>(
    nodeList: List<T> = emptyList(),
) : LoadBalancer<T> {

    protected val nodeList: MutableList<T> = ArrayList(nodeList)

    private val readWriteLock = ReentrantReadWriteLock()
    private val readLock: ReentrantReadWriteLock.ReadLock = readWriteLock.readLock()
    protected val writeLock: ReentrantReadWriteLock.WriteLock = readWriteLock.writeLock()

    init {
        require(this.nodeList.isNotEmpty()) { "Load balancer should be constructed with at least 1 node" }
    }

    override fun register(node: T): Boolean {
        writeLock.lock()
        try {
            return nodeList.add(node)
        } finally {
            writeLock.unlock()
        }
    }

    override fun unregister(node: T): Boolean {
        writeLock.lock()
        try {
            return nodeList.remove(node)
        } finally {
            writeLock.unlock()
        }
    }

    override fun getNodes(): List<T> {
        readLock.lock()
        try {
            return Collections.unmodifiableList(nodeList)
        } finally {
            readLock.unlock()
        }
    }

}
