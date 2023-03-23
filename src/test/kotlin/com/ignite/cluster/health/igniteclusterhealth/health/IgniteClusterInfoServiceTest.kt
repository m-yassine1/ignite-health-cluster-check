package com.ignite.cluster.health.igniteclusterhealth.health

import com.ignite.cluster.health.igniteclusterhealth.service.IgniteClusterInfoService
import com.ignite.cluster.health.igniteclusterhealth.service.NodeType
import com.ignite.cluster.health.igniteclusterhealth.utilities.getIgniteNodes
import com.ignite.cluster.health.igniteclusterhealth.utilities.stopIgniteNode
import com.ignite.cluster.health.igniteclusterhealth.utilities.stopIgniteNodes
import org.apache.ignite.Ignite
import org.apache.ignite.cluster.ClusterState
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class IgniteClusterInfoServiceTest(
    private val mockMvc: MockMvc
) {
    @Test
    @Order(1)
    fun `Validate Cluster is Up`() {
        mockMvc.perform(get("/actuator/health"))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.status").value("UP"))
            .andExpect(jsonPath("$.components").exists())
            .andExpect(jsonPath("$.components.igniteNode").exists())
            .andExpect(jsonPath("$.components.igniteNode.status").value("UP"))
            .andExpect(jsonPath("$.components.igniteNode.details").exists())
            .andExpect(jsonPath("$.components.igniteNode.details.clusterId").value(serverNodes[0].cluster().id().toString()))
            .andExpect(jsonPath("$.components.igniteNode.details.clusterState").value(ClusterState.ACTIVE.toString()))
            .andExpect(jsonPath("$.components.igniteNode.details.numberOfActiveServerNodes").value(2))
            .andExpect(jsonPath("$.components.igniteNode.details.numberOfActiveDaemonNodes").value(0))
            .andExpect(jsonPath("$.components.igniteNode.details.numberOfActiveClientNodes").value(1))
            .andExpect(jsonPath("$.components.igniteNode.details.numberOfActiveNodes").value(3))
            .andExpect(jsonPath("$.components.igniteNode.details.numberOfUpBaseLineNodes").value(2))
            .andExpect(jsonPath("$.components.igniteNode.details.numberOfDownBaseLineNodes").value(0))
            .andExpect(jsonPath("$.components.igniteNode.details.numberOfBaseLineNodes").value(2))
    }

    @Test
    @Order(2)
    fun `Validate Cluster is down`() {
        stopIgniteNode(serverNodes[1].name())
        Thread.sleep(4000)
        mockMvc.perform(get("/actuator/health"))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.status").value("DOWN"))
            .andExpect(jsonPath("$.components").exists())
            .andExpect(jsonPath("$.components.igniteNode").exists())
            .andExpect(jsonPath("$.components.igniteNode.status").value("DOWN"))
            .andExpect(jsonPath("$.components.igniteNode.details").exists())
            .andExpect(jsonPath("$.components.igniteNode.details.clusterId").value(serverNodes[0].cluster().id().toString()))
            .andExpect(jsonPath("$.components.igniteNode.details.clusterState").value(ClusterState.ACTIVE.toString()))
            .andExpect(jsonPath("$.components.igniteNode.details.numberOfActiveServerNodes").value(1))
            .andExpect(jsonPath("$.components.igniteNode.details.numberOfActiveDaemonNodes").value(0))
            .andExpect(jsonPath("$.components.igniteNode.details.numberOfActiveClientNodes").value(1))
            .andExpect(jsonPath("$.components.igniteNode.details.numberOfActiveNodes").value(2))
            .andExpect(jsonPath("$.components.igniteNode.details.numberOfUpBaseLineNodes").value(1))
            .andExpect(jsonPath("$.components.igniteNode.details.numberOfDownBaseLineNodes").value(1))
            .andExpect(jsonPath("$.components.igniteNode.details.numberOfBaseLineNodes").value(2))
    }

    @Test
    @Order(3)
    fun `Validate Cluster is down with none baseline node added`() {
        getIgniteNodes("ignite-cluster-info-health-server-",1, isClientMode = false, hasDataConfiguration = false)
        Thread.sleep(4000)
        mockMvc.perform(get("/actuator/health"))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.status").value("DOWN"))
            .andExpect(jsonPath("$.components").exists())
            .andExpect(jsonPath("$.components.igniteNode").exists())
            .andExpect(jsonPath("$.components.igniteNode.status").value("DOWN"))
            .andExpect(jsonPath("$.components.igniteNode.details").exists())
            .andExpect(jsonPath("$.components.igniteNode.details.clusterId").value(serverNodes[0].cluster().id().toString()))
            .andExpect(jsonPath("$.components.igniteNode.details.clusterState").value(ClusterState.ACTIVE.toString()))
            .andExpect(jsonPath("$.components.igniteNode.details.numberOfActiveServerNodes").value(2))
            .andExpect(jsonPath("$.components.igniteNode.details.numberOfActiveDaemonNodes").value(0))
            .andExpect(jsonPath("$.components.igniteNode.details.numberOfActiveClientNodes").value(2))
            .andExpect(jsonPath("$.components.igniteNode.details.numberOfActiveNodes").value(4))
            .andExpect(jsonPath("$.components.igniteNode.details.numberOfUpBaseLineNodes").value(1))
            .andExpect(jsonPath("$.components.igniteNode.details.numberOfDownBaseLineNodes").value(1))
            .andExpect(jsonPath("$.components.igniteNode.details.numberOfBaseLineNodes").value(2))
    }

    @Test
    @Order(4)
    fun `Validate Cluster is back up with baseline node added`() {
        getIgniteNodes("ignite-cluster-info-health-server-",1, isClientMode = false, hasDataConfiguration = true)
        Thread.sleep(4000)
        mockMvc.perform(get("/actuator/health"))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.status").value("UP"))
            .andExpect(jsonPath("$.components").exists())
            .andExpect(jsonPath("$.components.igniteNode").exists())
            .andExpect(jsonPath("$.components.igniteNode.status").value("UP"))
            .andExpect(jsonPath("$.components.igniteNode.details").exists())
            .andExpect(jsonPath("$.components.igniteNode.details.clusterId").value(serverNodes[0].cluster().id().toString()))
            .andExpect(jsonPath("$.components.igniteNode.details.clusterState").value(ClusterState.ACTIVE.toString()))
            .andExpect(jsonPath("$.components.igniteNode.details.numberOfActiveServerNodes").value(3))
            .andExpect(jsonPath("$.components.igniteNode.details.numberOfActiveDaemonNodes").value(0))
            .andExpect(jsonPath("$.components.igniteNode.details.numberOfActiveClientNodes").value(2))
            .andExpect(jsonPath("$.components.igniteNode.details.numberOfActiveNodes").value(5))
            .andExpect(jsonPath("$.components.igniteNode.details.numberOfUpBaseLineNodes").value(2))
            .andExpect(jsonPath("$.components.igniteNode.details.numberOfDownBaseLineNodes").value(0))
            .andExpect(jsonPath("$.components.igniteNode.details.numberOfBaseLineNodes").value(2))
    }

    companion object {
        private lateinit var igniteClusterInfoService: IgniteClusterInfoService
        private lateinit var serverNodes: List<Ignite>

        @JvmStatic
        @BeforeAll
        fun beforeAll() {
            stopIgniteNodes()
            serverNodes = getIgniteNodes(
                "ignite-cluster-info-health-server-", 2,
                isClientMode = false,
                hasDataConfiguration = true
            )
        }

        @JvmStatic
        @AfterAll
        fun afterAll() {
            stopIgniteNodes()
            Thread.sleep(4000)
        }
    }
}