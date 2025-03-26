package games

import org.example.games.Base
import kotlin.random.Random

class LCM : Base() {
    override val gameRules: String = "Find the smallest common multiple of given numbers."
    private var numbers: List<Int> = emptyList()

    private fun lcm(
        a: Int,
        b: Int,
    ): Int {
        val larger = if (a > b) a else b
        val maxLcm = a * b
        var lcm = larger
        while (lcm <= maxLcm) {
            if (lcm % a == 0 && lcm % b == 0) {
                return lcm
            }
            lcm += larger
        }
        return maxLcm
    }

    override fun getRightAnswer(): String = lcm(lcm(numbers[0], numbers[1]), numbers[2]).toString()

    override fun getRandomQuestion(): String {
        numbers = List(3) { Random.nextInt(1, 10) }
        return numbers.joinToString(", ")
    }
}
