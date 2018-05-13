
fun main(args: Array<String>) {
    var checksum = 0

    val lines = inputD2.split('\n')
    for (line in lines) {
        val numbers = line.split('\t').map { it.toInt() }
        checksum += numbers.max()!! - numbers.min()!!
    }

    println(checksum)
}