open class Tea(val cost: Int, val volume: Int) {
    override fun toString(): String {
        return "cost=$cost, volume=$volume"
    }
}

class BlackTea(acost: Int, avolume: Int): Tea(acost,avolume){
    override fun toString(): String {
        return "BlackTea{cost=$cost, volume=$volume}"
    }
}