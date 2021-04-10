package au.com.amp.esi.meta.cache.poller.model.status;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class DataRecipient {

    private String dataRecipientId;
    private String dataRecipientStatus;

}
