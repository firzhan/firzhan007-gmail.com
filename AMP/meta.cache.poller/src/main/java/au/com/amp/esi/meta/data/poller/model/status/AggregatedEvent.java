package au.com.amp.esi.meta.data.poller.model.status;

import au.com.amp.esi.meta.data.poller.model.event.SoftwareProductEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AggregatedEvent {

    //private List<DataRecipient> dataRecipients = new ArrayList<>();
    //private List<SoftwareProduct> softwareProducts = new ArrayList<>();
    private Set<SoftwareProductEvent> softwareProductEventSet = new HashSet<>();
}
