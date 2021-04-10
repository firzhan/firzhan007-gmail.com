package au.com.amp.esi.meta.cache.poller.service;

import au.com.amp.esi.meta.cache.poller.component.MetaCachePollerScheduler;
import au.com.amp.esi.meta.cache.poller.config.MetaCachePollerConfig;
import au.com.amp.esi.meta.cache.poller.model.status.AggregatedResult;
import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.RestTemplate;

@Service
public class ConnectionProcessingService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HttpEntity httpEntity;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MetaCachePollerConfig metaCachePollerConfig;

    private static ch.qos.logback.classic.Logger logger =
            (Logger) LoggerFactory.getLogger(ConnectionProcessingService.class);

    public <T> Object invokeRecipientsEP(Class<T> cl, String url){
        ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, cl);
        return response.getBody();
    }

    public void sendMessage(AggregatedResult aggregatedResult) throws JsonProcessingException {

        String payload = objectMapper.writeValueAsString(aggregatedResult);
        kafkaTemplate.send(metaCachePollerConfig.getDataRecipientTopicName(), payload);
        ListenableFuture<SendResult<String, String>> future =
                kafkaTemplate.send(metaCachePollerConfig.getDataRecipientTopicName(), payload);
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                logger.info("Inserted Payload: " + payload + " With Offset="
                        +  result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                logger.error("Unable to send message=["
                        + payload + "] due to : " + ex.getMessage());
                //TODO --> Exponential BackOff implementation.
            }
        });
    }

}
