import java.util.*
import kotlin.experimental.and

fun main(args: Array<String>) {
    part1()
    part2()
}

private fun part1() {
    fun getHashBytes(input: String): List<Int> {
        // From D10
        val wheel = (0..255).toMutableList()
        var currentPosition = 0
        var skipSize = 0

        val inputLengths = input.toList().map { it.toInt() }
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

        return (0 until 16).map {
            wheel.slice(it * 16 until (it+1) * 16).reduce { a, b -> a xor b }
        }
    }

    val squaresUsed = (0 until 128)
            .map { "$inputD14-$it" }
            .map ( ::getHashBytes )
            .map { it.fold(0) { acc, int -> acc + Integer.bitCount(int) } }
            .sum()

    println(squaresUsed)
}

private fun part2() {
    fun getHashBytes(input: String): List<Byte> {
        // From D10
        val wheel = (0..255).toMutableList()
        var currentPosition = 0
        var skipSize = 0

        val inputLengths = input.toList().map { it.toInt() }
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

        return (0 until 16).map {
            wheel.slice(it * 16 until (it+1) * 16).reduce { a, b -> a xor b }.toByte()
        }
    }

    fun toBooleanList(hashBytes: List<Byte>): List<Boolean> {
        val booleanList = LinkedList<Boolean>()
        for (byte in hashBytes.reversed()) {
            for (bitPosition in (0 until 8)) {
                val mask = 1.shl(bitPosition).toByte()
                booleanList.addFirst(byte.and(mask) != 0.toByte())
            }
        }
        return booleanList
    }

    val grid = (0 until 128)
            .map { "$inputD14-$it" }
            .map ( ::getHashBytes )
            .map ( ::toBooleanList )

    var groupCount = 0
    val visitedSet = mutableSetOf<Pair<Int, Int>>()

    for (y in (0 until 128)) {
        for (x in (0 until 128)) {
            if (grid[y][x] && Pair(y, x) !in visitedSet) {
                val visitQueue: Queue<Pair<Int, Int>> = LinkedList(listOf(Pair(y, x)))
                while (visitQueue.isNotEmpty()) {
                    val coords = visitQueue.poll()
                    val (searchY, searchX) = coords
                    if (grid.getOrNull(searchY)?.getOrNull(searchX) == true && coords !in visitedSet) {
                        val left = Pair(searchY, searchX-1)
                        val right = Pair(searchY, searchX+1)
                        val up = Pair(searchY-1, searchX)
                        val down = Pair(searchY+1, searchX)

                        visitedSet.add(coords)
                        visitQueue.addAll(listOf(left, right, up, down))
                    }
                }
                groupCount++
            }
        }
    }
    
    println(groupCount)
}
