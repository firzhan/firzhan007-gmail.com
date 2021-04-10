package au.com.amp.esi.meta.data.processor.component;

import au.com.amp.esi.meta.data.processor.config.MetaCacheProcessorConfig;
import au.com.amp.esi.meta.data.processor.model.dao.DCRTable;
import au.com.amp.esi.meta.data.processor.model.kafka.AggregatedResult;
import au.com.amp.esi.meta.data.processor.model.kafka.DataRecipient;
import au.com.amp.esi.meta.data.processor.repo.DCRTableRepository;
import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Component
public class MetaCacheProcessorPoller {

    private static Logger logger =
            (Logger) LoggerFactory.getLogger(MetaCacheProcessorPoller.class);

    private HttpEntity<String> entity;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MetaCacheProcessorConfig metaCacheProcessorConfig;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DCRTableRepository dcrTableRepository;

    //private String groupId;

    private final Map<String, String> headerMap = new HashMap<>();

    @KafkaListener(topics = "${kafka.dataRecipientTopicName}", groupId = "${kafka.groupId}")
    public void listenTopics( @Payload String message,
                              @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) throws JsonProcessingException {
        logger.info("Received Message: " + message + "from partition: " + partition);

        AggregatedResult aggregatedResult = objectMapper.readValue(message, AggregatedResult.class);

        DataRecipient dataRecipient = aggregatedResult.getDataRecipients().get(0);
        System.out.println("size :" + dcrTableRepository.findByOrgId(dataRecipient.getDataRecipientId()).size());


    }


    private void processDataRecipient(List<DataRecipient> dataRecipientList){

        for(DataRecipient dataRecipient : dataRecipientList){

            List<DCRTable> dcrTableList = dcrTableRepository.findByOrgId(dataRecipient.getDataRecipientId());

            for(DCRTable dcrTable : dcrTableList){
                if(dcrTable.getProcessedState() == 1){

                }
            }
        }
    }



    /*@PostConstruct
    public void init() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(MetaCachePollerConstant.X_VERSION_HEADER_NAME, metaCachePollerConfig.getMetadataCacheVersion());
        headers.set(MetaCachePollerConstant.HOST_HEADER_NAME, metaCachePollerConfig.getMetadataCacheDataRecipientHostname());
        headers.set(MetaCachePollerConstant.ACCEPT_HEADER_NAME, MediaType.APPLICATION_JSON_VALUE);

        entity = new HttpEntity<String>(headers);
    }*/

    /*@Scheduled(fixedDelayString = "${accc.metadata-cache.delay}", initialDelay = 1000L)
    public void processMetaData() throws JsonProcessingException {

       //DataRecipients dataRecipients = invokeDataRecipientsEP();
       DataRecipients dataRecipients = (DataRecipients) invokeRecipientsEP(DataRecipients.class,
               String.format(MetaCachePollerConstant.DATA_RECIPIENT_STATUS_URL_FORMAT,
                       metaCachePollerConfig.getMetadataCacheDataRecipientHostname()));

       SoftwareProducts softwareProducts = (SoftwareProducts) invokeRecipientsEP(SoftwareProducts.class,
                String.format(MetaCachePollerConstant.DATA_RECIPIENT_SOFTWARE_PRODUCTS_STATUS_URL_FORMAT,
                        metaCachePollerConfig.getMetadataCacheSoftwareStatusHostname()));

        AggregatedResult aggregatedResult = new AggregatedResult();
        if(dataRecipients != null &&  !dataRecipients.getDataRecipients().isEmpty()){
            aggregatedResult.setDataRecipients(dataRecipients.getDataRecipients());
        }

        if(softwareProducts != null && !softwareProducts.getSoftwareProducts().isEmpty()){
            aggregatedResult.setSoftwareProducts(softwareProducts.getSoftwareProducts());
        }

        sendMessage(aggregatedResult);
    }

*/
 /*   private <T> Object invokeRecipientsEP(Class<T> cl, String url){
        ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET, entity, cl);
        return response.getBody();
    }

    private void sendMessage(AggregatedResult aggregatedResult) throws JsonProcessingException {

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
    }*/
}
