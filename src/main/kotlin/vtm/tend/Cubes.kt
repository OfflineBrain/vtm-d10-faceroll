package io.github.offlinebrain.vtm.tend

object Cubes {
    private val values = 1..10
    fun roll(amount: Int): List<Int> {
        if (amount <= 0) {
            return emptyList()
        }

        return generateSequence { values.random() }.take(amount).sorted().toList()
    }
}