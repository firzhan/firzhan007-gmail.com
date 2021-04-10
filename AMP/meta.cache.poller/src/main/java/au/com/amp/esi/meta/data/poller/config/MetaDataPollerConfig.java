package au.com.amp.esi.meta.data.poller.config;

import au.com.amp.esi.meta.data.poller.utils.MetaCachePollerConstant;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;


import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Configuration
public class MetaDataPollerConfig {

    @Value( "${accc.metadata-cache.hostname}" )
    private String acccHostname;

    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    @Value(value = "${kafka.topic-name}")
    private String topicName;

    @Value(value = "${kafka.partitionCount}")
    private int partitionCount;

    @Value(value = "${kafka.replicationFactor}")
    private short replicationFactor;

    @Value( "${accc.metadata-cache.x-v}" )
    private String metadataCacheVersion;

    @Value("${trust.store}")
    private Resource trustStore;

    @Value("${trust.store-password}")
    private String trustStorePassword;

    @Value("${kafka.ssl-enable}")
    private boolean kafkaSSLEnable;

    @Value("${server.ssl.key-store}")
    private Resource keystore;

    @Value("${server.ssl.key-store-password}")
    private String keyStorePassword;

    @Value("${accc.metadata-cache.status-check-polling-interval-ms}")
    private long statusCheckPollingIntervalMS;

    @Value("${accc.metadata-cache.recipient-check-polling-interval-ms}")
    private long recipientCheckPollingIntervalMS;



    /*@Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
*/

    @Bean
    HttpEntity httpEntity(){
        HttpHeaders headers = new HttpHeaders();
        headers.set(MetaCachePollerConstant.X_VERSION_HEADER_NAME, metadataCacheVersion);
        headers.set(MetaCachePollerConstant.ACCEPT_HEADER_NAME, MediaType.APPLICATION_JSON_VALUE);
        return new HttpEntity<>(headers);
    }

    @Bean
    RestTemplate restTemplate() throws IOException, CertificateException,
            NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        SSLContext sslContext = new SSLContextBuilder()
                .loadTrustMaterial(trustStore.getURL(), trustStorePassword.toCharArray())
                .build();
        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext);
        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(socketFactory)
                .build();
        HttpComponentsClientHttpRequestFactory factory =
                new HttpComponentsClientHttpRequestFactory(httpClient);
        return new RestTemplate(factory);
    }

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    /*@Bean
    public NewTopic topic1() {
        return new NewTopic(dataRecipientTopicName, partitionCount, replicationFactor);
    }*/

    @Bean
    public ProducerFactory<String, String> producerFactory() throws IOException {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapAddress);
        configProps.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        configProps.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        if(kafkaSSLEnable){
            configProps.put("security.protocol", "SSL");
            configProps.put("ssl.truststore.location", trustStore.getFile().getAbsolutePath());
            configProps.put("ssl.truststore.password", trustStorePassword);

            configProps.put("ssl.key.password", keyStorePassword);
            configProps.put("ssl.keystore.password", keyStorePassword);
            configProps.put("ssl.keystore.location", keystore.getFile().getAbsolutePath());
        }
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() throws IOException {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
