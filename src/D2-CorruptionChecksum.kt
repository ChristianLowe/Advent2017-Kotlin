
fun main(args: Array<String>) {
    part1()
    part2()
}

private fun part1() {
    var checksum = 0

    val lines = inputD2.split('\n')
    for (line in lines) {
        val numbers = line.split('\t').map { it.toInt() }
        checksum += numbers.max()!! - numbers.min()!!
    }

    println(checksum)
}

private fun part2() {
    var checksum = 0

    val lines = inputD2.split('\n')
    for (line in lines) {
        val numbers = line.split('\t').map { it.toInt() }.sorted()

        for (i in 0 until numbers.size) {
            val numerator = numbers.slice(i+1 until numbers.size).find { it % numbers[i] == 0 }

            if (numerator != null) {
                checksum += numerator / numbers[i]
                break
            }
        }
    }

    println(checksum)
}
