package au.com.amp.esi.meta.data.processor.utils;

import au.com.amp.esi.meta.data.processor.config.PropertiesConfig;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

public class Utils {

    public static JedisConnectionFactory jedisConnectionFactory(PropertiesConfig propertiesConfig, String host,
                                                                int port) {

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
                new RedisStandaloneConfiguration(host, port);

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
    }
}
