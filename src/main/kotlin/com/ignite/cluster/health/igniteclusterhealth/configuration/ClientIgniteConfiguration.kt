package com.ignite.cluster.health.igniteclusterhealth.configuration

import org.apache.ignite.Ignite
import org.apache.ignite.Ignition
import org.apache.ignite.ShutdownPolicy
import org.apache.ignite.cluster.ClusterState
import org.apache.ignite.configuration.IgniteConfiguration
import org.apache.ignite.spi.communication.tcp.TcpCommunicationSpi
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi
import org.apache.ignite.spi.discovery.tcp.ipfinder.multicast.TcpDiscoveryMulticastIpFinder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ClientIgniteConfiguration {
    @Bean
    fun igniteClientConfiguration(): IgniteConfiguration {
        return IgniteConfiguration()
            .setIgniteInstanceName("my-client")
            .setShutdownPolicy(ShutdownPolicy.IMMEDIATE)
            .setClientMode(true)
            .setClusterStateOnStart(ClusterState.ACTIVE)
            .setDiscoverySpi(
                TcpDiscoverySpi()
                    .setIpFinder(
                        TcpDiscoveryMulticastIpFinder()
                        .setAddresses(listOf("127.0.0.1:47500..57509"))
                    ).setLocalPort(47500)
            ).setCommunicationSpi(
                TcpCommunicationSpi()
                .setLocalPort(4321)
            )
    }

    @Bean(destroyMethod = "close")
    fun igniteClient(igniteClientConfiguration: IgniteConfiguration): Ignite {
        return Ignition.start(igniteClientConfiguration)
    }
}