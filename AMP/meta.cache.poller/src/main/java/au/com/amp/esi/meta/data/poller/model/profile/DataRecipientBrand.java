package au.com.amp.esi.meta.cache.poller.model.profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataRecipientBrand implements Serializable {

    private String dataRecipientBrandId;
    private String brandName;
    private String logoUri;
    List<SoftwareProductEx> softwareProducts;


}
