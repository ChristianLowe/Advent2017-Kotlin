
fun main(args: Array<String>) {
    part1()
    part2()
}

private fun part1() {
    var pointer = 0
    var score = 0
    var scope = 1

    var inGarbage = false

    while (pointer < inputD9.length) {
        val char = inputD9[pointer]

        if (inGarbage) {
            when (char) {
                '!' -> pointer++
                '>' -> inGarbage = false
            }
        } else {
            when (char) {
                '{' -> {score += scope; scope++}
                '}' -> scope--
                '!' -> pointer++
                '<' -> inGarbage = true
            }
        }

        pointer++
    }

    println(score)
}

private fun part2() {
    var pointer = 0
    var garbage = 0

    var inGarbage = false

    while (pointer < inputD9.length) {
        val char = inputD9[pointer]

        if (inGarbage) {
            when (char) {
                '!' -> pointer++
                '>' -> inGarbage = false
                else -> garbage++
            }
        } else {
            when (char) {
                '!' -> pointer++
                '<' -> inGarbage = true
            }
        }

        pointer++
    }

    println(garbage)
}
