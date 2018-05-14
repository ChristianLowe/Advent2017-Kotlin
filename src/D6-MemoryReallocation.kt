
fun main(args: Array<String>) {
    part1()
    part2()
}

private fun part1() {
    val registerHistory = mutableListOf<IntArray>()
    var registers = inputD6.split("\\s+".toRegex()).map { it.toInt() }.toIntArray()
    var cycles = 0

    while (!registerHistory.any { it contentEquals registers }) {
        registerHistory.add(registers)
        registers = registers.clone()
        cycles++

        val maxVal = registers.max()!!
        val maxIdx = registers.indexOf(maxVal)

        registers[maxIdx] = 0
        (1..maxVal).forEach {
            val idx = (maxIdx + it) % registers.size
            registers[idx]++
        }
    }

    println(cycles)
}

private fun part2() {
    val registerHistory = mutableListOf<IntArray>()
    var registers = inputD6.split("\\s+".toRegex()).map { it.toInt() }.toIntArray()
    var cycles = 0

    while (!registerHistory.any { it contentEquals registers }) {
        registerHistory.add(registers)
        registers = registers.clone()
        cycles++

        val maxVal = registers.max()!!
        val maxIdx = registers.indexOf(maxVal)

        registers[maxIdx] = 0
        (1..maxVal).forEach {
            val idx = (maxIdx + it) % registers.size
            registers[idx]++
        }
    }

    val loopStarter = registerHistory.find { it contentEquals registers }
    println(registerHistory.size - registerHistory.indexOf(loopStarter))
}
