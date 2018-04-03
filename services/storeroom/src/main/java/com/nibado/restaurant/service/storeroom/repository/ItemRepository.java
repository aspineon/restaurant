package com.nibado.restaurant.service.storeroom.repository;

import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.Select;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.nibado.restaurant.service.storeroom.repository.domain.ItemEntity;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;

import static com.datastax.driver.core.querybuilder.QueryBuilder.*;
import static com.nibado.restaurant.service.storeroom.repository.domain.ItemEntity.*;

@Repository
public class ItemRepository {
    private final Session session;
    private final Mapper<ItemEntity> mapper;

    public ItemRepository(final Session session) {
        this.session = session;
        this.mapper = new MappingManager(session).mapper(ItemEntity.class);
    }

    public List<ItemEntity> getAll() {
        Select select = select().all().from(TABLE);

        return mapper.map(session.execute(select)).all();
    }

    public void increment(final String item, final int amount) {
        Statement statement = update(TABLE).with(incr(C_QTTY, amount)).where(eq(C_NAME, item));

        session.execute(statement);
    }

    @PostConstruct
    public void initSchema() {
        session.execute("create table if not exists items (name text, qtty counter, primary KEY(name))");

        increment("potatoes", 2);
        this.getAll().forEach(System.out::println);
    }
}
