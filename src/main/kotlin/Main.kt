package org.example

import java.io.File

fun main() {
    val data = generateData(100.0, 30)
    val means = KMeans(data)
    val clusters = means.cluster(5)

    File("src/main/resources/data.csv").printWriter().use { out ->
        for (cluster in clusters)
            for (point in cluster)
                out.println("${point[0]},${point[1]}")
    }

    File("src/main/resources/sizes.csv").printWriter().use { out ->
        for (cluster in clusters)
            out.println("${cluster.size}")
    }
}