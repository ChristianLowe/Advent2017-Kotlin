
fun main(args: Array<String>) {
    part1()
    part2()
}

private fun allWordsUnique(passphrase: String): Boolean {
    val wordList = passphrase.split("\\s+".toRegex())
    return wordList.size == wordList.toSet().size
}

private fun noWordsAnagrams(passphrase: String): Boolean {
    val listOfSortedCharacters = passphrase.split("\\s+".toRegex()).map { it.toList().sorted() }
    return listOfSortedCharacters.size == listOfSortedCharacters.toSet().size
}

private fun part1() {
    println(inputD4.split("\n").count(::allWordsUnique))
}

private fun part2() {
    println(inputD4.split("\n").filter(::allWordsUnique).filter(::noWordsAnagrams).size)
}
