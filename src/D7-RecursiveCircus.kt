import java.util.*

fun main(args: Array<String>) {
    part1()
    part2()
}

private fun part1() {
    val lines = inputD7.split("\n")
    val nodeRegex = """([a-z]+) \(([0-9]+)\).*""".toRegex()
    val depsRegex = """.* -> ([a-z, ]+)""".toRegex()

    data class Node(val name: String, val weight: Int, val dependencies: List<String>)
    val nodeList = mutableListOf<Node>()

    for (line in lines) {
        val (name, weightStr) = nodeRegex.find(line)!!.destructured
        val dependenciesStr = depsRegex.find(line)?.destructured?.component1()
        val weight = weightStr.toInt()
        val dependencies = dependenciesStr?.split(", ") ?: listOf()

        nodeList.add(Node(name, weight, dependencies))
    }

    val nodeQueue: Queue<Node> = LinkedList(nodeList)
    val foundList = mutableListOf<Node>()
    while (nodeQueue.size > 1) {
        val node = nodeQueue.poll()
        if (node.dependencies.all { foundList.any { foundNode -> foundNode.name == it }}) {
            foundList.add(node)
        } else {
            nodeQueue.add(node)
        }
    }

    println(nodeQueue.poll().name)
}

private fun part2() {
    val lines = inputD7.split("\n")
    val nodeRegex = """([a-z]+) \(([0-9]+)\).*""".toRegex()
    val depsRegex = """.* -> ([a-z, ]+)""".toRegex()

    data class Node(val name: String, val weight: Int, val dependencies: List<String>)
    val nodeList = mutableListOf<Node>()

    for (line in lines) {
        val (name, weightStr) = nodeRegex.find(line)!!.destructured
        val dependenciesStr = depsRegex.find(line)?.destructured?.component1()
        val weight = weightStr.toInt()
        val dependencies = dependenciesStr?.split(", ") ?: listOf()

        nodeList.add(Node(name, weight, dependencies))
    }

    val nodeQueue: Queue<Node> = LinkedList(nodeList)
    val foundList = mutableListOf<Node>()
    while (nodeQueue.size > 1) {
        val node = nodeQueue.poll()
        if (node.dependencies.all { foundList.any { foundNode -> foundNode.name == it }}) {
            foundList.add(node)
        } else {
            nodeQueue.add(node)
        }
    }
    val baseNode = nodeQueue.poll()

    fun findNodeByName(name: String): Node {
        return nodeList.find { it.name == name }!!
    }

    fun sumTreeWeights(node: Node): Int {
        return node.dependencies
                .map(::findNodeByName)
                .map(::sumTreeWeights)
                .sum() + node.weight
    }

    fun findGoodBadWeights(node: Node): Pair<Int?, Int?> {
        val dependencyNodes = node.dependencies.map(::findNodeByName)
        val fullWeights = dependencyNodes.map { it to sumTreeWeights(it) }.toMap()
        val weightCounts = fullWeights
                .values
                .groupingBy { it }
                .eachCount()
        val goodWeight = weightCounts
                .filterValues { it != 1 }
                .entries.firstOrNull()?.key
        val badWeight = weightCounts
                .filterValues { it == 1 }
                .entries.firstOrNull()?.key

        return Pair(goodWeight, badWeight)
    }

    fun findBadNode(node: Node): Node {
        val dependencyNodes = node.dependencies.map(::findNodeByName)
        val (_, badWeight) = findGoodBadWeights(node)

        if (badWeight == null) {
            return node
        } else {
            val badNode = dependencyNodes.find { sumTreeWeights(it) == badWeight }!!
            return findBadNode(badNode)
        }
    }

    val badNode = findBadNode(baseNode)

    val (goodWeight, badWeight) = findGoodBadWeights(baseNode)
    val difference = goodWeight!! - badWeight!!

    println(badNode.weight + difference)
}

