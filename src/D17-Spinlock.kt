import java.util.*
import kotlin.collections.ArrayList

fun main(args: Array<String>) {
    part1()
    part2()
}

private fun part1() {
    val list = mutableListOf(0)
    var currentPosition = 0

    (1..2017).forEach {
        currentPosition = (currentPosition + inputD17) % list.size
        currentPosition++

        list.add(currentPosition, it)
    }

    println(list[currentPosition + 1])
}

private fun part2() {
    var afterZero = -1
    var zeroPosition = 0
    var currentPosition = 0

    (1..50_000_000).forEach {
        currentPosition = (currentPosition + inputD17) % it
        currentPosition++

        if (currentPosition <= zeroPosition) {
            zeroPosition++
        } else if (currentPosition == zeroPosition + 1) {
            afterZero = it
        }
    }

    println(afterZero)
}
