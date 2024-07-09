package io.github.offlinebrain

import io.github.offlinebrain.vtm.tend.Cubes
import io.github.offlinebrain.vtm.tend.Rules
import io.github.offlinebrain.vtm.tend.Settings

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    println("Set difficulty level [2-10]")
    Settings.difficulty = readInt()

    println("Set success threshold [1-10]")
    Settings.threshold = readInt()


    while (true) {
        print("Roll dices: ")
        val num = readInt()

        val roll = Cubes.roll(num)
        val result = Rules.check(roll)

        println(result.prettyString())
    }
}

private fun readInt(): Int {
    try {
        return readln().toInt()
    } catch (e: NumberFormatException) {
        println("Incorrect number format")
        return readInt()
    }
}