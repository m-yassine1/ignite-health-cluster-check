package com.ignite.cluster.health.igniteclusterhealth.mock

import com.ignite.cluster.health.igniteclusterhealth.mock.models.MockBaselineNode
import com.ignite.cluster.health.igniteclusterhealth.mock.models.MockClusterNode
import com.ignite.cluster.health.igniteclusterhealth.service.IgniteClusterInfoService
import com.ignite.cluster.health.igniteclusterhealth.service.NodeType
import io.mockk.every
import io.mockk.mockk
import org.apache.ignite.Ignite
import org.apache.ignite.IgniteCluster
import org.apache.ignite.cluster.ClusterGroup
import org.apache.ignite.cluster.ClusterState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class IgniteClusterInfoServiceTest {
    private val clusterGroup: ClusterGroup = mockk()
    private val igniteCluster: IgniteCluster = mockk()
    val ignite: Ignite = mockk()

    private lateinit var igniteClusterInfoService: IgniteClusterInfoService

    @BeforeEach
    fun beforeEach() {
        every { igniteCluster.forServers() } returns clusterGroup
        every { igniteCluster.forDaemons() } returns clusterGroup
        every { igniteCluster.forClients() } returns clusterGroup
        every { ignite.cluster() } returns igniteCluster
        igniteClusterInfoService = IgniteClusterInfoService(ignite)
    }
    @Test
    fun `Get Cluster ID`() {
        val uuid = UUID.randomUUID()
        every { igniteCluster.id() } returns uuid
        assertEquals(uuid.toString(), igniteClusterInfoService.getClusterId())
    }

    @Test
    fun `Get Cluster Active`() {
        every { igniteCluster.state() } returns ClusterState.ACTIVE
        assertEquals(ClusterState.ACTIVE, igniteClusterInfoService.getClusterState())
    }


    @Test
    fun `Number of Server Nodes is Correct`() {
        val uuid = UUID.randomUUID()
        val uuid2 = UUID.randomUUID()
        every { clusterGroup.nodes() } returns listOf(
            MockClusterNode(isClient = false, isDaemon = false, consistentId = uuid, uuid = uuid),
            MockClusterNode(isClient = false, isDaemon = false, consistentId = uuid2, uuid = uuid2)
        )
        every { clusterGroup.forServers() } returns clusterGroup
        val nodes = igniteClusterInfoService.getNodes(NodeType.SERVER)
        assertEquals(2, nodes.size)
        assertEquals(uuid, nodes[0].id())
        assertEquals(false, nodes[0].isClient)
    }

    @Test
    fun `Number of Client Nodes is Correct`() {
        val uuid = UUID.randomUUID()
        val uuid2 = UUID.randomUUID()
        every { clusterGroup.nodes() } returns listOf(
            MockClusterNode(isClient = true, isDaemon = false, consistentId = uuid, uuid = uuid),
            MockClusterNode(isClient = true, isDaemon = false, consistentId = uuid2, uuid = uuid2)
        )
        every { clusterGroup.forServers() } returns clusterGroup
        val nodes = igniteClusterInfoService.getNodes(NodeType.SERVER)
        assertEquals(2, nodes.size)
        assertEquals(uuid, nodes[0].id())
        assertEquals(true, nodes[0].isClient)
    }

    @Test
    fun `Number of Daemon Nodes is Correct`() {
        val uuid = UUID.randomUUID()
        val uuid2 = UUID.randomUUID()
        every { clusterGroup.nodes() } returns listOf(
            MockClusterNode(isClient = false, isDaemon = true, consistentId = uuid, uuid = uuid),
            MockClusterNode(isClient = false, isDaemon = true, consistentId = uuid2, uuid = uuid2)
        )
        every { clusterGroup.forServers() } returns clusterGroup
        val nodes = igniteClusterInfoService.getNodes(NodeType.DAEMON)
        assertEquals(2, nodes.size)
        assertEquals(uuid, nodes[0].id())
        assertEquals(true, nodes[0].isDaemon)
    }

    @Test
    fun `Number of Nodes is Correct`() {
        val uuid = UUID.randomUUID()
        val uuid2 = UUID.randomUUID()
        every { igniteCluster.nodes() } returns listOf(
            MockClusterNode(isClient = true, isDaemon = false, consistentId = uuid, uuid = uuid),
            MockClusterNode(isClient = false, isDaemon = false, consistentId = uuid2, uuid = uuid2)
        )

        val nodes = igniteClusterInfoService.getNodes(NodeType.ALL)
        assertEquals(2, nodes.size)
        assertEquals(uuid, nodes[0].id())
    }

    @Test
    fun `Number of Baseline Nodes is Correct`() {
        val uuid = UUID.randomUUID()
        val uuid2 = UUID.randomUUID()
        every { igniteCluster.currentBaselineTopology() } returns listOf(
            MockBaselineNode(consistentId = uuid),
            MockBaselineNode(consistentId = uuid2)
        )
        val nodes = igniteClusterInfoService.getBaselineNodes()
        assertEquals(2, nodes.size)
        assertEquals(uuid, nodes[0].consistentId())
    }

    @Test
    fun `Check if all baseline nodes are up`() {
        val uuid = UUID.randomUUID()
        val uuid2 = UUID.randomUUID()
        every { igniteCluster.currentBaselineTopology() } returns listOf(
            MockBaselineNode(consistentId = uuid),
            MockBaselineNode(consistentId = uuid2)
        )

        every { clusterGroup.nodes() } returns listOf(
            MockClusterNode(isClient = false, isDaemon = false, consistentId = uuid, uuid = uuid),
            MockClusterNode(isClient = false, isDaemon = false, consistentId = uuid2, uuid = uuid2)
        )

        assertEquals(true, igniteClusterInfoService.areAllBaseLineNodesUp())
    }

    @Test
    fun `Check if all baseline nodes are down`() {
        val uuid = UUID.randomUUID()
        val uuid2 = UUID.randomUUID()
        every { igniteCluster.currentBaselineTopology() } returns listOf(
            MockBaselineNode(consistentId = uuid),
            MockBaselineNode(consistentId = uuid2)
        )

        every { clusterGroup.nodes() } returns listOf(
            MockClusterNode(isClient = true, isDaemon = false, consistentId = UUID.randomUUID(), uuid = UUID.randomUUID())
        )

        assertEquals(false, igniteClusterInfoService.areAllBaseLineNodesUp())
    }

    @Test
    fun `Check number of baseline nodes that are up and down`() {
        val uuid = UUID.randomUUID()
        val uuid2 = UUID.randomUUID()
        every { igniteCluster.currentBaselineTopology() } returns listOf(
            MockBaselineNode(consistentId = uuid),
            MockBaselineNode(consistentId = uuid2)
        )

        every { clusterGroup.nodes() } returns listOf(
            MockClusterNode(isClient = false, isDaemon = false, consistentId = uuid, uuid = uuid)
        )

        val upBaselineNodes = igniteClusterInfoService.getUpBaselineNodes()
        val downBaselineNodes = igniteClusterInfoService.getDownBaselineNodes()
        assertEquals(1, upBaselineNodes.size)
        assertEquals(1, downBaselineNodes.size)
        assertEquals(uuid, upBaselineNodes[0].consistentId())
        assertEquals(uuid2, downBaselineNodes[0].consistentId())
    }
}