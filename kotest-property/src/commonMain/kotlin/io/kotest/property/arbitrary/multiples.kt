package io.kotest.property.arbitrary

import io.kotest.property.Arb
import io.kotest.property.Shrinker

/**
 * Returns an [Arb] where each generated value is a multiple of k between 0 to the specified max.
 *
 * The edge cases are 0, since 0 is a multiple of every integer.
 *
 * This arbitrary uses a [Shrinker] which will reduce the size of a failing value by removing
 * factors of k until 0 is hit.
 *
 */
fun Arb.Companion.multiple(k: Int, max: Int): Arb<Int> =
   arbitrary(MultiplesShrinker(k)) { it.random.nextInt(0, max / k) * k }

@Deprecated("Use multiple. Deprecated since 5.0 and will be removed in 6.0", ReplaceWith("multiple(k, max)"))
fun Arb.Companion.multiples(k: Int, max: Int): Arb<Int> = multiple(k, max)

class MultiplesShrinker(private val multiple: Int) : Shrinker<Int> {
   override fun shrink(value: Int): List<Int> = when (value) {
      0 -> emptyList()
      in 1..multiple -> listOf(0)
      else -> listOf(value - multiple)
   }
}

fun Arb.Companion.factor(k: Int): Arb<Int> = arbitrary {
   generateSequence { it.random.nextInt(k) }.filter { it > 0 }.filter { k % it == 0 }.first()
}

@Deprecated("Use factor. Deprecated since 5.0 and will be removed in 6.0", ReplaceWith("factor(k)"))
fun Arb.Companion.factors(k: Int): Arb<Int> = factor(k)
