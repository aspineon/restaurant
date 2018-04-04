package com.nibado.restaurant.testlib.cassandra;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.CodecRegistry;
import com.datastax.driver.core.Session;
import info.archinnov.achilles.embedded.CassandraEmbeddedServerBuilder;
import info.archinnov.achilles.embedded.CassandraShutDownHook;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@Slf4j
public class EmbeddedCassandraConfiguration {
    @Value("${cassandra.keyspace}")
    private String keyspace;

    @Bean
    @Primary
    public Session session(Cluster cluster) {
        CodecRegistry codecRegistry = cluster.getConfiguration().getCodecRegistry();

        return cluster.connect(keyspace);
    }

    @Bean
    @Primary
    public Cluster cluster() {
        final CassandraEmbeddedServerBuilder builder = CassandraEmbeddedServerBuilder
                .builder()
                .cleanDataFilesAtStartup(true)
                .withKeyspaceName(keyspace)
                .withClusterName("cluster")
                .withScript("schema.cql")
                .withShutdownHook(new CassandraShutDownHook());

        return builder.buildNativeCluster();
    }
}
