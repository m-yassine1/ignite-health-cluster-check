package com.ignite.cluster.health.igniteclusterhealth.mock.models

import org.apache.ignite.cluster.BaselineNode

class MockBaselineNode(
    private val consistentId: Any
): BaselineNode {
    override fun consistentId(): Any {
        return consistentId
    }

    override fun <T : Any?> attribute(name: String?): T? {
        TODO("Not yet implemented")
    }

    override fun attributes(): MutableMap<String, Any> {
        TODO("Not yet implemented")
    }
}