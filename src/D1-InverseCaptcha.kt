
fun main(args: Array<String>) {
    part1()
    part2()
}

private fun part1() {
    val numbers = inputD1.toList().map { it.toInt() - '0'.toInt() }

    var sum = 0
    var lastNumber = numbers[0]
    for (idx in 1..numbers.size) {
        val i = idx % numbers.size

        if (numbers[i] == lastNumber) {
            sum += lastNumber
        }

        lastNumber = numbers[i]
    }

    println(sum)
}

private fun part2() {
    val numbers = inputD1.toList().map { it.toInt() - '0'.toInt() }
    val halfPoint = numbers.size / 2

    var sum = 0
    for (i in 0 until numbers.size) {
        val j = (i + halfPoint) % numbers.size

        if (numbers[i] == numbers[j]) {
            sum += numbers[i]
        }
    }

    println(sum)
}
