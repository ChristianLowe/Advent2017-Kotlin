
fun main(args: Array<String>) {
    part1()
    part2()
}

private fun part1() {
    val wheel = (0..255).toMutableList()
    var currentPosition = 0

    val lengths = inputD10.split(",").map { it.toInt() }
    for ((skipSize, length) in lengths.withIndex()) {
        val indices = (currentPosition until currentPosition + length).map { it % wheel.size }
        val slice = wheel.slice(indices).reversed()

        for (i in 0 until indices.size) {
            wheel[indices[i]] = slice[i]
        }

        currentPosition = (currentPosition + length + skipSize) % wheel.size
    }

    println(wheel[0] * wheel[1])
}

private fun part2() {
    val wheel = (0..255).toMutableList()
    var currentPosition = 0
    var skipSize = 0

    val inputLengths = inputD10.toList().map { it.toInt() }
    val lengths = inputLengths.plus(listOf(17, 31, 73, 47, 23))

    for (round in (1..64)) {
        for (length in lengths) {
            val indices = (currentPosition until currentPosition + length).map { it % wheel.size }
            val slice = wheel.slice(indices).reversed()

            for (i in 0 until indices.size) {
                wheel[indices[i]] = slice[i]
            }

            currentPosition = (currentPosition + length + skipSize) % wheel.size
            skipSize++
        }
    }

    val denseHash = (0 until 16).map {
        wheel.slice(it * 16 until (it+1) * 16).reduce { a, b -> a xor b }.toByte()
    }

    denseHash.forEach { print(String.format("%02x", it)) }
    println()
}
