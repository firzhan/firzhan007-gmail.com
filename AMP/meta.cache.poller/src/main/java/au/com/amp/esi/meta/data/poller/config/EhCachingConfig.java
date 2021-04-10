package au.com.amp.esi.meta.cache.poller.config;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
@EnableCaching
public class EhCachingConfig extends CachingConfigurerSupport {

    @Bean
    public CacheManager ehCacheManager(){
        CacheConfiguration softwareIdCacheConfiguration = new CacheConfiguration();
        softwareIdCacheConfiguration.setName("softwareId-cache");

        CacheConfiguration dataRecipientExecutionTimeConfiguration = new CacheConfiguration();
        dataRecipientExecutionTimeConfiguration.setName("dr-scheduled-et-cache");

        Configuration configuration = new Configuration();
        configuration.addCache(softwareIdCacheConfiguration);
        configuration.addCache(dataRecipientExecutionTimeConfiguration);

        return CacheManager.newInstance(configuration);
    }

    @Bean
    public org.springframework.cache.CacheManager cacheManager(){
        return super.cacheManager();
    }
}
