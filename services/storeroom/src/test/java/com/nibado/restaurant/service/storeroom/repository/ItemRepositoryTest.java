package com.nibado.restaurant.service.storeroom.repository;

import com.datastax.driver.core.Session;
import com.nibado.restaurant.service.storeroom.repository.domain.ItemEntity;
import com.nibado.restaurant.testlib.cassandra.EmbeddedCassandraConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static com.nibado.restaurant.service.storeroom.repository.domain.ItemEntity.TABLE;
import static com.nibado.restaurant.testlib.cassandra.CassandraHelper.truncate;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = NONE, classes = {EmbeddedCassandraConfiguration.class})
@ActiveProfiles("test")
public class ItemRepositoryTest {

    @Autowired
    protected Session session;

    private ItemRepository repository;

    @Before
    public void setup() {
        repository = new ItemRepository(session);
        truncate(session, TABLE);
    }

    @Test
    public void getAll() {
        assertThat(repository.getAll()).isEmpty();
        repository.increment("potatoes", 100);
        assertThat(repository.getAll()).containsExactly(new ItemEntity("potatoes", 100));
        repository.increment("potatoes", 200);
        assertThat(repository.getAll()).containsExactly(new ItemEntity("potatoes", 300));
        repository.increment("tomatoes", 50);
        assertThat(repository.getAll()).containsExactly(new ItemEntity("potatoes", 300), new ItemEntity("tomatoes", 50));
    }

    @Test
    public void increment() {
        assertThat(repository.getAll()).isEmpty();
        repository.increment("potatoes", 100);
        assertThat(repository.getAll()).containsExactly(new ItemEntity("potatoes", 100));
        repository.increment("potatoes", -100);
        assertThat(repository.getAll()).containsExactly(new ItemEntity("potatoes", 0));
    }
}