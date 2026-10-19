package org.example

fun main() {
    val data = listOf(listOf(81.0, 81.0), listOf(82.0, 82.0), listOf(108.0, 108.0), listOf(109.0, 109.0))
    val means = KMeans(data)
    val clusters = means.cluster(2)
    println(clusters)
}