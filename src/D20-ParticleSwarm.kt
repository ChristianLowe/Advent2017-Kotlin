import kotlin.math.abs

typealias Vector = Triple<Long, Long, Long>
data class Particle(val position: Vector, val velocity: Vector, val acceleration: Vector)

val zeroVector = Vector(0, 0, 0)

operator fun Vector.get(index: Int): Long {
    return when (index) {
        0 -> first
        1 -> second
        2 -> third
        else -> throw IndexOutOfBoundsException()
    }
}

fun Vector.manhattanFrom(from: Vector): Long =
        abs(this.first - from.first) + abs(this.second - from.second) + abs(this.third - from.third)

fun vectorFrom(x: String, y: String, z: String): Vector =
        Vector(x.toLong(), y.toLong(), z.toLong())

operator fun Vector.plus(other: Vector): Vector =
        Vector(first + other.first, second + other.second, third + other.third)

fun main(args: Array<String>) {
    part1()
    part2()
}

private fun part1() {
    val particleRegex
            = """p=<(-?\d+),(-?\d+),(-?\d+)>, v=<(-?\d+),(-?\d+),(-?\d+)>, a=<(-?\d+),(-?\d+),(-?\d+)>""".toRegex()

    val particleList = inputD20.split('\n')
            .map { particleRegex.find(it)!!.groupValues }
            .map { Particle(
                    vectorFrom(it[1], it[2], it[3]),
                    vectorFrom(it[4], it[5], it[6]),
                    vectorFrom(it[7], it[8], it[9])) }

    val positions = particleList.map { it to it.position.manhattanFrom(zeroVector) }.toMap()
    val velocities = particleList.map { it to it.velocity.manhattanFrom(zeroVector) }.toMap()
    val accelerations = particleList.map { it to it.acceleration.manhattanFrom(zeroVector) }.toMap()

    val closestParticle = particleList
            .sortedBy { positions[it] }
            .sortedBy { velocities[it] }
            .sortedBy { accelerations[it] }
            .first()

    println(particleList.indexOf(closestParticle))
}

inline fun <T, K> Iterable<T>.duplicateKeysBy(selector: (T) -> K): List<T> {
    val set = HashSet<K>()
    val list = ArrayList<T>()
    for (e in this) {
        val key = selector(e)
        if (!set.add(key))
            list.add(e)
    }
    return list
}

private fun part2() {
    val particleRegex
            = """p=<(-?\d+),(-?\d+),(-?\d+)>, v=<(-?\d+),(-?\d+),(-?\d+)>, a=<(-?\d+),(-?\d+),(-?\d+)>""".toRegex()

    var particleList = inputD20.split('\n')
            .map { particleRegex.find(it)!!.groupValues }
            .map { Particle(
                    vectorFrom(it[1], it[2], it[3]),
                    vectorFrom(it[4], it[5], it[6]),
                    vectorFrom(it[7], it[8], it[9])) }

    // 40-tick limit found by letting simulation run for a few minutes.
    // Is there a way to know for sure that no more particles will collide?
    for (tick in (1..40)) {
        val colliding = particleList.filter {
            it.position in particleList.duplicateKeysBy { it.position }.map { it.position }
        }

        particleList -= colliding
        particleList = particleList.map { Particle(it.position, it.velocity + it.acceleration, it.acceleration) }
        particleList = particleList.map { Particle(it.position + it.velocity, it.velocity, it.acceleration)}
    }

    println(particleList.size)
}
