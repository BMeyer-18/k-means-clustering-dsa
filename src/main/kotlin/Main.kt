package org.example

fun main() {
    val data = generateData(10.0, 5)
    //println(data)
    val means = KMeans(data)
    val clusters = means.cluster(2)
}