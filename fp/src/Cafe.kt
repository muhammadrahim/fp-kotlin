class Cafe {
    fun buyCoffee(cc: CreditCard): Pair<Coffee, Charge>
    = TODO()

    fun buyCoffees(cc: CreditCard, n: Int): Pair<List<Coffee>, Charge> {
        val purchases: List<Pair<Coffee, Charge>> = List(n) { buyCoffee(cc) }
        val (coffees, charges) = purchases.unzip()
        charges.coalesce()
        return Pair(coffees, charges.reduce{ c1, c2 -> c1.combine(c2) })
    }

    fun factorial(i: Int): Int {
        tailrec fun go(n: Int, acc:Int): Int =
                if (n <= 0) acc
        else go(n-1, n*acc)
        return go(i,1)
    }

    fun List<Charge>.coalesce(): List<Charge> =
            this.groupBy { it.cc }.values
                    .map { it.reduce { a, b -> a.combine(b)} }
}

data class Charge(val cc: CreditCard, private val price: Int) {

    fun combine(other: Charge): Charge =
            if (cc == other.cc) {
                Charge(cc, price + other.price)
            } else throw Exception(
                    "Cannot combine charges to different cards"
            )
}

interface CreditCard

class Payments {
    fun charge(cc: CreditCard, price: Any) {
        TODO("Not yet implemented")
    }
}

class Coffee(val price: Int)
