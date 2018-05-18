import java.util.*

fun main(args: Array<String>) {
    part1()
    part2()
}

sealed class Operand {
    class Reference(val register: Char) : Operand()
    class Literal(val value: Long) : Operand()

    companion object {
        fun from(parameter: String): Operand {
            return when (parameter[0]) {
                in 'a'..'z' -> Reference(parameter[0])
                else -> Literal(parameter.toLong())
            }
        }
    }
}

private fun part1() {
    val instructionList = inputD18.split('\n')
    val registers = LongArray(('a'..'z').count())

    fun getValue(operand: Operand): Long =
            when (operand) {
                is Operand.Literal -> operand.value
                is Operand.Reference -> registers[operand.register - 'a']
            }

    fun String.toRegisterNum(): Int = this[0] - 'a'

    var lastPlayedSound = -1L
    var instructionPointer = 0

    mainLoop@while (instructionPointer >= 0 && instructionPointer < instructionList.size) {
        val command = instructionList[instructionPointer].split(' ')

        when (command[0]) {
            "set" -> registers[command[1].toRegisterNum()]  = getValue(Operand.from(command[2]))
            "add" -> registers[command[1].toRegisterNum()] += getValue(Operand.from(command[2]))
            "mul" -> registers[command[1].toRegisterNum()] *= getValue(Operand.from(command[2]))
            "mod" -> registers[command[1].toRegisterNum()] %= getValue(Operand.from(command[2]))

            "snd" -> lastPlayedSound = getValue(Operand.from(command[1]))

            "rcv" -> {
                val value = getValue(Operand.from(command[1]))

                if (value != 0L) {
                    break@mainLoop
                }
            }

            "jgz" -> {
                val value = getValue(Operand.from(command[1]))
                val offset = getValue(Operand.from(command[2]))

                if (value > 0) {
                    instructionPointer += offset.toInt()
                    continue@mainLoop // Skip incrementing instruction pointer
                }
            }

            else -> throw IllegalStateException("unknown instruction ${command[0]}")
        }

        instructionPointer++
    }

    println(lastPlayedSound)
}

sealed class ProgramStatus {
    class Running: ProgramStatus()
    class Terminated: ProgramStatus()
    class SendingData(val data: Long): ProgramStatus()
    class ReceivingData(val registerNum: Int): ProgramStatus()
}

private fun part2() {
    val instructionList = inputD18.split('\n')

    class Program(programId: Long) {
        val registers = LongArray(('a'..'z').count())
        var instructionPointer = 0

        init {
            registers["p".toRegisterNum()] = programId
        }

        private fun getValue(operand: Operand): Long =
                when (operand) {
                    is Operand.Literal -> operand.value
                    is Operand.Reference -> registers[operand.register - 'a']
                }

        private fun String.toRegisterNum(): Int = this[0] - 'a'

        fun setRegister(registerNum: Int, data: Long) {
            registers[registerNum] = data
        }

        fun exec(): ProgramStatus {
            if (instructionPointer < 0 || instructionPointer >= instructionList.size) {
                return ProgramStatus.Terminated()
            }

            val command = instructionList[instructionPointer].split(' ')

            var programStatus: ProgramStatus = ProgramStatus.Running()
            when (command[0]) {
                "set" -> registers[command[1].toRegisterNum()]  = getValue(Operand.from(command[2]))
                "add" -> registers[command[1].toRegisterNum()] += getValue(Operand.from(command[2]))
                "mul" -> registers[command[1].toRegisterNum()] *= getValue(Operand.from(command[2]))
                "mod" -> registers[command[1].toRegisterNum()] %= getValue(Operand.from(command[2]))

                "snd" -> {
                    val data = getValue(Operand.from(command[1]))
                    programStatus = ProgramStatus.SendingData(data)
                }

                "rcv" -> {
                    val registerNum = command[1].toRegisterNum()
                    programStatus = ProgramStatus.ReceivingData(registerNum)
                }

                "jgz" -> {
                    val value = getValue(Operand.from(command[1]))
                    val offset = getValue(Operand.from(command[2]))

                    if (value > 0) {
                        instructionPointer += offset.toInt()
                        return programStatus // Skip incrementing instruction pointer
                    }
                }

                else -> throw IllegalStateException("unknown instruction ${command[0]}")
            }

            instructionPointer++
            return programStatus
        }
    }

    val program0 = Program(0)
    var programStatus0: ProgramStatus = ProgramStatus.Running()
    val queue0: Queue<Long> = LinkedList()
    var waiting0 = false

    val program1 = Program(1)
    var programStatus1: ProgramStatus = ProgramStatus.Running()
    val queue1: Queue<Long> = LinkedList()
    var waiting1 = false

    var answer = 0

    while (true) {
        if (programStatus0 is ProgramStatus.ReceivingData) {
            waiting0 = if (queue1.isNotEmpty()) {
                program0.setRegister(programStatus0.registerNum, queue1.poll())
                false
            } else {
                true
            }
        }

        if (!waiting0) {
            do {
                programStatus0 = program0.exec()
            } while (programStatus0 is ProgramStatus.Running)
        }

        if (programStatus0 is ProgramStatus.SendingData) {
            queue0.add(programStatus0.data)
        }

        if (programStatus1 is ProgramStatus.ReceivingData) {
            waiting1 = if (queue0.isNotEmpty()) {
                program1.setRegister(programStatus1.registerNum, queue0.poll())
                false
            } else {
                true
            }
        }

        if (!waiting1) {
            do {
                programStatus1 = program1.exec()
            } while (programStatus1 is ProgramStatus.Running)
        }

        if (programStatus1 is ProgramStatus.SendingData) {
            queue1.add(programStatus1.data)
            answer++
        }

        if (       (waiting0 && waiting1)
                || (programStatus0 is ProgramStatus.Terminated && programStatus1 is ProgramStatus.Terminated)
                || (programStatus0 is ProgramStatus.Terminated && waiting1)
                || (programStatus1 is ProgramStatus.Terminated && waiting0))
        {
            break
        }
    }

    println(answer)
}
