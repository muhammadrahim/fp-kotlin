sealed class LinkedList<out A> {
    companion object {
        fun <A> of(vararg aa: A): LinkedList<A> {
            val tail = aa.sliceArray(1 until aa.size)
            return if (aa.isEmpty()) Nil else Cons(aa[0], of(*tail))
        }

        fun sum(ints: LinkedList<Int>): Int =
                when (ints) {
                    is Nil -> 0
                    is Cons -> ints.head + sum(ints.tail)
                }

        fun product(doubles: LinkedList<Double>): Double =
                when (doubles) {
                    is Nil -> 1.0
                    is Cons -> doubles.head * product(doubles.tail)
                }

        fun <A> tail(xs: LinkedList<A>): LinkedList<A> =
                when (xs) {
                    is Cons -> xs.tail
                    is Nil -> throw IllegalStateException("no")
                }

        fun <A> append(a1: LinkedList<A>, a2: LinkedList<A>): LinkedList<A> =
                when (a1) {
                    is Nil -> a2
                    is Cons -> Cons(a1.head, append(a1.tail, a2))
                }
    }
}

object Nil : LinkedList<Nothing>()

data class Cons<out A>(
        val head: A,
        val tail: LinkedList<A>
) : LinkedList<A>()

val EX_1: LinkedList<Double> = Nil
val EX_2: LinkedList<Int> = Cons(1, Nil)
val EX_3: LinkedList<String> = Cons("b", Cons("a", Nil))

val ints = LinkedList.of(1, 2, 3, 4)

fun sum(xs: LinkedList<Int>): Int =
        when (xs) {
            is Nil -> 0
            is Cons -> xs.head + sum(xs.tail)
        }