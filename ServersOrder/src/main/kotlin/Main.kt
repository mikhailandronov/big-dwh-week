package org.ama.contest

fun main() {
    val numOfServers = readln().trim().toInt()
    val serverWeights = readln().trim()
        .split(' ')
        .filter { it.isNotEmpty() }
        .map { it.toInt() }
        .toList()



    val res = mutableListOf<Int>(0)
    var accEnergy = 0
    for (i in 1 .. serverWeights.lastIndex){
        val curWeight = serverWeights[i]
        val sortedWeights = serverWeights.subList(0, i).sorted()
        var sumEnergy = 0
        var curPos = i - 1
        while (curPos >= 0 && curWeight < sortedWeights[curPos]){
            sumEnergy += sortedWeights[curPos]
            curPos--
        }
        accEnergy += sumEnergy
        res.add(accEnergy)
    }
    println(res.joinToString(" "))
}
