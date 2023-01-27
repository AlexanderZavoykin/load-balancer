package practice.load.balance

open class SimpleNode(
    override val host: String,
    override val port: Int,
) : Node {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SimpleNode

        if (host != other.host) return false
        if (port != other.port) return false

        return true
    }

    override fun hashCode(): Int {
        var result = host.hashCode()
        result = 31 * result + port
        return result
    }

    override fun toString(): String {
        return "SimpleNode(host='$host', port=$port)"
    }


}