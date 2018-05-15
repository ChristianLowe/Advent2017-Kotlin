import kotlin.math.abs

fun main(args: Array<String>) {
    part1()
    part2()
}

private fun part1() {
    var q = 0
    var r = 0

    inputD11.split(',').forEach { when (it) {
        "n" -> r--
        "s" -> r++
        "se" -> q++
        "nw" -> q--
        "ne" -> {q++; r--}
        "sw" -> {q--; r++}
    }}

    val distance = listOf(q, r, -q-r).map(::abs).max()
    println(distance)
}

private fun part2() {
    var q = 0
    var r = 0

    val allVisitedHexes = mutableSetOf<Pair<Int, Int>>()
    inputD11.split(',').forEach {
        when (it) {
            "n" -> r--
            "s" -> r++
            "se" -> q++
            "nw" -> q--
            "ne" -> {q++; r--}
            "sw" -> {q--; r++}
        }
        allVisitedHexes.add(Pair(q, r))
    }

    fun distance(point: Pair<Int, Int>): Int =
            listOf(point.first, point.second, -point.first-point.second)
            .map(::abs).max()!!

    val maxDistance = allVisitedHexes.map(::distance).max()
    println(maxDistance)
}
