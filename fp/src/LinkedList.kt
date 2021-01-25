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

        fun <A, B> foldRight(xs: LinkedList<A>, z: B, f: (A, B) -> B): B =
                when (xs) {
                    is Nil -> z
                    is Cons -> f(xs.head, foldRight(xs.tail, z, f))
                }

        fun <A, B> sum2(ints: LinkedList<Int>): Int =
                foldRight(ints, 0) { a, b -> a + b }

        fun <A, B> product2(dbs: LinkedList<Double>): Double =
                foldRight(dbs, 1.0) { a, b -> a * b }

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

        fun <A> empty(): LinkedList<A> = Nil

        fun <A> length(xs: LinkedList<A>): Int =
                foldRight(xs, 0) { _, a -> 1 + a }

        fun <A, B> foldLeft(xs: LinkedList<A>, z: B, f: (B, A) -> B): B {
            return when (xs) {
                is Nil -> z
//                is Cons -> f(foldLeft(xs.tail, z, f), xs.head)
                is Cons -> foldLeft(xs.tail, f(z, xs.head), f)
            }
        }

        fun <A> concat(xss: LinkedList<LinkedList<A>>): LinkedList<A> =
                foldRight(xss, empty()) { a, b -> append(a,b) }

        fun <A> append2(a: LinkedList<A>, b: LinkedList<A>): LinkedList<A> =
                foldRight(a, b) { x,y -> Cons(x,y) }

        fun inc(ints: LinkedList<Int>): LinkedList<Int> =
                foldRight(ints, empty()) { a,b -> Cons(a+1, b) }

        fun convertToString(dbs: LinkedList<Double>): LinkedList<String> =
                foldRight(dbs, empty()) { a,b -> Cons(a.toString(), b) }

        fun <A,B> map(xs: LinkedList<A>, f: (A) -> B): LinkedList<B>  =
                foldRight(xs, empty()) { a, b -> Cons(f(a), b) }

        fun <A,B> flatMap(a: LinkedList<A>, f: (A) -> LinkedList<B>): LinkedList<B> =
                foldRight(a, empty()) { x,y -> append(f(x), y) }

        fun sum3(ints: LinkedList<Int>): Int = foldLeft(ints, 0) { x, y -> x + y }
        fun product3(dbs: LinkedList<Double>): Double = foldLeft(dbs, 1.0) { x, y -> x * y }
        fun <A> length2(xs: LinkedList<A>): Int = foldLeft(xs, 0) { acc,_ -> 1 + acc }
        fun <A> reverse(xs: LinkedList<A>): LinkedList<A> = foldLeft(xs, empty())  {
            t: LinkedList<A>, h: A -> Cons(h, t)
        }

        fun <A> filter(xs: LinkedList<A>, f: (A) -> Boolean): LinkedList<A> =
                foldRight(xs, empty()) { a, b -> if (f(a)) Cons(a,b) else b }

        fun <A> filter2(xs: LinkedList<A>, f:(A) -> Boolean): LinkedList<A> =
                flatMap(xs) { a -> if (f(a)) LinkedList.of(a) else empty() }

        fun removeOdd(ints: LinkedList<Int>): LinkedList<Int> =
                foldRight(ints, empty()) { a, _ -> filter(ints) { a%2 == 0 } }
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