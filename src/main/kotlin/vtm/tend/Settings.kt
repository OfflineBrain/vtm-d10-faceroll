package io.github.offlinebrain.vtm.tend

object Settings {
    var difficulty: Int = 8
        set(value) {
            if (value < 2 || value > 10) {
                throw IllegalArgumentException("difficulty must be between 2 and 10 (inclusive)");
            }
            field = value
        }

    var threshold: Int = 3
        set(value) {
            if (value < 1 || value > 10) {
                throw IllegalArgumentException("threshold must be between 1 and 10 (inclusive)");
            }
            field = value
        }
}