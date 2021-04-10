package au.com.amp.esi.meta.cache.poller.model.profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LegalEntity  implements Serializable {

    private String legalEntityId;
    private String legalEntityName;
    private String accreditationNumber;
    private String industry;
    private String logoUri;
    private String status;
    private Date lastUpdated;
    private List<DataRecipientBrand> dataRecipientBrands;

}
