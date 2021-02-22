sealed class Either<out E, out A>

data class Left<out E>(val value: E) : Either<E, Nothing>()
data class Right<out A>(val value: A) : Either<Nothing, A>()

fun meanEither(xs: List<Double>): Either<Exception, Double> =
    try {
        Right(xs.sum() / xs.size)
    } catch (e: Exception) {
        Left(e)
    }

fun <A> catching(a: () -> A): Either<Exception, A> =
    try {
        Right(a())
    } catch (e: Exception) {
        Left(e)
    }

fun <E, A, B> Either<E, A>.map(f: (A) -> B): Either<E, B> = TODO()
fun <E, A, B> Either<E, A>.flatMap(f: (A) -> Either<E, B>): Either<E, B> =
    TODO()

fun <E, A> Either<E, A>.orElse(
    f: () -> Either<E, A>
): Either<E, A> = TODO()

fun <E, A, B, C> map2(
    ae: Either<E, A>,
    be: Either<E, B>,
    f: (A, B) -> C
): Either<E, C> = TODO()