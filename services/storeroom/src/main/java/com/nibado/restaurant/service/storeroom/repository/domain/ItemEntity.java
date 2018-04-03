package com.nibado.restaurant.service.storeroom.repository.domain;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import static com.nibado.restaurant.service.storeroom.repository.domain.ItemEntity.TABLE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = TABLE)
public class ItemEntity {
    public static final String TABLE = "items";
    public static final String C_NAME = "name";
    public static final String C_QTTY = "qtty";
    @PartitionKey
    @Column(name = C_NAME)
    private String name;

    @Column(name = C_QTTY)
    private long qtty;
}
