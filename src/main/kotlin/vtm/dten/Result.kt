package io.github.offlinebrain.vtm.tend

sealed class Result {
    data class CriticalSuccess(val total: Int, val tens: Int = 0, val sum: Int, val values: Map<Int, Int>) : Result() {
        override fun prettyString(): String {
            return this.toString().green()
        }
    }

    data class CriticalFailure(val total: Int, val ones: Int = 0, val sum: Int, val values: Map<Int, Int>) : Result() {
        override fun prettyString(): String {
            return this.toString().red()
        }
    }

    data class Success(val success: Int, val failure: Int, val sum: Int, val values: Map<Int, Int>) : Result() {
        override fun prettyString(): String {
            return this.toString().blue()
        }
    }

    data class Failure(val success: Int, val failure: Int, val sum: Int, val values: Map<Int, Int>) : Result() {
        override fun prettyString(): String {
            return this.toString().yellow()
        }
    }

    data object None : Result() {
        override fun prettyString(): String {
            return this.toString().red()
        }
    }

    abstract fun prettyString(): String

    companion object {
        fun String.red(): String {
            return "\u001b[31m$this\u001b[0m"
        }

        fun String.green(): String {
            return "\u001b[32m$this\u001b[0m"
        }

        fun String.yellow(): String {
            return "\u001b[33m$this\u001b[0m"
        }

        fun String.blue(): String {
            return "\u001b[34m$this\u001b[0m"
        }
    }
}
