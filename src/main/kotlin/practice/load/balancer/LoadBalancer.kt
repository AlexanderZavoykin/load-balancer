package practice.load.balancer

interface LoadBalancer<T : Node> {

    /**
     * Register (add) a new node.
     *
     * @param node to register
     * @return true - if node is successfully registered, false - if node is already registered.
     */
    fun register(node: T): Boolean

    /**
     * Unregister (remove) a node.
     *
     * @param node to unregister
     * @return true - if node is successfully unregistered, false - if node was not present in balancer`s node collection.
     */
    fun unregister(node: T): Boolean

    /**
     * Get a node to request next with a strategy specific for this balancer.
     *
     * @return node to request next.
     */
    fun getNextNode(): T


    /**
     * Get list of all available nodes of this balancer.
     *
     * @return list of available nodes.
     */
    fun getNodes(): List<T>

    /**
     * Get strategy used by this load balancer.
     *
     * @return load balancer strategy.
     */
    fun getStrategy(): LoadBalancerStrategy

}