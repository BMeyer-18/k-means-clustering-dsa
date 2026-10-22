package org.example

import kotlin.random.Random

/**
 *
 * @property data a 2D list, in which each list is a data point and each item in the list is a
 * coordinate. Each data point should have the same number of coordinates.
 */
class KMeans(val data: List<List<Double>>) {

    /**
     * Categorize the data into [k] clusters based on the k-means clustering algorithm,
     */
    fun cluster(k: Int, initialCentroids: List<List<Double>> = pickRandomCentroids(k)): List<List<List<Double>>> {
        if (k != initialCentroids.size)
            throw IllegalArgumentException("Number of centroids is not equal to number of desired clusters")

        val clusters = (0..<k).map { mutableListOf<List<Double>>() }.toMutableList()
        var centroids = initialCentroids

        while (true) {
            // empty clusters
            for (i in clusters.indices)
                clusters[i] = mutableListOf()
            // fill clusters based on current centroids
            for (i in data.indices) {
                var closestIdx = 0
                var minDistance = calculateDistance(data[i], centroids[0])
                // add point to cluster with closest centroid
                for (j in 1..<centroids.size) {
                    val dist = calculateDistance(data[i], centroids[j])
                    if (dist < minDistance) {
                        minDistance = dist
                        closestIdx = j
                    }
                }
                clusters[closestIdx].add(data[i])
            }

            // recalculate centroids based on clusters
            val newCentroids = mutableListOf<List<Double>>()
            for (i in centroids.indices)
                newCentroids.add(calculateCentroid(clusters[i]))

            // check convergence
            //println(centroids)
            if (areCentroidsEqual(centroids, newCentroids))
                return clusters
            centroids = newCentroids
        }
    }

    /**
     * Picks [k] random points from the data to use as initial centroids
     * @return list of centroids, in which each centroid is a list of coordinates
     */
    fun pickRandomCentroids(k: Int): List<List<Double>> {
        val centroids = mutableListOf<List<Double>>()
        for (i in 0..<k)
            centroids.add(data[Random.nextInt(data.size)])
        return centroids
    }

    /**
     * Picks [k] points from the data to use as initial centroids.
     * The first is chosen randomly, and the rest are chosen based on a probability
     * distribution proportional to the point's squared distance to the nearest centroid.
     * This picks initial centroids further away from each other, helping runtime and accuracy.
     * @return list of centroids, in which each centroid is a list of coordinates
     */
    fun pickPlusPlusCentroids(k: Int): List<List<Double>> {
        // choose random point to be first centroid
        val centroids = mutableListOf<List<Double>>()
        centroids.add(data[Random.nextInt(data.size)])

        // choose other k-1 centroids (points likely far from already-selected centroids)
        while (centroids.size < k) {
            // find all points' squared distance to closest centroid
            val sqDists = mutableListOf<Double>()
            for (point in data) {
                var minDist = calculateDistance(point, centroids[0])
                for (idx in 1..<centroids.size) {
                    val dist = calculateDistance(point, centroids[idx])
                    if (dist < minDist)
                        minDist = dist
                }
                sqDists.add(minDist*minDist)
            }

            // Pick a random threshold between 0 and the sum of the squared distances, and iterate
            // through squared distances, keeping a cumulative sum. When the cumulative sum
            // exceeds the random threshold, choose that point as a centroid. This adds a random
            // centroid based on a probability proportional to minimum squared distance.
            val total = sqDists.sum()
            val threshold = Random.nextDouble(total)
            var cumulativeSum = 0.0
            for (idx in data.indices) {
                cumulativeSum += sqDists[idx]
                if (cumulativeSum >= threshold) {
                    centroids.add(data[idx])
                    break
                }
            }
        }

        return centroids
    }

    /**
     * Calculates the cost that k-means clustering attempts to minimize for a group of [clusters],
     * which is the sum of squared distances from each point to its centroid.
     * Divides by the number of points as a means of normalization.
     * @return total cost of clustering
     */
    fun calculateCost(clusters: List<List<List<Double>>>): Double {
        var sumSqDists = 0.0
        for (cluster in clusters) {
            val centroid = calculateCentroid(cluster)
            for (point in cluster) {
                val dist = calculateDistance(point, centroid)
                sumSqDists += dist*dist
            }
        }
        return sumSqDists/data.size.toDouble()
    }

    /**
     * @return the Euclidean distance between two points
     * @param point1 a list of coordinates for a point
     * @param point2 a list of coordinates for another point
     * @throws IllegalArgumentException if points have different numbers of coordinates
     */
    private fun calculateDistance(point1: List<Double>, point2: List<Double>): Double {
        if (point1.size != point2.size)
            throw IllegalArgumentException("points have different numbers of coordinates")
        var distance = 0.0
        for (i in point1.indices) {
            val coordDist = point1[i]-point2[i]
            distance += coordDist*coordDist
        }
        return kotlin.math.sqrt(distance)
    }

    /**
     * @return the point representing the centroid of a cluster
     * @param cluster list of points, in which each point is a list of coordinates
     */
    private fun calculateCentroid(cluster: List<List<Double>>): List<Double> {
        if (cluster.isEmpty())
            return data[Random.nextInt(data.size)]
        val centroid = data[0].indices.map { 0.0 }.toMutableList()
        for (i in cluster.indices)
            for (j in cluster[0].indices)
                centroid[j] += cluster[i][j]
        for (i in centroid.indices)
            centroid[i] /= cluster.size
        return centroid
    }

    /**
     * Compares centroid coordinates to see if [centroids1] is the same as [centroids2]
     * @return true if the centroid lists are the same, false otherwise
     */
    private fun areCentroidsEqual(centroids1: List<List<Double>>, centroids2: List<List<Double>>): Boolean {
        for (i in centroids1.indices)
            for (j in centroids1[i].indices)
                if (centroids1[i][j] != centroids2[i][j])
                    return false
        return true
    }
}