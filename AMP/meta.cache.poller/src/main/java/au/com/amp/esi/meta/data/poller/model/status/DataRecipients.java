package au.com.amp.esi.meta.cache.poller.model.status;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class DataRecipients {

    private List<DataRecipient> dataRecipients;
    //private List<SoftwareProduct> softwareProducts;
}
