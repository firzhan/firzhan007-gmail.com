package com.wso2.finance.open.banking.mg.interceptor;

import au.com.amp.mgw.ob.utils.FilterConstants;
import com.google.gson.Gson;
import org.ballerinalang.jvm.values.ObjectValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.micro.gateway.interceptor.Caller;
import org.wso2.micro.gateway.interceptor.Interceptor;
import org.wso2.micro.gateway.interceptor.Request;
import org.wso2.micro.gateway.interceptor.Response;
import org.wso2.micro.gateway.interceptor.Utils;
import org.wso2.micro.gateway.interceptor.ConfigUtils;

import java.util.Map;

public class OpenBankingInterceptor  implements Interceptor {

    private static final Logger log = LoggerFactory.getLogger("ballerina");

    @Override
    public boolean interceptRequest(Caller caller, Request request) {

     /*   Map<String, Object> invocationContextAttributes = Utils.getInvocationContextAttributes();
        String resource = (String) invocationContextAttributes.get(FilterConstants.RESOURCE_PATH_KEY);
        String svcId = (String) invocationContextAttributes.get(FilterConstants.SCV_ID_KEY);
        ObjectValue requestObj = request.getNativeRequestObject();

        log.debug("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        log.debug("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxresource:" + resource);
        log.debug("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxsvcId:" + svcId);

        //Bearer 123456780008:MTIzNDU2Nzg6L2Nkcy1hdS92MS9jb21tb24vY3VzdG9tZXI6T0I=:/cds-au/v1/v1/common/customer:GET
        //String cacheKey = modifiedToken + ":" + apiContext + "/" + apiVersion + resource + ":" + method;
        if(resource.endsWith(FilterConstants.COMMON_CUSTOMER_RESOURCE_PATH) ||
                resource.endsWith(FilterConstants.COMMON_CUSTOMER_RESOURCE_PATH + "/")){
            requestObj.set(FilterConstants.RAW_PATH_CONTEXT_ATTRIBUTE_KEY,
                    requestObj.getStringValue(FilterConstants.RAW_PATH_CONTEXT_ATTRIBUTE_KEY) + "/" + svcId);
        } else {
            String newContext = FilterConstants.COMMON_CUSTOMER_RESOURCE_PATH + "/" + svcId
                    + FilterConstants.COMMON_CUSTOMER_DETAIL_SECOND_HALF_RESOURCE_PATH;
            String rawPath = requestObj.getStringValue(FilterConstants.RAW_PATH_CONTEXT_ATTRIBUTE_KEY).
                    replaceAll(FilterConstants.COMMON_CUSTOMER_DETAIL_RESOURCE_PATH, newContext);
            requestObj.set(FilterConstants.RAW_PATH_CONTEXT_ATTRIBUTE_KEY, rawPath);
        }

        log.debug("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxFilterConstants.RAW_PATH_CONTEXT_ATTRIBUTE_KEY:"
                + requestObj.getStringValue(FilterConstants.RAW_PATH_CONTEXT_ATTRIBUTE_KEY));
*/
        log.debug("Env From System: " + System.getenv("productionEndpoint_prod_endpoint_0"));

        log.debug("Env From ConfigUtils: " + ConfigUtils.getAsString("productionEndpoint.prod.endpoint_0", "NOT" +
                "-PICKED"));
        log.debug("User.name:" + System.getProperty("user.name"));



/*

        log.debug("productionEndpoint_prod_endpoint_0:" + );

*/


        return true;
    }



    @Override
    public boolean interceptResponse(Caller caller, Response response) {

        return true;
    }
}