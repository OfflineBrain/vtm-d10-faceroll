package io.github.offlinebrain.vtm.tend

object Rules {
    fun check(throws: List<Int>): Result {
        //Empty
        if (throws.isEmpty()) return Result.None

        val (successful, failure) = throws.partition { it >= Settings.difficulty }
        val mutSuccess = successful.toMutableList()
        val mutFailure = failure.toMutableList()


        var bonkers = mutSuccess.count { it == 10 }
        var dicks = mutFailure.count { it == 1 }

        //Critical failure
        val allFailure = failure.size == throws.size
        if (allFailure) {
            if (dicks > 1) return Result.CriticalFailure(failure.size, dicks, throws.sum(), throws.groupCounting());
            else return Result.Failure(successful.size, failure.size, throws.sum(), throws.groupCounting());
        }


        rollExtra(bonkers, dicks, mutSuccess, mutFailure)
        bonkers = mutSuccess.count { it == 10 }
        dicks = mutFailure.count { it == 1 }


        //Critical success
        if (mutFailure.isEmpty()) return Result.CriticalSuccess(mutSuccess.size, bonkers, mutSuccess.sum(), mutSuccess.groupCounting())
        //Critical failure
        if (mutSuccess.isEmpty() && dicks > 1) return Result.CriticalFailure(
            mutFailure.size,
            dicks,
            mutFailure.sum(),
            mutFailure.groupCounting()
        );
        //Success
        if (mutSuccess.size >= Settings.threshold) return Result.Success(
            mutSuccess.size,
            mutFailure.size,
            mutSuccess.sum() + mutFailure.sum(),
            (mutSuccess + mutFailure).groupCounting()
        )
        //Failure
        return Result.Failure(
            mutSuccess.size,
            mutFailure.size,
            mutSuccess.sum() + mutFailure.sum(),
            (mutSuccess + mutFailure).groupCounting()
        )
    }

    private fun rollExtra(
        bonkers: Int,
        dicks: Int,
        successful: MutableList<Int>,
        failure: MutableList<Int>
    ): Pair<Int, Int> {
        var mBonkers = bonkers
        var mDicks = dicks
        if (mBonkers > 0) {
            println("Extra roll!")
            val extra = Cubes.roll(mBonkers)
            for (i in extra) {
                mBonkers--
                when {
                    i == 1 -> {
                        mDicks++
                        if (successful.isNotEmpty()) {
                            successful.removeLast()
                        }
                        failure += i
                    }

                    i == 10 -> {
                        mBonkers++
                        successful += i
                    }

                    i >= Settings.difficulty -> {
                        successful += i
                        successful.sort()
                    }

                    else -> {
                        failure += i
                    }
                }
            }
        }

        if (mBonkers > 0) {
            return rollExtra(mBonkers, mDicks, successful, failure)
        }

        return Pair(bonkers, dicks)
    }
}

fun List<Int>.groupCounting() = this.groupingBy { it }.eachCount()