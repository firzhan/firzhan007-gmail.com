package au.com.amp.esi.meta.data.processor.config;

import au.com.amp.esi.meta.data.processor.model.event.SoftwareProductEvent;
import au.com.amp.esi.meta.data.processor.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

@Configuration
public class RedisConfig {

    @Autowired
    private PropertiesConfig propertiesConfig;


   /* private JedisConnectionFactory jedisConnectionFactory() {

        JedisPoolConfig poolConfig = new JedisPoolConfig();

        poolConfig.setMaxWaitMillis(propertiesConfig.getConnectionTimeout());
        poolConfig.setMaxTotal(propertiesConfig.getAllowedMaxConnCount());
        poolConfig.setMaxIdle(propertiesConfig.getIdleMaxConnCount());
        poolConfig.setMinIdle(propertiesConfig.getIdleMinConnCount());
        poolConfig.setTestOnBorrow(propertiesConfig.isTestOnBorrow());
        poolConfig.setTestOnReturn(propertiesConfig.isTestOnReturn());
        poolConfig.setTestWhileIdle(propertiesConfig.isTestWhileIdle());
        poolConfig.setNumTestsPerEvictionRun(propertiesConfig.getEvicationTestRunCount());
        poolConfig.setTimeBetweenEvictionRunsMillis(propertiesConfig.getEvicationRunInterval());

        RedisStandaloneConfiguration redisStandaloneConfiguration =
                new RedisStandaloneConfiguration(propertiesConfig.getPrimaryEndpoint(), propertiesConfig.getPrimaryEndpointPort());

        if (propertiesConfig.getPassword() != null && !propertiesConfig.getPassword().isEmpty()) {
            redisStandaloneConfiguration.setPassword(RedisPassword.of(propertiesConfig.getPassword()));
        }

        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jedisPoolingClientConfigurationBuilder =
                JedisClientConfiguration.builder().connectTimeout(Duration.ofMillis(propertiesConfig.getConnectionTimeout())).usePooling().
                poolConfig(poolConfig);

        JedisClientConfiguration jedisClientConfiguration;
        if(propertiesConfig.isSSLEnabled()){
            jedisClientConfiguration = jedisPoolingClientConfigurationBuilder.and().useSsl().build();
        } else {
            jedisClientConfiguration = jedisPoolingClientConfigurationBuilder.build();
        }
        return new JedisConnectionFactory(redisStandaloneConfiguration,
                jedisClientConfiguration);
    }*/

    @Bean("RedisPrimaryTemplate")
    @DependsOn("properties-beans")
    public RedisTemplate<String, Object> redisPrimaryTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(Utils.jedisConnectionFactory(propertiesConfig,
                propertiesConfig.getPrimaryEndpoint(), propertiesConfig.getPrimaryEndpointPort()));
        return template;
    }

    @Bean("RedisReaderTemplate")
    @DependsOn("properties-beans")
    public RedisTemplate<String, Object> redisReaderTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(Utils.jedisConnectionFactory(propertiesConfig,
                propertiesConfig.getReaderEndpoint(), propertiesConfig.getReaderEndpointPort()));

        return redisTemplate;
    }
}
