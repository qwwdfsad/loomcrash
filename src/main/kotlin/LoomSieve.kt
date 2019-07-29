package org.jetbrains.loom.generators

private class GeneratorImpl<T : Any> : Generator<T>, Iterator<T> {
    val scope = ContinuationScope("Yield scope")
    private var _value: T? = null
    lateinit var continuation: Continuation

    override fun yield(value: T) {
        _value = value
        Continuation.yield(scope)
    }

    override fun hasNext(): Boolean {
        if (!continuation.isDone) {
            continuation.run()
            return _value != null
        }
        return false
    }

    override fun next(): T {
        val result = _value ?: error("todo")
        _value = null
        return result
    }
}


public fun <T : Any> buildIterator(builder: Generator<T>.() -> Unit): Iterator<T> {
    val generator = GeneratorImpl<T>()
    generator.continuation = Continuation(generator.scope, Runnable { generator.builder() })
    return generator
}

interface Generator<T : Any> {
    fun yield(value: T)
}


private fun filterLoom(sequence: Iterator<Long>, prime: Long): Iterator<Long> = buildIterator {
    while (sequence.hasNext()) {
        val next = sequence.next()
        if (next % prime != 0L) {
            yield(next)
        }
    }
}

fun loomPrimes(): Iterator<Long> = buildIterator {
    val naturalNumbers = buildIterator<Long> {
        var i = 1L
        while (true) {
            yield(++i)
        }
    }

    var source = naturalNumbers
    while (source.hasNext()) {
        val next = source.next()
        yield(next)
        source = filterLoom(source, next)
    }
}

fun main(args: Array<String>) {
    while (true) {
        Thread.sleep(100)
    }

//    val s = loomPrimes().asSequence().drop(100).first()
    println(loomPrimes().asSequence().drop(2).first())

//    for (l in it) {
//        println(l)
//    }
}