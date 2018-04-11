package com.nibado.restaurant.service.sleuthtest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

@RestController
@Slf4j
public class TestController {
    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @RequestMapping("/")
    public String home() {
        log.info("Controller work");
        testService.doWork();
        testService.asyncWork();
        return "Hello World";
    }

    @RequestMapping("/other")
    public String other() {
        log.info("Other controller work");
        return "Other";
    }

    @RequestMapping("/callable")
    public Callable<String> callable() {
        log.info("Callable controller work");
        return () -> {
            log.info("Inside callable");
            return "Other";
        };
    }
}
