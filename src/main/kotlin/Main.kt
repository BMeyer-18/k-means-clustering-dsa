package org.example

import java.io.File

/**
 * Main function, for testing and getting data
 */
fun main() {
    // runClustering()
    collectClusteringData()
}

/**
 * Runs k-means clustering with some predefined parameters and a randomly
 * generated set of data, then writes outputs to csv files for plotting.
 */
fun runClustering() {
    // run clustering on a sample dataset
    val data = generateData(500.0, 30)
    val means = KMeans(data)
    val centroids = means.pickPlusPlusCentroids(5)
    val (clusters, _) = means.cluster(5, centroids)
    val cost = means.calculateCost(clusters)
    println(cost)

    // write points to a csv file in cluster order
    File("src/main/resources/data.csv").printWriter().use { out ->
        for (cluster in clusters)
            for (point in cluster)
                out.println("${point[0]},${point[1]}")
    }
    
    // write the size of each cluster to a csv file, in the same order as the last file
    File("src/main/resources/sizes.csv").printWriter().use { out ->
        for (cluster in clusters)
            out.println("${cluster.size}")
    }
}

/**
 * Runs k-means clustering with random and plus-plus centroid picking over increasing
 * stagger size, and writes resulting iteration count and cost to a csv file.
 */
fun collectClusteringData() {
    // initialize output arrays, number of repetitions
    val staggers = (0..<10).map { it*50 + 50 }
    val iterations = Array(2) { Array(staggers.size) { 0 } }
    val costs = Array(2) { Array(staggers.size) { 0.0 } }
    val repetitions = 1000

    // get average iteration count and cost for each stagger value
    for (i in staggers.indices) {
        // take averages for data to reduce noise
        for (r in 0..<repetitions) {
            // generate data and initialize KMeans
            val data = generateData(staggers[i].toDouble(), 50)
            val means = KMeans(data)

            // define initial centroids using both approaches
            val randomCentroids = means.pickRandomCentroids(5)
            val plusCentroids = means.pickPlusPlusCentroids(5)

            // take data for clustering using random centroids
            val (randClusters, randIter) = means.cluster(5, randomCentroids)
            costs[0][i] += means.calculateCost(randClusters)
            iterations[0][i] += randIter

            // take data for clustering using plus plus centroids
            val (plusClusters, plusIter) = means.cluster(5, plusCentroids)
            costs[1][i] += means.calculateCost(plusClusters)
            iterations[1][i] += plusIter
        }

        //divide costs and iterations by the number of repetitions to get the mean
        for (j in 0..<2) {
            costs[j][i] /= repetitions
            iterations[j][i] /= repetitions
        }
    }

    // write data to file
    File("src/main/resources/staggerData.csv").printWriter().use { out ->
        out.println("stagger,randCost,randIter,plusCost,plusIter")
        for (idx in staggers.indices)
            out.println("${staggers[idx]},${costs[0][idx]},${iterations[0][idx]},${costs[1][idx]},${iterations[1][idx]}")
    }
}