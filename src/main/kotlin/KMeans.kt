package org.example

/**
 *
 * @property data a 2D list, in which each list is a data point and each item in the list is a
 * coordinate. Each data point should have the same number of coordinates.
 */
class KMeans(val data: List<List<Double>>) {

    /**
     *
     */
    fun cluster(k: Int, initialCentroids: List<List<Double>> = pickRandomCentroids(k)): List<List<List<Double>>> {
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
            println(centroids)
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
            centroids.add(data[kotlin.random.Random.nextInt(data.size)])
        return centroids
    }

    /**
     *
     */
    fun pickPlusPlusCentroids() {

    }

    /**
     * @return the Euclidean distance between two points
     * @param point a list of coordinates for a point
     * @param centroid a list of coordinates for a centroid
     * @throws IllegalArgumentException if points have different numbers of coordinates
     */
    private fun calculateDistance(point: List<Double>, centroid: List<Double>): Double {
        if (point.size != centroid.size)
            throw IllegalArgumentException("points have different numbers of coordinates")
        var distance = 0.0
        for (i in point.indices) {
            val coordDist = point[i]-centroid[i]
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
            return data[0].indices.map { Double.POSITIVE_INFINITY }
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