import kotlin.math.abs

fun main(args: Array<String>) {
    part1()
    part2()
}

private fun part1() {
    val depthToRangeMap = inputD13.split('\n')
            .map { it.split(": ").map { it.toInt() } }
            .map { it[0] to it[1] }.toMap()

    var severity = 0

    for ((depth, range) in depthToRangeMap) {
        // Check if our time-value "depth" is located at the top of a triangle wave
        val exclusiveRange = range - 1
        if (abs((depth % (2 * exclusiveRange)) - exclusiveRange) == exclusiveRange) {
            severity += depth * range
        }
    }

    println(severity)
}

private fun part2() {
    val depthToRangeMap = inputD13.split('\n')
            .map { it.split(": ").map { it.toInt() } }
            .map { it[0] to it[1] }.toMap()

    var delay = 0

    delayLoop@while (true) {
        for ((depth, range) in depthToRangeMap) {
            // Check if our time-value is located at the top of a triangle wave
            val time = depth + delay
            val exclusiveRange = range - 1
            if (abs((time % (2 * exclusiveRange)) - exclusiveRange) == exclusiveRange) {
                delay++
                continue@delayLoop
            }
        }
        break // None of the scanners detected the packet
    }

    println(delay)
}
