package pl.mmalkiew.kafka.consumer.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import pl.mmalkiew.kafka.consumer.model.Sample;

@Service
public class SampleConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(SampleConsumer.class);

    @KafkaListener(topics = "sample_kafka_topic", groupId = "sample_id")
    public void consume(String message) {
        LOGGER.info("Consumed sample message: {}", message);
    }


    @KafkaListener(topics = "sample_json_kafka_topic", groupId = "sample_json")
    public void consumeJson(Sample sample) {
        LOGGER.info("Consumed sample json message: {}", sample);
    }
}
