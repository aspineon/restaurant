package com.nibado.restaurant.lib.cassandra;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class CassandraConfiguration {
    @Value("${cassandra.port:9042}")
    private int port;
    @Value("${cassandra.contactPoint:localhost}")
    private String contactPoint;

    @Value("${cassandra.keyspace}")
    private String keyspace;

    @Bean
    public Cluster cluster() {
        Cluster.Builder builder = Cluster.builder()
                .withClusterName("cluster")
                .withPort(port)
                .addContactPoint("localhost");

        return builder.build();
    }

    @Bean
    public Session session(Cluster cluster) {
        log.info("Cassandra on {}:{} with keyspace: {}", contactPoint, port, keyspace);

        return cluster.connect(keyspace);
    }
}
