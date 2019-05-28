package pl.mmalkiew.kafka.producer;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerDemoWithCallback {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerDemoWithCallback.class);

    public static void main(String[] args) {

        String bootstrapServers = "127.0.0.1:9092";

        //  create Producer properties
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        //  create producer
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);


        // create producer record
        ProducerRecord<String, String> record = new ProducerRecord<String, String>("first_topic", "hello_world with callback");
        //send data
        producer.send(record, new Callback() {
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                if (e == null) {
                    LOGGER.info("record is successfully sent .... Record MetaData {}" , recordMetadata);
                } else {
                    LOGGER.error("record is not sent successfully. Error msg: {}", e.getMessage());
                }
            }
        });

        producer.flush();

        producer.close();
    }
}
