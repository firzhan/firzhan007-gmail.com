package au.com.amp.esi.meta.data.poller.model.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SoftwareProductEvent implements Serializable {

    private String softwareId;
    private String orgId;
    private String legalEntityId;
    private String status;

}
