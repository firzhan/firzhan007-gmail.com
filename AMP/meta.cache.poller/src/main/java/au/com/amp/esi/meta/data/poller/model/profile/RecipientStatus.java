package au.com.amp.esi.meta.cache.poller.model.profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RecipientStatus implements Serializable {

    private List<LegalEntity> data;

}
