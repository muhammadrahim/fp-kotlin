# fp-kotlin
Learning functional programming in detail with Kotlin and Arrow.kt (potentially) by building a library - supported by *Scala's famous Red Book*.

Currently, this library has a `Tree` and `LinkedList` data structure. `Optional` & `Either` data types has also been added for better error-handling.

There is a lot of relevant methods already existing in these classes that I have created.


Example usage:

```
Optional.kt

class Employee(val name: String, val dept: String, val manager: Optional<String>)

fun lookupByName(name: String): Optional<Employee> = TODO()

fun timDepartment(): Optional<String> = lookupByName("Tim").map { it.dept }

fun timManager(): String = lookupByName("Tim")
        .flatMap { it.manager }
        .getOrElse { "Unemployed" }
```