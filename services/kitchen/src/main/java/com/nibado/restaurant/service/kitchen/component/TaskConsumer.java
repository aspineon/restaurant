package com.nibado.restaurant.service.kitchen.component;

import com.nibado.restaurant.service.kitchen.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TaskConsumer {
    private final TaskService taskService;

    @Autowired
    private KafkaTemplate<Integer, String> template;

    public TaskConsumer(TaskService taskService) {
        this.taskService = taskService;
    }

    @KafkaListener(topicPattern = "${kafka.topics.input:kitchen_in}")
    public void task(final ConsumerRecord<Integer, String> record) {
        log.debug("Got record: {}", record);

        taskService.submit(record.value());
    }

    @Scheduled(fixedRate = 2000)
    public void test() throws Exception {
        log.debug("Sending...");
        template.send("kitchen_in", 1, "fries").get();
    }
}
