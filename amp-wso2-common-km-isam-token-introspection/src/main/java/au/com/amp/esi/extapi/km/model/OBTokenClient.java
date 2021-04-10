package au.com.amp.esi.extapi.km.model;

import feign.HeaderMap;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.Map;

public interface OBTokenClient {

    //client_id=OauthClient_for_OB_WSO2APIGW&grant_type=client_credentials&client_secret=<Secret_Value>&scope
    // =Introspect_Full_Access
    @RequestLine("POST")
    @Headers({"Content-type:application/x-www-form-urlencoded", "Accept:application/json"})
    OBAccessTokenInfo token(@HeaderMap Map<String, Object> headers, @Param("client_id") String clientId,
                            @Param("scope") String scope, @Param("grant_type") String grantType,
                            @Param("client_secret") String clientSecret);
}
