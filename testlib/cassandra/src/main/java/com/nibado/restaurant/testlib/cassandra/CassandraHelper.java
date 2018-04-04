package com.nibado.restaurant.testlib.cassandra;

import com.datastax.driver.core.Session;

public class CassandraHelper {
    private static final String KEYSPACE_CREATE_STATEMENT = "CREATE KEYSPACE IF NOT EXISTS %s WITH REPLICATION = { 'class' : 'org.apache.cassandra.locator.SimpleStrategy', 'replication_factor': '1' };";

    public static void createKeySpace(final Session session, final String keyspace) {
        session.execute(String.format(KEYSPACE_CREATE_STATEMENT, keyspace));
    }

    public static void truncate(final Session session, final String... tables) {
        for(String table : tables) {
            session.execute(String.format("TRUNCATE %s;", table));
        }

    }
}
