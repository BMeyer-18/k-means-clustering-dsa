package org.example

import kotlin.random.Random

/**
 * Generates random (hopefully) cluster-able data, centered around a set of initial centroids.
 * Each centroid gets [points] points around it, which are up to [stagger] units away from
 * their respective centroid on the x and y axes.
 * @return a list of data centered around the clusters
 */
fun generateData(stagger: Double, points: Int): List<List<Double>> {
    val initialPoints = listOf(listOf(-125.0, 450.0), listOf(200.0, 150.0), listOf(50.0, -50.0), listOf(-250.0, -275.0), listOf(295.0, -280.0))
    val data = mutableListOf<List<Double>>()
    for ((x, y) in initialPoints)
        for (i in 0..<points)
            data.add(listOf(x+Random.nextDouble(-stagger, stagger),
                            y+Random.nextDouble(-stagger, stagger)))
    return data
}