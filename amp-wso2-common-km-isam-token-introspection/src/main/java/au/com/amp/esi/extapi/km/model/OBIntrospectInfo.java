package au.com.amp.esi.extapi.km.model;

import com.google.gson.annotations.SerializedName;

public class OBIntrospectInfo extends IntrospectParent{


    @SerializedName("cdr_arrangement_id")
    private String arrangementId;

    @SerializedName("ampscvid")
    private String scvId;

    @SerializedName("ampeEntityRef")
    private String entityRef;

    @SerializedName("client_Id")
    private String clientId;

    @SerializedName("error_msg")
    private String errorMsg;

    @SerializedName("error_code")
    private String errorCode;

    public String getArrangementId() {
        return arrangementId;
    }

    public void setArrangementId(String arrangementId) {
        this.arrangementId = arrangementId;
    }

    public String getScvId() {
        return scvId;
    }

    public void setScvId(String scvId) {
        this.scvId = scvId;
    }

    public String getEntityRef() {
        return entityRef;
    }

    public void setEntityRef(String entityRef) {
        this.entityRef = entityRef;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
