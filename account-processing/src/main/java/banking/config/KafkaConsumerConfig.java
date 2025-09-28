package banking.config;

import banking.model.dto.CardCreateRequest;
import banking.model.dto.ClientProductResponse;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    private Map<String, Object> baseConsumerConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "banking-service"); // общий group.id
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return props;
    }

    @Bean
    public ConsumerFactory<String, CardCreateRequest> cardConsumerFactory() {
        JsonDeserializer<CardCreateRequest> deserializer =
                new JsonDeserializer<>(CardCreateRequest.class, false);
        deserializer.addTrustedPackages("banking.model.dto");

        return new DefaultKafkaConsumerFactory<>(
                baseConsumerConfig(), new StringDeserializer(), deserializer);
    }

    @Bean
    public ConsumerFactory<String, ClientProductResponse> clientConsumerFactory() {
        JsonDeserializer<ClientProductResponse> deserializer =
                new JsonDeserializer<>(ClientProductResponse.class, false);
        deserializer.addTrustedPackages("banking.model.dto");

        return new DefaultKafkaConsumerFactory<>(
                baseConsumerConfig(), new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, CardCreateRequest> cardKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, CardCreateRequest> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(cardConsumerFactory());
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ClientProductResponse> clientKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ClientProductResponse> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(clientConsumerFactory());
        return factory;
    }
}