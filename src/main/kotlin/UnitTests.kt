package org.example

/**
 * Runs all unit tests for the KMeans class.
 * @throws RuntimeException if a test fails.
 */
fun testKMeans() {
    testCentroidCount()
    testCentroidSource()
    testCalculateCost()
    testClusterCount()
    testClusterData()
    println("Passed all unit tests for k-means clustering.")
}

/**
 * Tests both centroid-picking functions to ensure they pick the expected
 * number of centroids.
 * @throws RuntimeException if a test fails.
 */
fun testCentroidCount() {
    val data = generateData(500.0, 30)
    val means = KMeans(data)
    for (i in 1..10) {
        val centroidsR = means.pickRandomCentroids(i)
        if (centroidsR.size != i)
            throw RuntimeException("Incorrect random centroid count at k=${i}")
        val centroidsP = means.pickPlusPlusCentroids(i)
        if (centroidsP.size != i)
            throw RuntimeException("Incorrect plus plus centroid count at k=${i}")
    }
    println("Passed test for number of coordinates in centroids.")
}

/**
 * Tests both centroid-picking functions to ensure all centroids are picked
 * from the original data.
 * @throws RuntimeException if a test fails.
 */
fun testCentroidSource() {
    val data = generateData(500.0, 30)
    val means = KMeans(data)
    for (i in 1..10) {
        val centroidsR = means.pickRandomCentroids(i)
        for (centroid in centroidsR)
            if (!data.contains(centroid))
                throw RuntimeException("Random centroid not found at k=${i}")
        val centroidsP = means.pickPlusPlusCentroids(i)
        for (centroid in centroidsP)
            if (!data.contains(centroid))
                throw RuntimeException("Plus plus centroid not found at k=${i}")
    }
    println("Passed test for sources of centroids.")
}

/**
 * Tests calculateCost function for known clusters to check for positive, expected output.
 * @throws RuntimeException is a test fails.
 */
fun testCalculateCost () {
    val sampleResults = listOf(
        listOf(listOf(listOf(1.0, 1.0)), listOf(listOf(5.0, 5.0))), // clusters with one point each
        listOf(listOf(listOf(1.0, 1.0), listOf(4.0, 5.0))), // one cluster with two points
        listOf(listOf(listOf(1.0, 1.0)), listOf(listOf(-1.0, -1.0), listOf(-4.0, -5.0))) // clusters of different sizes
    )
    val expectedCosts = listOf(
        0.0,
        6.25*2.0,
        6.25*2.0
    )

    for (idx in expectedCosts.indices) {
        val means = KMeans(listOf(listOf(1.0,1.0)))
        val cost = means.calculateCost(sampleResults[idx])
        if (kotlin.math.abs(cost - expectedCosts[idx]) > 0.01)
            throw RuntimeException("Cost calculation failed for round ${idx+1}")
    }
    println("Passed test for calculateCost function.")
}

/**
 * Tests that the number of clusters returned by the cluster function is equal to k.
 * @throws RuntimeException is a test fails.
 */
fun testClusterCount() {
    for (k in 1..10) {
        val data = generateData(100.0, k)
        val means = KMeans(data)
        val (clusters, _) = means.cluster(k)
        if (clusters.size != k)
            throw RuntimeException("Returned wrong number of clusters at k=$k")
    }
    println("Passed test for number of clusters returned from clustering.")
}

/**
 * Tests that all points returned by the cluster function are from the original data,
 * and that all each data point is used exactly once.
 */
fun testClusterData() {
    for (k in 1..10) {
        val data = generateData(100.0, k).toMutableList()
        val means = KMeans(data)
        val (clusters, _) = means.cluster(k)
        for (cluster in clusters) {
            for (point in cluster) {
                val idx = data.indexOf(point)
                if (idx == -1)
                    throw RuntimeException("Data in cluster not found in original data")
                data.removeAt(idx)
            }
        }
        if (data.isNotEmpty())
            throw RuntimeException("Not all points from data found in clusters")
    }
    println("Passed test for source of data returned by clustering.")
}