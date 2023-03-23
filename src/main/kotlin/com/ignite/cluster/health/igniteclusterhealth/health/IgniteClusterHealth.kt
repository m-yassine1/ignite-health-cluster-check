package com.ignite.cluster.health.igniteclusterhealth.health

import com.ignite.cluster.health.igniteclusterhealth.service.IgniteClusterInfoService
import com.ignite.cluster.health.igniteclusterhealth.service.NodeType
import org.apache.ignite.cluster.BaselineNode
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.HealthIndicator
import org.springframework.boot.actuate.health.Status
import org.springframework.stereotype.Component

@Component
class IgniteClusterHealth(
    val igniteClusterInfoService: IgniteClusterInfoService
) : HealthIndicator {
    val log: Logger = LoggerFactory.getLogger(IgniteClusterHealth::class.java)

    override fun health(): Health {
        try {
            if(igniteClusterInfoService.areAllBaseLineNodesUp()) {
                return getHealthDetails(Status.UP)
            }
            return getHealthDetails(Status.DOWN)
        } catch (e: Exception) {
            log.error("Error fetching ignite cluster information", e)
            return Health.down(Exception("Error fetching ignite cluster information: ${e.message}", e)).build()
        }
    }

    private fun getHealthDetails(status: Status): Health {
        val serverNodes = igniteClusterInfoService.getNodes(NodeType.SERVER)
        val daemonNodes = igniteClusterInfoService.getNodes(NodeType.DAEMON)
        val nodes = igniteClusterInfoService.getNodes(NodeType.ALL)
        val clientNodes = igniteClusterInfoService.getNodes(NodeType.CLIENT)
        val upBaseLineNodes = igniteClusterInfoService.getUpBaselineNodes()
        val downBaseLineNodes = igniteClusterInfoService.getDownBaselineNodes()
        val baseLineNodes = igniteClusterInfoService.getBaselineNodes()

        return Health.status(status)
            .withDetail("clusterId", igniteClusterInfoService.getClusterId())
            .withDetail("clusterState", igniteClusterInfoService.getClusterState())
            .withDetail("numberOfActiveServerNodes", serverNodes.size)
            .withDetail("activeServerNodes", getConsistentIds(serverNodes))
            .withDetail("numberOfActiveDaemonNodes", daemonNodes.size)
            .withDetail("activeDaemonNodes", getConsistentIds(daemonNodes))
            .withDetail("numberOfActiveNodes", nodes.size)
            .withDetail("activeNodes", getConsistentIds(nodes))
            .withDetail("numberOfActiveClientNodes", clientNodes.size)
            .withDetail("activeClientNodes", getConsistentIds(clientNodes))
            .withDetail("numberOfUpBaseLineNodes", upBaseLineNodes.size)
            .withDetail("activeUpBaseLineNodes", getConsistentIds(upBaseLineNodes))
            .withDetail("numberOfDownBaseLineNodes", downBaseLineNodes.size)
            .withDetail("activeDownBaseLineNodes", getConsistentIds(downBaseLineNodes))
            .withDetail("numberOfBaseLineNodes", baseLineNodes.size)
            .withDetail("activeBaseLineNodes", getConsistentIds(baseLineNodes))
            .build()
    }

    private fun <T: BaselineNode> getConsistentIds(nodes: List<T>): List<String> {
        return nodes.map { it.consistentId().toString() }
    }
}