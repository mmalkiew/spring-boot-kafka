package pl.mmalkiew.kafka.producer.rest;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mmalkiew.kafka.producer.model.Sample;

import java.time.LocalDateTime;

@RestController
@RequestMapping("sample")
public class SampleController {

    private static final String TOPIC = "sample_kafka_topic";
    private static final String TOPIC_JSON = "sample_json_kafka_topic";

    private final KafkaTemplate<String, Sample> kafkaTemplate;

    public SampleController(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @GetMapping("/publish/{message}")
    public String publishSampleMessage(@PathVariable("message") final String message) {

        kafkaTemplate.send(TOPIC, Sample.create(message));

        return "Published successfully: " + LocalDateTime.now()
                                                         .toString();
    }

    @GetMapping("/publish/json/{message}")
    public String publishSampleJsonMessage(@PathVariable("message") final String message) {

        kafkaTemplate.send(TOPIC_JSON, Sample.create(message));

        return "Published successfully: " + LocalDateTime.now()
                                                         .toString();
    }
}
