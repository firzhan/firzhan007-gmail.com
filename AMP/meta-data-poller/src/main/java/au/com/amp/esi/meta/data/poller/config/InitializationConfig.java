package au.com.amp.esi.meta.data.poller.config;

import au.com.amp.esi.meta.data.poller.controller.MetaDataPollingController;
import ch.qos.logback.classic.Logger;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;

import javax.annotation.PostConstruct;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Configuration("InitializationConfig")
public class InitializationConfig {

    private static ch.qos.logback.classic.Logger logger =
            (Logger) LoggerFactory.getLogger(InitializationConfig.class);


    @Value("${accc.metadata-cache.hostname}")
    private String acccHostname;

    @Value(value = "${kafka.topic-name}")
    private String topicName;

    @Value(value = "${kafka.partitionCount}")
    private int partitionCount;

    @Value(value = "${kafka.replicationFactor}")
    private short replicationFactor;

    @Value("${accc.metadata-cache.x-v}")
    private String metadataCacheVersion;

    @Value("${server.ssl.trust-store.location}")
    private Resource trustStore;

    @Value("${server.ssl.trust-store.password}")
    private String trustStorePassword;
    //
    @Value("${server.ssl.key-store.location}")
    private Resource keystore;

    @Value("${server.ssl.key-store.password}")
    private String keyStorePassword;

    @Value("${accc.metadata-cache.status-check-polling-interval-ms}")
    private long statusCheckPollingIntervalMS;

    @Value("${accc.metadata-cache.recipient-check-polling-interval-ms}")
    private long recipientCheckPollingIntervalMS;

    @Value("${amp.proxy.port}")
    private int proxyPort;

    @Value("${amp.proxy.host}")
    private String proxyHost;

    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    @Value("${kafka.ssl-enable}")
    private boolean kafkaSSLEnable;

    @Autowired
    private Environment environment;


    /*@Bean
    public NewTopic topic1() {
        return new NewTopic(dataRecipientTopicName, partitionCount, replicationFactor);
    }*/

    @SneakyThrows
    @PostConstruct
    public void init(){
        //set to TLSv1.1 or TLSv1.2
        System.setProperty("https.protocols", "TLSv1.2");

        System.setProperty("javax.net.ssl.trustStore", trustStore.getFile().getAbsolutePath());
        System.setProperty("javax.net.ssl.trustStorePassword",trustStorePassword);
        System.setProperty("jasypt.encryptor.password","some-random-data");
    }
}
