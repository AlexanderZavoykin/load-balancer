package practice.load.balance

class WeightedNode(
    override val host: String,
    override val port: Int,
    val weight: Int = 0,
) : SimpleNode(host, port) {

    override fun toString(): String {
        return "WeightedNode(host='$host', port=$port, weight=$weight)"
    }

}