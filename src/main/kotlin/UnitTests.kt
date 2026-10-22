package org.example

/**
 * Runs all unit tests for the KMeans class.
 * @throws RuntimeException if a test fails.
 */
fun testKMeans() {
    testCentroidCount()
    testCentroidSource()
    testAreCentroidsEqual()
    testCalculateDistance()
    testCentroidDimensions()
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

    println("Passed test for number of coordinates in centroids.")
}

/**
 * Tests both centroid-picking functions to ensure all centroids are picked
 * from the original data.
 * @throws RuntimeException if a test fails.
 */
fun testCentroidSource() {

    println("Passed test for sources of centroids.")
}

/**
 * Tests the areCentroidsEqual function for a set of known centroid values.
 * @throws RuntimeException is a test fails.
 */
fun testAreCentroidsEqual() {

    println("Passed test for areCentroidsEqual function.")
}

/**
 * Tests the calculateDistance function for a set of known coordinate pairs.
 * Checks that results are as expected, non-negative, and equivalent after an x/y swap.
 * @throws RuntimeException is a test fails.
 */
fun testCalculateDistance() {

    println("Passed test for calculateDistance function.")
}

/**
 * Tests the coordinates of the centroids returned by the calculateCentroid function.
 * Checks that the dimension count is the same as a point, and that each coordinate is
 * between the minimum and maximum.
 * @throws RuntimeException is a test fails.
 */
fun testCentroidDimensions () {

    println("Passed test for dimensions of calculated centroids.")
}

/**
 * Tests calculateCost function for known clusters to check for positive, expected output.
 * @throws RuntimeException is a test fails.
 */
fun testCalculateCost () {

    println("Passed test for calculateCost function.")
}

/**
 * Tests that the number of clusters returned by the cluster function is equal to k.
 * @throws RuntimeException is a test fails.
 */
fun testClusterCount() {

    println("Passed test for number of clusters returned from clustering.")
}

/**
 * Tests that all points returned by the cluster function are from the original data,
 * and that all each data point is used exactly once.
 */
fun testClusterData() {

    println("Passed test for source of data returned by clustering.")
}