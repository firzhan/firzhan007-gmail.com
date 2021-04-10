package au.com.amp.esi.extapi.km.model;

import com.google.gson.annotations.SerializedName;

public abstract class IntrospectParent {

    @SerializedName("active")
    private boolean active;

    @SerializedName("exp")
    private long expiry;

    @SerializedName("scope")
    private String scope;

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public long getExpiry() {
        return expiry;
    }

    public void setExpiry(long expiry) {
        this.expiry = expiry;
    }


    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


}
