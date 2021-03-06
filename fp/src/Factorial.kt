import Example.factorial

object Example {

    fun abs(n: Int): Int = if (n < 0) -n else n

    fun factorial(i: Int): Int {
        fun go(n: Int, acc: Int): Int =
                if (n <= 0) acc
                else go(n - 1, n * acc)
        return go(i, 1)
    }

    fun formatResult(name: String, n: Int, f: (Int) -> Int): String {
        val msg = "The %s of %d is %d."
        return msg.format(name, n, f(n))
    }

    fun <A> findFirst(xs: Array<A>, p: (A) -> Boolean): Int {
        tailrec fun loop(n: Int): Int =
                when {
                    n >= xs.size -> -1
                    p(xs[n]) -> n
                    else -> loop(n + 1)
                }
        return loop(0)
    }

    fun <A, B, C> partial1(a: A, f: (A, B) -> C): (B) -> C = { b -> f(a, b) }
}

fun main() {
    println(Example.formatResult("factorial", 7, ::factorial))
    println(Example.formatResult("abs", -42) { if (it < 0) -it else it })
}
