package org.example

fun main() {
    val data = listOf(listOf(1.0, 1.0), listOf(2.0, 2.0), listOf(8.0, 8.0), listOf(9.0, 9.0))
    val means = KMeans(data)
    val clusters = means.cluster(2)
    println(clusters)
}