import java.util.*

object SpiralPattern {
    enum class Direction { RIGHT, UP, LEFT, DOWN }

    var moveCount = 1

    private var direction = Direction.RIGHT
    private var nextChange = 2
    private var nextChangeRelative = Pair(1, 2)

    fun getNextDirection(): Direction {
        update()

        return direction
    }

    private fun update() {
        if (moveCount == nextChange) {
            val changeBy = nextChangeRelative.first

            direction = when (direction) {
                Direction.RIGHT -> Direction.UP
                Direction.UP    -> Direction.LEFT
                Direction.LEFT  -> Direction.DOWN
                Direction.DOWN  -> Direction.RIGHT
            }

            nextChange += changeBy
            nextChangeRelative = if (changeBy == nextChangeRelative.second) {
                Pair(changeBy, changeBy + 1)
            } else {
                Pair(changeBy + 1, changeBy + 1)
            }
        }

        moveCount++
    }
}

fun main(args: Array<String>) {
    // Generate grid
    val gridSize = generateSequence(1) { it + 2 }.find { it * it > inputD3 }!!
    val grid = Array(gridSize, { IntArray(gridSize) })
    val middle = Pair(gridSize / 2, gridSize / 2)

    try {
        var (y, x) = middle

        while (true) {
            grid[y][x] = SpiralPattern.moveCount

            when (SpiralPattern.getNextDirection()) {
                SpiralPattern.Direction.LEFT  -> x--
                SpiralPattern.Direction.RIGHT -> x++
                SpiralPattern.Direction.UP    -> y--
                SpiralPattern.Direction.DOWN  -> y++
            }
        }
    } catch (e: ArrayIndexOutOfBoundsException) { /* Grid is now filled */ }

    // Breadth-first search
    data class BfsItem(val coords: Pair<Int, Int>, val depth: Int)
    val queue: Queue<BfsItem> = LinkedList()
    val searched = mutableSetOf<Int>()

    queue.add(BfsItem(middle, 0))
    while (queue.isNotEmpty()) {
        val (coords, depth) = queue.poll()
        val (y, x) = coords

        val found = grid.getOrNull(y)?.getOrNull(x)

        if (found == inputD3) {
            println(depth)
            return
        } else if (found != null && !searched.contains(found)) {
            searched.add(found)

            queue.add(BfsItem(Pair(y+1, x), depth+1))
            queue.add(BfsItem(Pair(y-1, x), depth+1))
            queue.add(BfsItem(Pair(y, x+1), depth+1))
            queue.add(BfsItem(Pair(y, x-1), depth+1))
        }
    }
}