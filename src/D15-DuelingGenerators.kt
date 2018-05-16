
fun main(args: Array<String>) {
    part1()
    part2()
}

private fun part1() {
    class Generator(private var previous: Long, private var factor: Long) {
        fun next(): Long {
            previous *= factor
            previous %= Int.MAX_VALUE
            return previous
        }
    }

    val generatorA = Generator(inputD15A, 16807)
    val generatorB = Generator(inputD15B, 48271)
    val mask = 0xFFFF.toLong() // Right-most 16 bits

    var count = 0

    for (generation in (1..40_000_000)) {
        if (generatorA.next().and(mask) == generatorB.next().and(mask)) {
            count++
        }
    }

    println(count)
}

private fun part2() {
    class Generator(private var previous: Long, private var factor: Long, private val multipleOf: Long) {
        fun next(): Long {
            do {
                previous *= factor
                previous %= Int.MAX_VALUE
            } while ((previous % multipleOf) != 0L)

            return previous
        }
    }

    val generatorA = Generator(inputD15A, 16807, 4)
    val generatorB = Generator(inputD15B, 48271, 8)
    val mask = 0xFFFF.toLong() // Right-most 16 bits

    var count = 0

    for (generation in (1..5_000_000)) {
        if (generatorA.next().and(mask) == generatorB.next().and(mask)) {
            count++
        }
    }

    println(count)
}
