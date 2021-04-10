package au.com.amp.esi.meta.data.poller.component;

import au.com.amp.esi.meta.data.poller.config.MetaCachePollerConfig;
import au.com.amp.esi.meta.data.poller.model.event.SoftwareProductEvent;
import au.com.amp.esi.meta.data.poller.model.status.AggregatedEvent;
import au.com.amp.esi.meta.data.poller.model.status.SoftwareProducts;
import au.com.amp.esi.meta.data.poller.model.profile.RecipientStatus;
import au.com.amp.esi.meta.data.poller.service.CachingLayerService;
import au.com.amp.esi.meta.data.poller.service.ConnectionProcessingService;
import au.com.amp.esi.meta.data.poller.utils.MetaCachePollerConstant;
import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Getter
@Setter
@Component
public class MetaDataPollerScheduler {

    private static ch.qos.logback.classic.Logger logger =
            (Logger) LoggerFactory.getLogger(MetaDataPollerScheduler.class);

    @Autowired private ConnectionProcessingService connectionProcessingService;
    @Autowired private MetaCachePollerConfig metaCachePollerConfig;
    @Autowired private CachingLayerService cachingLayerService;

    @Scheduled(fixedDelayString = "${accc.metadata-cache.status-check-delay}", initialDelay = 1000L)
    public void triggerCronJob() throws JsonProcessingException {

        long currentTime = System.currentTimeMillis();
        long dataRecipientExecutionTime = cachingLayerService.
                getExecutionTimeCache(MetaCachePollerConstant.DATA_RECIPIENT_EXECUTION_TIME_KEY);

        Set<SoftwareProductEvent> softwareProductEventSet;

        logger.info(String.format("currentTime:%d dataRecipientExecutionTime:%d differnce:%d ", currentTime,
                dataRecipientExecutionTime,
                dataRecipientExecutionTime - metaCachePollerConfig.getDeltaDelayMilliSeconds()
                ));

        if(dataRecipientExecutionTime == 0L ||
                (currentTime >= dataRecipientExecutionTime - metaCachePollerConfig.getDeltaDelayMilliSeconds())){

            //Time to update the metadata cache
            RecipientStatus recipientStatus =
                    (RecipientStatus) connectionProcessingService.invokeRecipientsEP(RecipientStatus.class,
                    String.format(MetaCachePollerConstant.DATA_RECIPIENT_URL_FORMAT,
                    "run.mocky.io/v3/96b84d7c-6c66-4df2-b3b2-46a45d48256b"));

            logger.info("Invoking the Recipient endpoint with the result: " + recipientStatus.toString());

            cachingLayerService.putExecutionTimeCache(MetaCachePollerConstant.DATA_RECIPIENT_EXECUTION_TIME_KEY
                    ,System.currentTimeMillis() + metaCachePollerConfig.getRecipientDelayMilliSeconds() );
            cachingLayerService.flushCache();
            softwareProductEventSet = processLegalEntities(recipientStatus);
        } else {

            SoftwareProducts softwareProducts = (SoftwareProducts) connectionProcessingService.invokeRecipientsEP(SoftwareProducts.class,
                    String.format(MetaCachePollerConstant.DATA_RECIPIENT_SOFTWARE_PRODUCTS_STATUS_URL_FORMAT,
                            "run.mocky.io/v3/eeb990a8-29b6-46cf-9eb1-215d6f9c586c"));
            softwareProductEventSet = processSoftwareProductEvents(softwareProducts);

            logger.info("Invoking the Status endpoint with the result: " + softwareProducts.toString());
        }

      /* //DataRecipients dataRecipients = invokeDataRecipientsEP();
       DataRecipients dataRecipients = (DataRecipients) connectionProcessingService.invokeRecipientsEP(DataRecipients.class,
               String.format(MetaCachePollerConstant.DATA_RECIPIENT_STATUS_URL_FORMAT,
                       metaCachePollerConfig.getMetadataCacheDataRecipientHostname()));*/



        AggregatedEvent aggregatedEvent = new AggregatedEvent();
        aggregatedEvent.setSoftwareProductEventSet(softwareProductEventSet);
       /* if(dataRecipients != null &&  !dataRecipients.getDataRecipients().isEmpty()){
            aggregatedEvent.setDataRecipients(dataRecipients.getDataRecipients());
        }

        if(softwareProducts != null && !softwareProducts.getSoftwareProducts().isEmpty()){
            aggregatedEvent.setSoftwareProducts(softwareProducts.getSoftwareProducts());
        }*/
        connectionProcessingService.sendMessage(aggregatedEvent);
    }

    private Set<SoftwareProductEvent> processLegalEntities(RecipientStatus recipientStatus){

        final Set<SoftwareProductEvent> softwareProductEventSet = new HashSet<>();
        recipientStatus.getData().stream().parallel().forEach(legalEntity -> {
            legalEntity.getDataRecipientBrands().stream().parallel().forEach(dataRecipientBrand -> {
                dataRecipientBrand.getSoftwareProducts().stream().parallel().forEach(softwareProductEx -> {
                    SoftwareProductEvent softwareProductEvent = new SoftwareProductEvent();
                    softwareProductEvent.setSoftwareId(softwareProductEx.getSoftwareProductId());
                    softwareProductEvent.setLegalEntityId(legalEntity.getLegalEntityId());
                    softwareProductEvent.setOrgId(dataRecipientBrand.getDataRecipientBrandId());
                    softwareProductEvent.setStatus(softwareProductEx.getStatus());

                    softwareProductEventSet.add(softwareProductEvent);
                    cachingLayerService.putSoftwareProductCache(softwareProductEvent);
                });
            });
        });
        return softwareProductEventSet;
    }

    private Set<SoftwareProductEvent> processSoftwareProductEvents(SoftwareProducts softwareProducts){

        final Set<SoftwareProductEvent> softwareProductEventSet = new HashSet<>();
        softwareProducts.getSoftwareProducts().stream().parallel().forEach(softwareProduct -> {
            Optional<SoftwareProductEvent> softwareProductEventOptional = cachingLayerService.getSoftwareProductCache(
                    softwareProduct.getSoftwareProductId());

            softwareProductEventOptional.ifPresent(softwareProductEventSet::add);
        });

        return softwareProductEventSet;
    }
}
