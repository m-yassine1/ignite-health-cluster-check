package com.ignite.cluster.health.igniteclusterhealth

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class IgniteClusterHealthApplication

fun main(args: Array<String>) {
	runApplication<IgniteClusterHealthApplication>(*args)
}
