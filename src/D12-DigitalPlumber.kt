import java.util.*

fun main(args: Array<String>) {
    part1()
    part2()
}

private fun part1() {
    val pipeMap = inputD12.split('\n')
            .map { it.split(" <-> ")[1] }
            .map { it.split(", ").map { it.toInt() }}
            .mapIndexed { index, list -> index to list }
            .toMap()

    val visitQueue: Queue<Int> = LinkedList(listOf(0))
    val visitedSet = mutableSetOf<Int>()

    while (visitQueue.isNotEmpty()) {
        val newLocations = pipeMap[visitQueue.poll()]!!.filter { it !in visitedSet }
        visitQueue.addAll(newLocations)
        visitedSet.addAll(newLocations)
    }

    println(visitedSet.size)
}

private fun part2() {
    val pipeMap = inputD12.split('\n')
            .map { it.split(" <-> ")[1] }
            .map { it.split(", ").map { it.toInt() }}
            .mapIndexed { index, list -> index to list }
            .toMap()

    val visitedSet = mutableSetOf<Int>()
    var groupCount = 0

    for (i in pipeMap.keys) {
        if (i !in visitedSet) {
            val visitQueue: Queue<Int> = LinkedList(listOf(i))
            while (visitQueue.isNotEmpty()) {
                val newLocations = pipeMap[visitQueue.poll()]!!.filter { !visitedSet.contains(it) }
                visitQueue.addAll(newLocations)
                visitedSet.addAll(newLocations)
            }

            groupCount++
        }
    }

    println(groupCount)
}
