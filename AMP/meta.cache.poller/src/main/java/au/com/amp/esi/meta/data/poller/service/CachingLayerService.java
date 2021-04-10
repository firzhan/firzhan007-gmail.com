package au.com.amp.esi.meta.cache.poller.service;

import au.com.amp.esi.meta.cache.poller.model.event.SoftwareProductEvent;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CachingLayerService {

    @Cacheable( value = "softwareId-cache", key = "#softwareID", unless = "#result != null")
    public SoftwareProductEvent getSoftwareIdSnapshotCache(SoftwareProductEvent softwareProductEvent, String softwareID){
        return null;
    }

    @CachePut( value = "softwareId-cache", key = "#softwareIDSnapShot.softwareId")
    public SoftwareProductEvent updateSoftwareIdEventCache(SoftwareProductEvent softwareProductEvent){
        return softwareProductEvent;
    }

    @CacheEvict(value="softwareId-cache", allEntries=true)
    public void flushCache(){}

    @Cacheable( value = "dr-scheduled-et-cache", key = "#executionTimeKey", unless = "#result != 0")
    public long getExecutionTimeCache(String executionTimeKey){
        return 0L;
    }

    @CachePut( value = "dr-scheduled-et-cache", key = "#executionTimeKey")
    public long updateExecutionTimeCache(String executionTimeKey, long executionTime){
        return executionTime;
    }
}
