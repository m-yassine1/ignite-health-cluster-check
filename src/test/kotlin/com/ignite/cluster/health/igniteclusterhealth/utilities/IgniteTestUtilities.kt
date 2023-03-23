package com.ignite.cluster.health.igniteclusterhealth.utilities

import org.apache.ignite.Ignite
import org.apache.ignite.Ignition
import org.apache.ignite.ShutdownPolicy
import org.apache.ignite.cluster.ClusterState
import org.apache.ignite.configuration.DataStorageConfiguration
import org.apache.ignite.configuration.IgniteConfiguration
import org.apache.ignite.spi.communication.tcp.TcpCommunicationSpi
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi
import org.apache.ignite.spi.discovery.tcp.ipfinder.multicast.TcpDiscoveryMulticastIpFinder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*

class IgniteTestUtilities

private val log: Logger = LoggerFactory.getLogger(IgniteTestUtilities::class.java)

fun stopIgniteNodes() {
    try {
        Ignition.stopAll(true)
    } catch (e: Exception) {
        log.error("Error while stopping nodes", e)
    }
}

fun stopIgniteNode(name: String) {
    try {
        Ignition.stop(name,true)
    } catch (e: Exception) {
        log.error("Error while stopping node $name", e)
    }
}

fun getIgniteNodes(instancePrefixName: String, numberOfNodes: Int, isClientMode: Boolean, hasDataConfiguration: Boolean): List<Ignite> {
    return IntRange(0, numberOfNodes).map {
        getIgniteNode(instancePrefixName + UUID.randomUUID(), isClientMode, hasDataConfiguration)
    }
}

fun getIgniteNode(name: String, isClientMode: Boolean, hasDataConfiguration: Boolean): Ignite {
    return Ignition.start(getIgniteConfiguration(name, isClientMode, hasDataConfiguration))
}

private fun getIgniteConfiguration(instanceName: String, isClientMode: Boolean, hasDataConfiguration: Boolean): IgniteConfiguration {
    val storageConfiguration = DataStorageConfiguration()
    storageConfiguration.defaultDataRegionConfiguration.isPersistenceEnabled = true
    return IgniteConfiguration()
        .setIgniteInstanceName(instanceName)
        .setShutdownPolicy(ShutdownPolicy.IMMEDIATE)
        .setClientMode(isClientMode)
        .setClusterStateOnStart(ClusterState.ACTIVE)
        .setDataStorageConfiguration(if(hasDataConfiguration) storageConfiguration else null)
        .setDiscoverySpi(
            TcpDiscoverySpi()
                .setIpFinder(TcpDiscoveryMulticastIpFinder()
                        .setAddresses(listOf("127.0.0.1:47500..57509"))
                ).setLocalPort(47500)
        ).setCommunicationSpi(TcpCommunicationSpi()
            .setLocalPort(4321)
        )
}