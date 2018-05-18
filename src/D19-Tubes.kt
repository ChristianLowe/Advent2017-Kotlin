
fun main(args: Array<String>) {
    part1()
    part2()
}

private fun part1() {
    val grid = inputD19.split('\n')

    var location = Pair(0, grid[0].indexOf('|'))
    var direction = Direction.DOWN
    var string = ""

    fun charInGrid(newLocation: Pair<Int, Int>): Char? =
            grid.getOrNull(newLocation.first)?.getOrNull(newLocation.second)

    while (true) {
        location = when (direction) {
            Direction.RIGHT -> Pair(location.first, location.second + 1)
            Direction.UP -> Pair(location.first - 1, location.second)
            Direction.LEFT -> Pair(location.first, location.second - 1)
            Direction.DOWN -> Pair(location.first + 1, location.second)
        }

        val left = Pair(location.first, location.second - 1)
        val up = Pair(location.first - 1, location.second)

        when (charInGrid(location)) {
            '+' -> {
                direction = when (direction) {
                    Direction.UP, Direction.DOWN -> {
                        if (charInGrid(left) in ('A'..'Z') + '-')
                            Direction.LEFT
                        else
                            Direction.RIGHT
                    }
                    Direction.LEFT, Direction.RIGHT -> {
                        if (charInGrid(up) in ('A'..'Z') + '|')
                            Direction.UP
                        else
                            Direction.DOWN
                    }
                }
            }
            ' ' -> {
                println(string)
                return
            }
            in 'A'..'Z' -> {
                string += charInGrid(location)
            }
        }
    }
}

private fun part2() {
    val grid = inputD19.split('\n')

    var location = Pair(0, grid[0].indexOf('|'))
    var direction = Direction.DOWN
    var steps = 0

    fun charInGrid(newLocation: Pair<Int, Int>): Char? =
            grid.getOrNull(newLocation.first)?.getOrNull(newLocation.second)

    while (true) {
        steps++

        location = when (direction) {
            Direction.RIGHT -> Pair(location.first, location.second + 1)
            Direction.UP -> Pair(location.first - 1, location.second)
            Direction.LEFT -> Pair(location.first, location.second - 1)
            Direction.DOWN -> Pair(location.first + 1, location.second)
        }

        val left = Pair(location.first, location.second - 1)
        val up = Pair(location.first - 1, location.second)

        when (charInGrid(location)) {
            '+' -> {
                direction = when (direction) {
                    Direction.UP, Direction.DOWN -> {
                        if (charInGrid(left) in ('A'..'Z') + '-')
                            Direction.LEFT
                        else
                            Direction.RIGHT
                    }
                    Direction.LEFT, Direction.RIGHT -> {
                        if (charInGrid(up) in ('A'..'Z') + '|')
                            Direction.UP
                        else
                            Direction.DOWN
                    }
                }
            }
            ' ' -> {
                println(steps)
                return
            }
        }
    }
}
