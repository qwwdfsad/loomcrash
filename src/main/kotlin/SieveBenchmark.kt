package org.jetbrains.loom.generators

import org.openjdk.jmh.annotations.*
import java.util.concurrent.*

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Fork(1)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
open class SieveBenchmark {

    @Param(value = ["2", "10", "20", "30", "40", "50", "60", "70", "80", "90", "100"])
    var primes = 100

    @Benchmark
    fun kotlinSieve() = ktPrimes().asSequence().drop(primes).first()

    @Benchmark
    fun loomSieve() = loomPrimes().asSequence().drop(primes).first()
}
