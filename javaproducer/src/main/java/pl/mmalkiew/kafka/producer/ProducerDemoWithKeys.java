package pl.mmalkiew.kafka.producer;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class ProducerDemoWithKeys {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerDemoWithCallback.class);

    private static final String TOPIC = "first_topic";
    private static final String VALUE = "hello world %s";
    private static final String KEY = "id_%s";

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        String bootstrapServers = "127.0.0.1:9092";

        //  create Producer properties
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        //  create producer
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);


        for (int i=0; i<10; i++) {
            // create producer record
            String tmpValue = String.format(VALUE, Integer.toString(i));
            String tmpKey = String.format(KEY, Integer.toString(i));
            ProducerRecord<String, String> record = new ProducerRecord<String, String>(TOPIC, tmpKey, tmpValue);

            LOGGER.info("Key {}", tmpKey);
            //send data
            producer.send(record, new Callback() {
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if (e == null) {
                        LOGGER.info("record is successfully sent .... Record MetaData {}" , recordMetadata);
                    } else {
                        LOGGER.error("record is not sent successfully. Error msg: {}", e.getMessage());
                    }
                }
            }).get();   //block the send() to make it synchronous -dont do this in production!;
        }



        producer.flush();

        producer.close();
    }
}
