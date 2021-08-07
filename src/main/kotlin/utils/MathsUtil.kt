package utils

import java.util.*

object MathsUtil {
    fun generatePermutations(range: IntRange): List<List<Int>> {
        val permutations = mutableListOf<List<Int>>()

        // Heap's algorithm - https://en.wikipedia.org/wiki/Heap%27s_algorithm
        fun generatePermutations(k: Int, list: List<Int>) {
            if (k == 1) {
                // Make a copy of the original list before adding as it is mutating continuously
                permutations.add(list.toList())
            } else {
                for (i in 0 until k) {
                    generatePermutations(k - 1, list)
                    if (k % 2 == 0) {
                        Collections.swap(list, i, k - 1)
                    } else {
                        Collections.swap(list, 0, k - 1)
                    }
                }
            }
        }

        generatePermutations(range.count(), range.toList())
        return permutations
    }
}