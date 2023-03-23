package com.ignite.cluster.health.igniteclusterhealth.service

import org.apache.ignite.Ignite
import org.apache.ignite.IgniteCluster
import org.apache.ignite.cluster.BaselineNode
import org.apache.ignite.cluster.ClusterGroup
import org.apache.ignite.cluster.ClusterNode
import org.apache.ignite.cluster.ClusterState
import org.springframework.stereotype.Service

@Service
class IgniteClusterInfoService(
    val ignite: Ignite
) {
    /**
     * Gets Current active nodes depending on the type
     * @param type Node type (Server, Client, Daemon, All)
     * @return Returns a list of cluster nodes
     */
    fun getNodes(type: NodeType): List<ClusterNode> {
        return when(type) {
            NodeType.CLIENT -> getNodes(getCluster().forClients())
            NodeType.DAEMON -> getNodes(getCluster().forDaemons())
            NodeType.SERVER -> getNodes(getCluster().forServers())
            NodeType.ALL -> getCluster().nodes().toList()
        }
    }

    /**
     * Get Cluster State
     * @return Cluster State
     */
    fun getClusterState(): ClusterState {
        return getCluster().state()
    }

    /**
     * Get Cluster Id
     * @return Cluster Id
     */
    fun getClusterId(): String {
        return getCluster().id().toString()
    }

    /**
     * Checks if all baseline nodes are up
     * @return true if all the baseline nodes are up
     */
    fun areAllBaseLineNodesUp(): Boolean {
        return getUpBaselineNodes().size == getBaselineNodes().size
    }

    /**
     * Get up cluster baseline nodes
     * @return List of cluster nodes that are baseline nodes and are up
     */
    fun getUpBaselineNodes(): List<ClusterNode> {
        val baseLineNodeConsistentIds = getBaselineNodes().map(BaselineNode::consistentId)
        return getNodes(NodeType.SERVER)
            .filter { baseLineNodeConsistentIds.contains(it.consistentId()) }
    }

    /**
     * Get down cluster baseline nodes
     * @return List of baseline nodes that are down
     */
    fun getDownBaselineNodes(): List<BaselineNode> {
        val serverNodeConsistentIds = getNodes(NodeType.SERVER).map(BaselineNode::consistentId)
        return getBaselineNodes()
            .filter { !serverNodeConsistentIds.contains(it.consistentId()) }
    }

    /**
     * Get cluster baseline nodes
     * @return List of baseline nodes
     */
    fun getBaselineNodes(): List<BaselineNode> {
        return getCluster().currentBaselineTopology()?.toList() ?: listOf()
    }

    /**
     * Get cluster nodes
     * @param clusterGroup the cluster group
     * @return List of nodes
     */
    private fun getNodes(clusterGroup: ClusterGroup): List<ClusterNode> {
        return clusterGroup.nodes().toList()
    }

    /**
     * Get cluster
     * @return cluster
     */
    private fun getCluster(): IgniteCluster {
        return ignite.cluster()
    }
}

enum class NodeType {
    CLIENT,SERVER,DAEMON,ALL
}