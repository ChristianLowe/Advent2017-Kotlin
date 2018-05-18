import kotlin.math.abs

typealias Vector = Triple<Int, Int, Int>
data class Particle(val position: Vector, val velocity: Vector, val acceleration: Vector)

val zeroVector = Vector(0, 0, 0)

fun Vector.manhattanFrom(from: Vector): Int =
        abs(this.first - from.first) + abs(this.second - from.second) + abs(this.third - from.third)

fun vectorFrom(x: String, y: String, z: String): Vector =
        Vector(x.toInt(), y.toInt(), z.toInt())

fun main(args: Array<String>) {
    part1()
    part2()
}

private fun part1() {
    val particleRegex
            = """p=<(-?\d+),(-?\d+),(-?\d+)>, v=<(-?\d+),(-?\d+),(-?\d+)>, a=<(-?\d+),(-?\d+),(-?\d+)>""".toRegex()

    val vectorList = inputD20.split('\n')
            .map { particleRegex.find(it)!!.groupValues }
            .map { Particle(
                    vectorFrom(it[1], it[2], it[3]),
                    vectorFrom(it[4], it[5], it[6]),
                    vectorFrom(it[7], it[8], it[9])) }

    val positions = vectorList.map { it to it.position.manhattanFrom(zeroVector) }.toMap()
    val velocities = vectorList.map { it to it.velocity.manhattanFrom(zeroVector) }.toMap()
    val accelerations = vectorList.map { it to it.acceleration.manhattanFrom(zeroVector) }.toMap()

    val closestVector = vectorList
            .sortedBy { positions[it] }
            .sortedBy { velocities[it] }
            .sortedBy { accelerations[it] }
            .first()

    println(vectorList.indexOf(closestVector))
}

private fun part2() {

}
