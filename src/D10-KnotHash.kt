
fun main(args: Array<String>) {
    part1()
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
