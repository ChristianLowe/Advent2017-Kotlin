
fun main(args: Array<String>) {
    part1()
    part2()
}

private fun part1() {
    val offsets = inputD5.split("\n").map { it.toInt() }.toMutableList()

    var pointer = 0
    var jumps = 0

    while (pointer >= 0 && pointer < offsets.size) {
        val newPointer = pointer + offsets[pointer]
        offsets[pointer]++
        pointer = newPointer
        jumps++
    }

    println(jumps)
}

private fun part2() {
    val offsets = inputD5.split("\n").map { it.toInt() }.toMutableList()

    var pointer = 0
    var jumps = 0

    while (pointer >= 0 && pointer < offsets.size) {
        val newPointer = pointer + offsets[pointer]

        if (offsets[pointer] >= 3) {
            offsets[pointer]--
        } else {
            offsets[pointer]++
        }

        pointer = newPointer
        jumps++
    }

    println(jumps)
}
