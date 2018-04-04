package com.nibado.restaurant.service.kitchen.integration;

import com.nibado.restaurant.service.kitchen.Application;
import com.nibado.restaurant.service.kitchen.component.TaskConsumer;
import com.nibado.restaurant.service.kitchen.configuration.TestConfiguration;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;
import java.util.Properties;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = {Application.class, TestConfiguration.class})
@EnableConfigurationProperties
public class KitchenIntegrationTest {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    private static final String TOPIC = "kitchen_in";

    @ClassRule
    public static KafkaEmbedded embeddedKafka = new KafkaEmbedded(1, true, TOPIC);

    @Autowired
    private TaskConsumer taskConsumer;
    private KafkaProducer<String, String> producer;

    @Autowired
    private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

    @Before
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .build();

        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("integrationTest", "true",
                embeddedKafka);

        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, embeddedKafka.getBrokersAsString());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producer = new KafkaProducer<>(props);

        Set<String> containerIds = kafkaListenerEndpointRegistry.getListenerContainerIds();
        ContainerTestUtils.waitForAssignment(
                kafkaListenerEndpointRegistry.getListenerContainer(containerIds.iterator().next()),
                embeddedKafka.getPartitionsPerTopic());
    }

    @Test
    public void submitTask() throws Exception {
        producer.send(new ProducerRecord<>(TOPIC, "foo")).get();

        Thread.sleep(2000);

    }

    @Test
    public void recipes() throws Exception {
        mockMvc.perform(get("/recipe"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recipes[0].name", is("fries")));
    }
}
