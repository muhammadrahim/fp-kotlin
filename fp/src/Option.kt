import kotlin.math.abs
import kotlin.math.pow

sealed class Option<out A>
data class Some<out A>(val value: A) : Option<A>()
object None : Option<Nothing>()

fun <A, B> lift(f: (A) -> B): (Option<A>) -> Option<B> = { it.map(f) }

val abs0: (Option<Double>) -> Option<Double> = lift { abs(it) }

fun insuranceRateQuote(age: Int, numberOfSpeedingTickets: Int): Double = TODO()

fun parseInsuranceRateQuote(age: String, numberOfSpeedingTickets: String): Option<Double> {
    val optAge: Option<Int> = catches { age.toInt() }
    val optTickets: Option<Int> = catches { numberOfSpeedingTickets.toInt() }
    return map2(optAge, optTickets) { a, t -> insuranceRateQuote(a, t) }
}

fun <A, B, C> map2(a: Option<A>, b: Option<B>, f: (A, B) -> C): Option<C> =
        a.flatMap { a ->
            b.map { b -> f(a, b) }
        }

fun <A, B> traverse(
        xa: LinkedList<A>,
        f: (A) -> Option<B>
): Option<LinkedList<B>> = when (xa) {
    is Nil -> Some(Nil)
    is Cons -> map2(f(xa.head), traverse(xa.tail, f)) { b, xb -> Cons(b, xb) }
}

fun <A> catches(a: () -> A): Option<A> =
        try {
            Some(a())
        } catch (e: Throwable) {
            None
        }

fun <A, B> Option<A>.map(f: (A) -> B): Option<B> = when (this) {
    is Some -> Some(f(this.value))
    is None -> None
}

fun <A, B> Option<A>.flatMap(f: (A) -> Option<B>): Option<B> = when (this) {
    is Some -> f(this.value)
    is None -> None
}

fun <A> Option<A>.getOrElse(default: () -> A): A = when (this) {
    is Some -> this.value
    is None -> default()
}

fun <A> Option<A>.orElse(alt: () -> Option<A>): Option<A> = when (this) {
    is Some -> this
    is None -> alt()
}

fun <A> Option<A>.filter(f: (A) -> Boolean): Option<A> =
        when (this) {
            is Some -> if (f(this.value)) this else None
            is None -> None
        }

fun mean(xs: List<Double>): Option<Double> =
        if (xs.isEmpty()) None else Some(xs.sum() / xs.size)

fun variance(xs: List<Double>): Option<Double> =
        mean(xs).flatMap { m -> mean(xs.map { x -> (x - m).pow(2) }) }

class Employee(
        val name: String,
        val dept: String,
        val manager: Option<String>
)

fun lookupByName(name: String): Option<Employee> = TODO()

fun timDepartment(): Option<String> = lookupByName("Tim").map { it.dept }

fun timManager(): String = lookupByName("Tim")
        .flatMap { it.manager }
        .getOrElse { "Unemployed" }