package com.ignite.cluster.health.igniteclusterhealth.mock.models

import org.apache.ignite.cluster.ClusterMetrics
import org.apache.ignite.cluster.ClusterNode
import org.apache.ignite.lang.IgniteProductVersion
import java.util.*

class MockClusterNode(
    private val isClient: Boolean,
    private val isDaemon: Boolean,
    private val consistentId: Any,
    private val uuid: UUID
): ClusterNode{

    override fun consistentId(): Any {
        return consistentId
    }

    override fun <T : Any?> attribute(name: String?): T? {
        TODO("Not yet implemented")
    }

    override fun attributes(): MutableMap<String, Any> {
        TODO("Not yet implemented")
    }

    override fun id(): UUID {
        return uuid
    }

    override fun metrics(): ClusterMetrics {
        TODO("Not yet implemented")
    }

    override fun addresses(): MutableCollection<String> {
        TODO("Not yet implemented")
    }

    override fun hostNames(): MutableCollection<String> {
        TODO("Not yet implemented")
    }

    override fun order(): Long {
        TODO("Not yet implemented")
    }

    override fun version(): IgniteProductVersion {
        TODO("Not yet implemented")
    }

    override fun isLocal(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isDaemon(): Boolean {
        return isDaemon
    }

    override fun isClient(): Boolean {
        return isClient
    }
}