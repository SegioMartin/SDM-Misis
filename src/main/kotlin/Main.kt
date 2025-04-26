package org.example

fun bfs(graph: Map<Node, List<Node>>, start: Node, goal: Node): List<Node>? {
    val queue = ArrayDeque<List<Node>>()
    val visited = mutableSetOf<Node>()
    queue.add(listOf(start))

    while (queue.isNotEmpty()) {
        val path = queue.removeFirst()
        val node = path.last()

        if (node == goal) return path
        if (node in visited) continue

        visited.add(node)
        for (neighbor in graph[node] ?: emptyList()) {
            if (neighbor !in visited) {
                queue.add(path + neighbor)
            }
        }
    }

    return null // путь не найден
}

fun dfs(graph: Map<Node, List<Node>>, start: Node, goal: Node): List<Node>? {
    val stack = ArrayDeque<List<Node>>()
    val visited = mutableSetOf<Node>()
    stack.add(listOf(start))

    while (stack.isNotEmpty()) {
        val path = stack.removeLast()
        val node = path.last()

        if (node == goal) return path
        if (node in visited) continue

        visited.add(node)
        for (neighbor in graph[node] ?: emptyList()) {
            if (neighbor !in visited) {
                stack.add(path + neighbor)
            }
        }
    }

    return null
}

fun bidirectionalSearch(graph: Map<Node, List<Node>>, start: Node, goal: Node): List<Node>? {
    if (start == goal) return listOf(start)

    val queueStart = ArrayDeque<List<Node>>()
    val queueGoal = ArrayDeque<List<Node>>()
    val visitedStart = mutableMapOf<Node, List<Node>>()
    val visitedGoal = mutableMapOf<Node, List<Node>>()

    queueStart.add(listOf(start))
    visitedStart[start] = listOf(start)

    queueGoal.add(listOf(goal))
    visitedGoal[goal] = listOf(goal)

    while (queueStart.isNotEmpty() && queueGoal.isNotEmpty()) {
        val meeting = expandFrontier(queueStart, visitedStart, visitedGoal, graph, true)
        if (meeting != null) return meeting

        val meeting2 = expandFrontier(queueGoal, visitedGoal, visitedStart, graph, false)
        if (meeting2 != null) return meeting2
    }

    return null
}

private fun expandFrontier(
    queue: ArrayDeque<List<Node>>,
    visitedThisSide: MutableMap<Node, List<Node>>,
    visitedOtherSide: MutableMap<Node, List<Node>>,
    graph: Map<Node, List<Node>>,
    fromStart: Boolean
): List<Node>? {
    val path = queue.removeFirst()
    val node = path.last()

    for (neighbor in graph[node] ?: emptyList()) {
        if (neighbor in visitedThisSide) continue

        val newPath = path + neighbor
        visitedThisSide[neighbor] = newPath
        queue.add(newPath)

        if (neighbor in visitedOtherSide) {
            val pathFromStart = if (fromStart) newPath else visitedOtherSide[neighbor]!!
            val pathFromGoal = if (fromStart) visitedOtherSide[neighbor]!! else newPath

            // Исключаем встречный узел в одной из частей, чтобы не было повтора
            return pathFromStart + pathFromGoal.dropLast(1).reversed()
        }
    }
    return null
}

fun findConnectedWithinNSteps(start: Node, graph: Map<Node, List<Node>>, n: Int): Set<Node> {
    val visited = mutableSetOf<Node>()
    val queue = ArrayDeque<Pair<Node, Int>>() // Pair: node + текущая длина пути

    queue.add(start to 0)
    visited.add(start)

    while (queue.isNotEmpty()) {
        val (current, depth) = queue.removeFirst()

        if (depth >= n) continue

        for (neighbor in graph[current] ?: emptyList()) {
            if (neighbor !in visited) {
                visited.add(neighbor)
                queue.add(neighbor to (depth + 1))
            }
        }
    }

    // Убираем саму стартовую вершину
    return visited - start
}

fun doSearches(graph: BipartiteGraph, start: Node, target: Node) {
    println("BFS path:")
    println(bfs(graph.graph, start, target))

    println("\nDFS path:")
    println(dfs(graph.graph, start, target))

    println("\nBidirectional path:")
    println(bidirectionalSearch(graph.graph, start, target))
}

fun doConnections(graph: BipartiteGraph, target: Node) {
    val connectedN2 = findConnectedWithinNSteps(target, graph.graph, n = 2)
    println("Кол-во вершин, связанных при n=2: ${connectedN2.size}")
    println("Вершины: $connectedN2")

    val connectedN3 = findConnectedWithinNSteps(target, graph.graph, n = 3)
    println("Кол-во вершин, связанных при n=3: ${connectedN3.size}")
    println("Вершины: $connectedN3")
}

fun main() {
    // Real
    val (b, d) = Pair(9, 7) //real
    val target = Node(5, 50)

    // Test
//    val (b, d) = Pair(2, 4) //test
//    val target = Node(2, 2)

    val graph = BipartiteGraph(b, d)
//    graph.printGraph()

    val start = Node(0, 0)

    doSearches(graph, start, target)

    doConnections(graph, target)
}