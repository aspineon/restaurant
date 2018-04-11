package com.nibado.restaurant.service.sleuthtest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executor;

@Service
@Slf4j
public class TestService {
    private final TestRepo testRepo;
    private final Executor executor;

    public TestService(TestRepo testRepo, Executor executor) {
        this.testRepo = testRepo;
        this.executor = executor;
    }

    public void doWork() {
        log.info("Service work");
        testRepo.doWork();

        executor.execute(this::executorWork);
    }

    public void executorWork() {
        log.info("Executor service work");
    }

    @Async
    public void asyncWork() {
        log.info("Start Async Method");
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
        log.info("End Async Method");
    }
}
