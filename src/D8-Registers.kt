
fun main(args: Array<String>) {
    part1()
    part2()
}

private fun part1() {
    val inputRegex = """(\w+) (inc|dec) (-?\d+) if (\w+) (>|<|>=|<=|==|!=) (-?\d+)""".toRegex()

    val compare: (Int, String, Int) -> Boolean = {leftNum, op, rightNum ->
        when (op) {
            ">" -> leftNum > rightNum
            "<" -> leftNum < rightNum
            ">=" -> leftNum >= rightNum
            "<=" -> leftNum <= rightNum
            "==" -> leftNum == rightNum
            "!=" -> leftNum != rightNum
            else -> throw RuntimeException("invalid op")
        }
    }

    val registers = hashMapOf<String, Int>()
    for (line in inputD8.split("\n")) {
        val (targetReg, targetOp, targetStrVal,
                conditionalReg, conditionalOp, conditionalStrVal)
                = inputRegex.find(line)!!.destructured

        val targetInt = targetStrVal.toInt()
        val conditionalInt = conditionalStrVal.toInt()

        if (compare(registers.getOrDefault(conditionalReg, 0), conditionalOp, conditionalInt)) {
            val oldValue = registers.getOrDefault(targetReg, 0)

            when (targetOp) {
                "inc" -> registers[targetReg] = oldValue + targetInt
                "dec" -> registers[targetReg] = oldValue - targetInt
            }
        }
    }

    println(registers.values.max())
}

private fun part2() {
    val inputRegex = """(\w+) (inc|dec) (-?\d+) if (\w+) (>|<|>=|<=|==|!=) (-?\d+)""".toRegex()

    val compare: (Int, String, Int) -> Boolean = {leftNum, op, rightNum ->
        when (op) {
            ">" -> leftNum > rightNum
            "<" -> leftNum < rightNum
            ">=" -> leftNum >= rightNum
            "<=" -> leftNum <= rightNum
            "==" -> leftNum == rightNum
            "!=" -> leftNum != rightNum
            else -> throw RuntimeException("invalid op")
        }
    }

    var max = Int.MIN_VALUE
    val registers = hashMapOf<String, Int>()
    for (line in inputD8.split("\n")) {
        val (targetReg, targetOp, targetStrVal,
                conditionalReg, conditionalOp, conditionalStrVal)
                = inputRegex.find(line)!!.destructured

        val targetInt = targetStrVal.toInt()
        val conditionalInt = conditionalStrVal.toInt()

        if (compare(registers.getOrDefault(conditionalReg, 0), conditionalOp, conditionalInt)) {
            val oldValue = registers.getOrDefault(targetReg, 0)

            when (targetOp) {
                "inc" -> registers[targetReg] = oldValue + targetInt
                "dec" -> registers[targetReg] = oldValue - targetInt
            }

            max = Math.max(max, registers[targetReg]!!)
        }
    }

    println(max)
}
