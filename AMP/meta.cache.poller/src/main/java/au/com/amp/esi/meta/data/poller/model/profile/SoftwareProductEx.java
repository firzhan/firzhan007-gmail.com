package au.com.amp.esi.meta.cache.poller.model.profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SoftwareProductEx implements Serializable {

    private String softwareProductId;
    private String softwareProductName;
    private String softwareProductDescription;
    private String logoUri;
    private String status;

    //
}
