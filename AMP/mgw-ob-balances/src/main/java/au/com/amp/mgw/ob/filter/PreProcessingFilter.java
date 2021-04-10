package au.com.amp.mgw.ob.filter;

import au.com.amp.mgw.ob.utils.FilterConstants;
import com.wso2.finance.open.banking.mg.exception.OpenBankingMicrogatewayException;
import org.ballerinalang.jvm.values.ObjectValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.micro.gateway.interceptor.Caller;
import org.wso2.micro.gateway.interceptor.Request;
import org.wso2.micro.gateway.interceptor.Response;
import org.wso2.micro.gateway.interceptor.Utils;

import java.util.Base64;
import java.util.UUID;

public class PreProcessingFilter {

    private static final Logger log = LoggerFactory.getLogger("ballerina");


    public static boolean handleRequest(Caller caller, Request httpRequest, ObjectValue filterContext) throws OpenBankingMicrogatewayException {

        String authorizationHeader = httpRequest.getHeader(FilterConstants.AUTHORIZATION_HEADER);
        String mtlsCert = httpRequest.getHeader(FilterConstants.CDS_MTLS_CERT_HEADER);
        //String xForwardedFor = httpRequest.getHeader(FilterConstants.X_FORWARDED_FOR_HEADER);
        String requestPath = httpRequest.getRequestPath();

       /* if(xForwardedFor != null && !xForwardedFor.isEmpty()){
            String[] xForArr = xForwardedFor.split(",");
            log.debug("Length xForArr:" + xForArr.length);

            for(String s : xForArr){
                log.debug("s:" + s);
            }
            if(xForArr.length >= 1)
                httpRequest.setHeader(FilterConstants.X_FORWARDED_FOR_HEADER, xForArr[0].trim());
        }*/

        String postFixHeader =
                (mtlsCert != null ? mtlsCert : FilterConstants.NONE) + ":" + requestPath + ":" + FilterConstants.OB_SUFFIX;

        authorizationHeader += ":" + new String(Base64.getEncoder().encode(postFixHeader.getBytes()));
        //(authorizationHeader != null ?authorizationHeader : "NONE") + ":" ;
        httpRequest.setHeader(FilterConstants.AUTHORIZATION_HEADER, authorizationHeader);

        Utils.addDataToContextAttributes(FilterConstants.MODIFIED_TOKEN_KEY,
                authorizationHeader.startsWith(FilterConstants.BEARER_PREFIX) ?
                        authorizationHeader.replaceFirst(FilterConstants.BEARER_PREFIX, "").trim() :
                        authorizationHeader);

        return true;
    }

    public static boolean handleResponse(Response httpResponse) throws Exception {
        // filter response logic
        return true;
    }
}
