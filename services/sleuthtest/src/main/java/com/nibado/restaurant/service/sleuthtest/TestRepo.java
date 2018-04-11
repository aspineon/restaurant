package com.nibado.restaurant.service.sleuthtest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class TestRepo {
    public void doWork() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
        log.info("Repo work");
    }
}
