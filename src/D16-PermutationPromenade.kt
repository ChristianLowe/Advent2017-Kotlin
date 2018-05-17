fun main(args: Array<String>) {
    part1()
    part2()
}

fun CharArray.swap(positionA: Int, positionB: Int): CharArray {
    val temp = this[positionA]
    this[positionA] = this[positionB]
    this[positionB] = temp
    return this
}

fun Char.toDigit(): Int = this.toInt() - 48

private fun part1() {
    fun spin(dancers: String, count: Int): String {
        val pivot = dancers.length - count
        return dancers.substring(pivot until dancers.length) +
                dancers.substring(0 until pivot)
    }

    fun exchange(dancers: String, positionA: Int, positionB: Int): String {
        return String(dancers.toCharArray().swap(positionA, positionB))
    }

    fun partner(dancers: String, programA: Char, programB: Char): String {
        return String(dancers.toCharArray().swap(
                dancers.indexOf(programA), dancers.indexOf(programB)))
    }

    fun parseDance(dancers: String, move: String): String {
        val moveType = move[0]
        val parameters = move.substring(1 until move.length).split('/')

        return when (moveType) {
            's' -> spin(dancers, parameters[0].toInt())
            'x' -> exchange(dancers, parameters[0].toInt(), parameters[1].toInt())
            'p' -> partner(dancers, parameters[0][0], parameters[1][0])
            else -> throw IllegalStateException("invalid move '$moveType'")
        }
    }

    val beforeDance = "abcdefghijklmnop"
    val afterDance = inputD16.split(',').fold(beforeDance, ::parseDance)
    val evenAfter = inputD16.split(',').fold(afterDance, ::parseDance)
    println(evenAfter)
}

private fun part2() {
    fun spin(dancers: String, count: Int): String {
        val pivot = dancers.length - count
        return dancers.substring(pivot until dancers.length) +
                dancers.substring(0 until pivot)
    }

    fun exchange(dancers: String, positionA: Int, positionB: Int): String {
        return String(dancers.toCharArray().swap(positionA, positionB))
    }

    fun partner(dancers: String, programA: Char, programB: Char): String {
        return String(dancers.toCharArray().swap(
                dancers.indexOf(programA), dancers.indexOf(programB)))
    }

    fun parseDance(dancers: String, move: String): String {
        val moveType = move[0]
        val parameters = move.substring(1 until move.length).split('/')

        return when (moveType) {
            's' -> spin(dancers, parameters[0].toInt())
            'x' -> exchange(dancers, parameters[0].toInt(), parameters[1].toInt())
            'p' -> partner(dancers, parameters[0][0], parameters[1][0])
            else -> throw IllegalStateException("invalid move '$moveType'")
        }
    }

    val beforeDance = "abcdefghijklmnop"
    val instructions = inputD16.split(',')
    val dancePositions = mutableListOf(beforeDance)

    var inCycle = false
    var loopStarts = -1
    var dancePointer = -1

    for (i in (1..1_000_000_000)) {
        if (inCycle) {
            dancePointer++

            if (dancePointer == dancePositions.size) {
                dancePointer = loopStarts
            }
        } else {
            val afterDance = instructions.fold(dancePositions.last(), ::parseDance)

            if (afterDance in dancePositions) {
                inCycle = true
                loopStarts = dancePositions.indexOf(afterDance)
                dancePointer = loopStarts
            } else {
                dancePositions.add(afterDance)
            }
        }
    }

    println(dancePositions[dancePointer])
}
