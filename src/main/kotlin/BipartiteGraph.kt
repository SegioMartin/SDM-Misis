package org.example

import kotlin.math.pow

class BipartiteGraph(private val branchingFactor: Int, private val depth: Int) {
    val graph: MutableMap<Node, MutableList<Node>> = mutableMapOf()

    init {
        buildGraph()
    }

    private fun buildGraph() {
        val levelNodes = mutableMapOf<Int, MutableList<Node>>()

        for (d in 0 until depth) {
            levelNodes[d] = mutableListOf()
            val numNodes = branchingFactor.toDouble().pow(d.toDouble()).toInt()
            for (i in 0 until numNodes) {
                val node = Node(d, i)
                levelNodes[d]!!.add(node)
                graph[node] = mutableListOf()
            }
        }

        for (d in 0 until depth - 1) {
            val currentLevel = levelNodes[d]!!
            val nextLevel = levelNodes[d + 1]!!

            for ((i, node) in currentLevel.withIndex()) {
                val startChild = i * branchingFactor
                for (j in 0 until branchingFactor) {
                    val child = nextLevel[startChild + j]
                    graph[node]?.add(child)
                    graph[child]?.add(node) // неориентированное соединение
                }
            }
        }
    }

    fun printGraph() {
        for ((node, neighbors) in graph) {
            println("$node -> ${neighbors.joinToString(", ")}")
        }
    }
}