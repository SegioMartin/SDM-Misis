package org.example
import java.util.*
import kotlin.random.Random

fun generateRandomGraph(n: Int, minEdges: Int): Graph {
    val graph = Graph(n)
    val edgeSet = mutableSetOf<Pair<Int, Int>>()

    while (edgeSet.size < minEdges) {
        val from = Random.nextInt(n)
        var to = Random.nextInt(n)
        while (to == from) {
            to = Random.nextInt(n)
        }

        val edge = if (from < to) Pair(from, to) else Pair(to, from)
        if (edge !in edgeSet) {
            val weight = Random.nextInt(1, 20) // случайный вес от 1 до 20
            graph.addEdge(from, to, weight)
            edgeSet.add(edge)
        }
    }

    return graph
}

fun dijkstra(graph: Graph, start: Int): Pair<IntArray, IntArray?> {
    val distances = IntArray(graph.size) { Int.MAX_VALUE }
    val previous = IntArray(graph.size) { -1 }
    val visited = BooleanArray(graph.size)

    val queue = PriorityQueue(compareBy<Pair<Int, Int>> { it.second })
    distances[start] = 0
    queue.add(Pair(start, 0))

    while (queue.isNotEmpty()) {
        val (current, _) = queue.poll()
        if (visited[current]) continue
        visited[current] = true

        for (edge in graph.adjacencyList[current]!!) {
            val neighbor = edge.to
            val newDist = distances[current] + edge.weight
            if (newDist < distances[neighbor]) {
                distances[neighbor] = newDist
                previous[neighbor] = current
                queue.add(Pair(neighbor, newDist))
            }
        }
    }

    return Pair(distances, previous)
}

fun aStar(graph: Graph, start: Int, goal: Int): Pair<Int, List<Int>> {
    val openSet = PriorityQueue(compareBy<Pair<Int, Int>> { it.second }) // Pair(node, fScore)
    val cameFrom = mutableMapOf<Int, Int>()
    val gScore = IntArray(graph.size) { Int.MAX_VALUE }
    val fScore = IntArray(graph.size) { Int.MAX_VALUE }

    gScore[start] = 0
    fScore[start] = 0 // эвристика h(n) = 0

    openSet.add(Pair(start, fScore[start]))

    while (openSet.isNotEmpty()) {
        val (current, _) = openSet.poll()

        if (current == goal) {
            // восстановление пути
            val path = mutableListOf<Int>()
            var node = goal
            while (node != start) {
                path.add(node)
                node = cameFrom[node] ?: break
            }
            path.add(start)
            path.reverse()
            return Pair(gScore[goal], path)
        }

        for (edge in graph.adjacencyList[current]!!) {
            val neighbor = edge.to
            val tentativeG = gScore[current] + edge.weight
            if (tentativeG < gScore[neighbor]) {
                cameFrom[neighbor] = current
                gScore[neighbor] = tentativeG
                fScore[neighbor] = tentativeG // + 0, т.к. h = 0
                openSet.add(Pair(neighbor, fScore[neighbor]))
            }
        }
    }

    return Pair(-1, emptyList()) // путь не найден
}


fun main() {
    val n = 5
    val minEdges = 2 * n
    val graph = generateRandomGraph(n, minEdges)

    println("Граф с $n вершинами и минимум $minEdges рёбрами создан.")
    println(graph)

    val start = 0
    val end = 4

    val (distances, _) = dijkstra(graph, start)
    println("Кратчайшие расстояния от вершины $start: ${distances.joinToString()}")

    val (length, path) = aStar(graph, start, end)
    println("Путь от $start до $end: $path (длина: $length)")
}
