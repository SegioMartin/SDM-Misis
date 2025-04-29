package org.example


/**
 * @property size количество вершин графа
 * @property adjacencyList отображение вершины в список её исходящих рёбер
 */
class Graph(val size: Int) {
    val adjacencyList: MutableMap<Int, MutableList<Edge>> = mutableMapOf()

    init {
        for (i in 0 until size) {
            adjacencyList[i] = mutableListOf()
        }
    }

    fun addEdge(from: Int, to: Int, weight: Int) {
        adjacencyList[from]?.add(Edge(to, weight))
        adjacencyList[to]?.add(Edge(from, weight))
    }

    override fun toString(): String {
        val builder = StringBuilder()
        for ((vertex, edges) in adjacencyList) {
            builder.append("Вершина $vertex:\n")
            for (edge in edges) {
                builder.append("  -> ${edge.to} (вес: ${edge.weight})\n")
            }
        }
        return builder.toString()
    }
}
