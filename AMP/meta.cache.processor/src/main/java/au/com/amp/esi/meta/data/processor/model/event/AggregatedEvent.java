package au.com.amp.esi.meta.data.processor.model.kafka;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AggregatedResult {

    private List<DataRecipient> dataRecipients = new ArrayList<>();
    private List<SoftwareProduct> softwareProducts = new ArrayList<>();
}
