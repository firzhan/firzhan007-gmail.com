package au.com.amp.esi.meta.data.processor.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@NoArgsConstructor
@Configuration("properties-beans")
public class PropertiesConfig {

    @Value( "${redis.primary-endpoint}" )
    private String primaryEndpoint;

    @Value( "${redis.primary-endpoint-port}" )
    private int primaryEndpointPort;

    @Value( "${redis.reader-endpoint}" )
    private String readerEndpoint;

    @Value( "${redis.reader-endpoint-port}" )
    private int readerEndpointPort;

    @Value( "${redis.connection-timeout-ms}" )
    private int connectionTimeout;

    @Value( "${redis.allowed-max-conn-count}" )
    private int allowedMaxConnCount;

    @Value( "${redis.idle-max-conn-count}" )
    private int idleMaxConnCount;

    @Value( "${redis.idle-min-conn-count}" )
    private int idleMinConnCount;

    @Value( "${redis.test-on-borrow}" )
    private boolean isTestOnBorrow;

    @Value( "${redis.test-on-return}" )
    private boolean isTestOnReturn;

    @Value( "${redis.test-while-idle}" )
    private boolean isTestWhileIdle;

    @Value( "${redis.eviction-test-run-count}" )
    private int evicationTestRunCount;

    @Value( "${redis.eviction-run-interval-ms}" )
    private int evicationRunInterval;

    @Value( "${redis.password}" )
    private String password;

    @Value( "${redis.ssl}" )
    private boolean isSSLEnabled;

    @Value( "${redis.cache-expiry-seconds}" )
    private int cacheExpirySeconds;

}
