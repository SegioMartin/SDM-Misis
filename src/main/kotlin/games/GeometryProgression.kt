package org.example.games

import kotlin.math.pow
import kotlin.random.Random

class GeometryProgression : Base() {
    override val gameRules: String = "What number is missing in the progression?"
    private var numbers: List<Int> = emptyList()
    private var missingIndex: Int = -1

    override fun getRightAnswer(): String = numbers[missingIndex].toString()

    override fun getRandomQuestion(): String {
        val first = Random.nextInt(1, 10).toDouble()
        val commonRatio = Random.nextInt(1, 10).toDouble()
        val size = Random.nextInt(5, 10)
        numbers = List(size) { (first * commonRatio.pow(it)).toInt() }
        missingIndex = Random.nextInt(numbers.size)

        val question = numbers.map { it.toString() }.toMutableList()
        question[missingIndex] = ".."
        return question.joinToString(", ")
    }
}
