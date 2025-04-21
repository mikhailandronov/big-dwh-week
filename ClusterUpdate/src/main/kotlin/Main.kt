package org.ama.contest

fun main() {
    val numOfClusters = readln().trim().toInt()
    val clusterFrames = Array<Frame>(numOfClusters) {
        val input = readln().trim().split(' ')
        Frame(input.first().toInt(), input.last().toInt(), it)
    }

    val selectedFrames: MutableSet<Frame> = mutableSetOf()
    var totalLength = 0

    clusterFrames.sortBy { it.len }
    clusterFrames.forEach { frame ->
        if (frame.intersects(selectedFrames)) {
            if (frame.len > totalLength) {
                selectedFrames.clear()
                selectedFrames.add(frame)
                totalLength = frame.len
            }
        } else {
            selectedFrames.add(frame)
            totalLength += frame.len
        }
    }

    println(totalLength)
    println(selectedFrames
        .sortedBy { it.cluster }
        .joinToString(" ") { it.cluster.toString() }
    )
}

class Frame(val start: Int, val len: Int, val cluster: Int = 0) {
    fun intersects(compared: Frame): Boolean =
        if ((this.start + this.len - 1 >= compared.start) &&
            (compared.start + compared.len - 1 >= this.start)
        ) true
        else false

    fun intersects(comparedSet: Set<Frame>): Boolean =
        comparedSet.any { it.intersects(this) }
}