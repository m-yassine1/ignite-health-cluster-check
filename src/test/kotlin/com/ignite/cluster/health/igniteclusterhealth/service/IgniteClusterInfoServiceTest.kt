package com.ignite.cluster.health.igniteclusterhealth.service

import com.ignite.cluster.health.igniteclusterhealth.mock.models.MockBaselineNode
import com.ignite.cluster.health.igniteclusterhealth.mock.models.MockClusterNode
import com.ignite.cluster.health.igniteclusterhealth.service.IgniteClusterInfoService
import com.ignite.cluster.health.igniteclusterhealth.service.NodeType
import com.ignite.cluster.health.igniteclusterhealth.utilities.IgniteTestUtilities
import com.ignite.cluster.health.igniteclusterhealth.utilities.getIgniteNodes
import com.ignite.cluster.health.igniteclusterhealth.utilities.stopIgniteNode
import com.ignite.cluster.health.igniteclusterhealth.utilities.stopIgniteNodes
import io.mockk.every
import io.mockk.mockk
import org.apache.ignite.Ignite
import org.apache.ignite.IgniteCluster
import org.apache.ignite.cluster.ClusterGroup
import org.apache.ignite.cluster.ClusterState
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import java.util.*

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class IgniteClusterInfoServiceTest {
    @Test
    @Order(1)
    fun `Get Cluster ID`() {
        assertEquals(clientNodes[0].cluster().id().toString(), igniteClusterInfoService.getClusterId())
    }

    @Test
    @Order(2)
    fun `Get Cluster Active`() {
        assertEquals(clientNodes[0].cluster().state(), ClusterState.ACTIVE)
    }

    @Test
    @Order(3)
    fun `Number of Server Nodes is Correct`() {
        assertEquals(2, igniteClusterInfoService.getNodes(NodeType.SERVER).size)
    }

    @Test
    @Order(4)
    fun `Number of Client Nodes is Correct`() {
        assertEquals(1, igniteClusterInfoService.getNodes(NodeType.CLIENT).size)
    }

    @Test
    @Order(5)
    fun `Number of Daemon Nodes is Correct`() {
        assertEquals(0, igniteClusterInfoService.getNodes(NodeType.DAEMON).size)
    }

    @Test
    @Order(6)
    fun `Number of Nodes is Correct`() {
        assertEquals(3, igniteClusterInfoService.getNodes(NodeType.ALL).size)
    }

    @Test
    @Order(7)
    fun `Number of Baseline Nodes is Correct`() {
        assertEquals(2, igniteClusterInfoService.getBaselineNodes().size)
    }

    @Test
    @Order(8)
    fun `Check if all baseline nodes are up`() {
        assertEquals(true, igniteClusterInfoService.areAllBaseLineNodesUp())
    }

    @Test
    @Order(9)
    fun `Check if all baseline nodes are down`() {
        assertEquals(2, igniteClusterInfoService.getUpBaselineNodes().size)
    }

    @Test
    @Order(10)
    fun `Check number of baseline nodes that are down`() {
        assertEquals(0, igniteClusterInfoService.getDownBaselineNodes().size)
    }

    @Test
    @Order(11)
    fun `Check number of baseline nodes that are up and down after turning off one server`() {
        stopIgniteNode(serverNodes[0].name())
        Thread.sleep(4000)
        assertEquals(1, igniteClusterInfoService.getDownBaselineNodes().size)
    }

    @Test
    @Order(12)
    fun `Number of Server Nodes is Correct after turning off one server`() {
        assertEquals(1, igniteClusterInfoService.getNodes(NodeType.SERVER).size)
    }

    @Test
    @Order(13)
    fun `Number of Nodes is Correct after turning off one server`() {
        assertEquals(2, igniteClusterInfoService.getNodes(NodeType.ALL).size)
    }

    @Test
    @Order(14)
    fun `Number of Baseline Nodes is Correct after turning off one server`() {
        assertEquals(2, igniteClusterInfoService.getBaselineNodes().size)
    }

    @Test
    @Order(15)
    fun `Get Cluster Active after turning off one server`() {
        assertEquals(clientNodes[0].cluster().state(), ClusterState.ACTIVE)
    }

    @Test
    @Order(16)
    fun `Check number of baseline nodes that are up after turning off one server`() {
        assertEquals(1, igniteClusterInfoService.getUpBaselineNodes().size)
    }

    @Test
    @Order(17)
    fun `Check number of client nodes that are up after adding client`() {
        getIgniteNodes("ignite-cluster-info-test-client-", 1, isClientMode = true, hasDataConfiguration = false)
        Thread.sleep(4000)
        assertEquals(2, igniteClusterInfoService.getNodes(NodeType.CLIENT).size)
        assertEquals(3, igniteClusterInfoService.getNodes(NodeType.ALL).size)
    }

    @Test
    @Order(18)
    fun `Check number of baseline nodes that are up after adding none baseline server`() {
        getIgniteNodes("ignite-cluster-info-test-server-", 1, isClientMode = false, hasDataConfiguration = false)
        Thread.sleep(4000)
        assertEquals(2, igniteClusterInfoService.getNodes(NodeType.SERVER).size)
        assertEquals(2, igniteClusterInfoService.getBaselineNodes().size)
        assertEquals(1, igniteClusterInfoService.getUpBaselineNodes().size)
        assertEquals(4, igniteClusterInfoService.getNodes(NodeType.ALL).size)
    }

    companion object {
        private lateinit var igniteClusterInfoService: IgniteClusterInfoService
        private lateinit var serverNodes: List<Ignite>
        private lateinit var clientNodes: List<Ignite>

        @JvmStatic
        @BeforeAll
        fun beforeAll() {
            stopIgniteNodes()
            serverNodes = getIgniteNodes(
                "ignite-cluster-info-test-server-", 2,
                isClientMode = false,
                hasDataConfiguration = true
            )
            clientNodes = getIgniteNodes(
                "ignite-cluster-info-test-client-",
                1, isClientMode = true, hasDataConfiguration = false
            )

            igniteClusterInfoService =
                IgniteClusterInfoService(clientNodes[0])
        }

        @JvmStatic
        @AfterAll
        fun afterAll() {
            stopIgniteNodes()
            Thread.sleep(4000)
        }
    }
}