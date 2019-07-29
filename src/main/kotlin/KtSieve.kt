package org.jetbrains.loom.generators

// Kt impl
fun ktPrimes(): Iterator<Long> = iterator {
    val naturalNumbers = iterator<Long> {
        var i = 1L
        while (true) {
            yield(++i)
        }
    }

    var source = naturalNumbers
    while (true) {
        val next = source.next()
        yield(next)
        source = filter(source, next)
    }
}

fun filter(sequence: Iterator<Long>, prime: Long): Iterator<Long> = iterator {
    while (sequence.hasNext()) {
        val next = sequence.next()
        if (next % prime != 0L) {
            yield(next)
        }
    }
}
