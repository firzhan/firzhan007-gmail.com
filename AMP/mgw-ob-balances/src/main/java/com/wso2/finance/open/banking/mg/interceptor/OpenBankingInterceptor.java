package com.wso2.finance.open.banking.mg.interceptor;

import au.com.amp.mgw.ob.utils.FilterConstants;
import org.ballerinalang.jvm.values.ObjectValue;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.micro.gateway.interceptor.Caller;
import org.wso2.micro.gateway.interceptor.Interceptor;
import org.wso2.micro.gateway.interceptor.Request;
import org.wso2.micro.gateway.interceptor.Response;
import org.wso2.micro.gateway.interceptor.Utils;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class OpenBankingInterceptor  implements Interceptor {

    private static final Logger log = LoggerFactory.getLogger("ballerina");

    @Override
    public boolean interceptRequest(Caller caller, Request request) {

        Map<String, Object> invocationContextAttributes = Utils.getInvocationContextAttributes();
        String resource = (String) invocationContextAttributes.get(FilterConstants.RESOURCE_PATH_KEY);

        log.debug("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxsvcId:" + resource);
        ObjectValue requestObj = request.getNativeRequestObject();

        Optional<String> entityRefOptional =
                Optional.ofNullable((String) invocationContextAttributes.get(FilterConstants.ENTITY_REF_KEY));

        if(!entityRefOptional.isPresent()){
            log.error("Entity Ref is null for token request :");
            caller.respond(generateErrorResponseFromRequest("Processing the request aborted due to incomplete " +
                    "records", 406L));
            return false;
        }

        log.debug("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxsvcId:" + entityRefOptional.get());
        log.debug("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxsvcId:" + resource);

        //Bearer 123456780008:MTIzNDU2Nzg6L2Nkcy1hdS92MS9jb21tb24vY3VzdG9tZXI6T0I=:/cds-au/v1/v1/common/customer:GET
        //String cacheKey = modifiedToken + ":" + apiContext + "/" + apiVersion + resource + ":" + method;
        if(resource.endsWith(FilterConstants.BANKING_ACCOUNTS_RESOURCE_PATH) ||
                resource.endsWith(FilterConstants.BANKING_ACCOUNTS_RESOURCE_PATH + "/")){

            String rawPath = requestObj.getStringValue(FilterConstants.RAW_PATH_CONTEXT_ATTRIBUTE_KEY);
            StringBuilder rawPathBuilder = new StringBuilder();

            if(rawPath.length() > 0){
                if(!rawPath.contains("?")){
                    rawPathBuilder.append(rawPath);
                    if(!rawPathBuilder.toString().endsWith("/")){
                        rawPathBuilder.append("/");
                    }
                    rawPathBuilder.append(entityRefOptional.get());
                } else {
                    String[] tokenizer = rawPath.split("\\?");
                    rawPathBuilder.append(tokenizer[0]);
                    if(!tokenizer[0].trim().endsWith("/")){
                        rawPathBuilder.append("/");
                    }

                    rawPathBuilder.append(entityRefOptional.get());

                    for(int index = 1; index < tokenizer.length; ++index){
                        rawPathBuilder.append("?").append(tokenizer[index]);
                    }
                }
            } else {
                caller.respond(generateErrorResponseFromRequest("Invalid Context Path Provided", 400L));
                return false;
            }

            requestObj.set(FilterConstants.RAW_PATH_CONTEXT_ATTRIBUTE_KEY,rawPathBuilder.toString());
            log.debug("rawPath After Modification:" + requestObj.getStringValue(FilterConstants.RAW_PATH_CONTEXT_ATTRIBUTE_KEY));

        } else {
            log.error("Operation not supported for the Resource Path: " + resource);
            caller.respond(generateErrorResponseFromRequest("Operation not supported for the Resource Path: " + resource, 406L));
            return false;
        }

        log.debug("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxFilterConstants.RAW_PATH_CONTEXT_ATTRIBUTE_KEY:"
                + requestObj.getStringValue(FilterConstants.RAW_PATH_CONTEXT_ATTRIBUTE_KEY));

        String xRequestId =  request.getHeader(FilterConstants.X_REQUEST_ID);

        if(xRequestId == null || xRequestId.isEmpty()){
            request.setHeader(FilterConstants.X_REQUEST_ID,
                    request.getHeader(FilterConstants.X_FAPI_INTERACTION_ID));
        }
        return true;
    }

    @Override
    public boolean interceptResponse(Caller caller, Response response) {

        return true;
    }

     public Response generateErrorResponseFromRequest(String errorResponseString, long status) {
        Response response = new Response();
        response.setContentType("application/json");
        response.setResponseCode(status);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Error", errorResponseString);
        response.setJsonPayload(jsonObject);
        return response;
    }
}