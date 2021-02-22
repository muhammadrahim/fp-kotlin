import kotlin.math.abs
import kotlin.math.pow

sealed class Optional<out A>
data class Some<out A>(val value: A) : Optional<A>()
object None : Optional<Nothing>()

fun <A, B> lift(f: (A) -> B): (Optional<A>) -> Optional<B> = { it.map(f) }

val ABS_0: (Optional<Double>) -> Optional<Double> = lift { abs(it) }

fun insuranceRateQuote(age: Int, numberOfSpeedingTickets: Int): Double = TODO()

fun parseInsuranceRateQuote(age: String, numberOfSpeedingTickets: String): Optional<Double> {
    val optAge: Optional<Int> = catches { age.toInt() }
    val optTickets: Optional<Int> = catches { numberOfSpeedingTickets.toInt() }
    return map2(optAge, optTickets) { a, t -> insuranceRateQuote(a, t) }
}

fun <A, B, C> map2(a: Optional<A>, b: Optional<B>, f: (A, B) -> C): Optional<C> =
        a.flatMap { a ->
            b.map { b -> f(a, b) }
        }

fun <A, B> traverse(
        xa: LinkedList<A>,
        f: (A) -> Optional<B>
): Optional<LinkedList<B>> = when (xa) {
    is Nil -> Some(Nil)
    is Cons -> map2(f(xa.head), traverse(xa.tail, f)) { b, xb -> Cons(b, xb) }
}

fun <A> catches(a: () -> A): Optional<A> =
        try {
            Some(a())
        } catch (e: Throwable) {
            None
        }

fun <A, B> Optional<A>.map(f: (A) -> B): Optional<B> = when (this) {
    is Some -> Some(f(this.value))
    is None -> None
}

fun <A, B> Optional<A>.flatMap(f: (A) -> Optional<B>): Optional<B> = when (this) {
    is Some -> f(this.value)
    is None -> None
}

fun <A> Optional<A>.getOrElse(default: () -> A): A = when (this) {
    is Some -> this.value
    is None -> default()
}

fun <A> Optional<A>.orElse(alt: () -> Optional<A>): Optional<A> = when (this) {
    is Some -> this
    is None -> alt()
}

fun <A> Optional<A>.filter(f: (A) -> Boolean): Optional<A> =
        when (this) {
            is Some -> if (f(this.value)) this else None
            is None -> None
        }

fun mean(xs: List<Double>): Optional<Double> =
        if (xs.isEmpty()) None else Some(xs.sum() / xs.size)

fun variance(xs: List<Double>): Optional<Double> =
        mean(xs).flatMap { m -> mean(xs.map { x -> (x - m).pow(2) }) }

class Employee(
        val name: String,
        val dept: String,
        val manager: Optional<String>
)