package au.com.amp.esi.extapi.km.model;

import feign.HeaderMap;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.Map;

public interface OBIntrospectClient {

    //client_id=OauthClient_for_Illion&scope=Illion_Full_Access&token=12345&token_type_hint=access_token
    @RequestLine("POST")
    @Headers({"Content-type:application/x-www-form-urlencoded", "Accept:application/json"})
    OBIntrospectInfo introspect(@HeaderMap Map<String, Object> headers, @Param("cert_thumbprint") String cert_thumbprint,
                              @Param("apiEndpoint") String apiEndpoint, @Param("ADR_AT") String token,
                              @Param("token_hint_type") String tokenType);
}
