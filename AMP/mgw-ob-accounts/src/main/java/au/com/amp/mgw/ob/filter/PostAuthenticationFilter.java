package au.com.amp.mgw.ob.filter;

import au.com.amp.mgw.ob.utils.FilterConstants;
import com.wso2.finance.open.banking.mg.exception.OpenBankingMicrogatewayException;
import com.wso2.finance.open.banking.mg.util.GatewayCacheUtil;
import org.apache.http.HttpStatus;
import org.ballerinalang.jvm.values.ObjectValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.micro.gateway.interceptor.Caller;
import org.wso2.micro.gateway.interceptor.Request;
import org.wso2.micro.gateway.interceptor.Response;
import org.wso2.micro.gateway.interceptor.Utils;


import java.io.IOException;
import java.util.Map;

public class PostAuthenticationFilter {

    private static final Logger log = LoggerFactory.getLogger("ballerina");


    public static boolean handleRequest(Caller caller, Request httpRequest, ObjectValue filterContext)
            throws OpenBankingMicrogatewayException {

     /*   Map<String, Object> invocationContextAttributes = Utils.getInvocationContextAttributes();
        String modifiedToken = (String) invocationContextAttributes.get(FilterConstants.MODIFIED_TOKEN_KEY);
        String apiContext = (String) invocationContextAttributes.get(FilterConstants.API_CONTEXT_KEY);
        String apiVersion = (String) invocationContextAttributes.get(FilterConstants.API_VERSION_KEY);
        String resource = (String) invocationContextAttributes.get(FilterConstants.RESOURCE_PATH_KEY);
        String method = (String) invocationContextAttributes.get(FilterConstants.REQUEST_METHOD_KEY);

        String cacheKey = modifiedToken + ":" + apiContext + "/" + apiVersion + resource + ":" + method;
        String keyValidationObj = GatewayCacheUtil.retrieveFromGatewayKeyValidationCache(cacheKey).toString();


        int domainIndex = keyValidationObj.trim().indexOf(FilterConstants.AUTHORIZED_DOMAIN_KEY) +
                FilterConstants.AUTHORIZED_DOMAIN_KEY.length();
        int spaceIndex = keyValidationObj.trim().indexOf(" ", domainIndex);
        String domainString = keyValidationObj.trim().substring(domainIndex, spaceIndex);

        if(log.isDebugEnabled()){
            log.debug(String.format("Cache Key %s Yield %s", cacheKey, keyValidationObj));
            log.debug("Authorized Domains List:" + domainString);
        }

        String[] domainListArr = domainString.trim().split(":");

        if(domainListArr[1].equals(FilterConstants.NONE)){
            log.error("SVC_ID is null for token request :" + cacheKey);
            Utils.addDataToContextAttributes(FilterConstants.ERROR_DATA,
                    "Processing the request aborted due to incomplete records");
            throw new OpenBankingMicrogatewayException(String.valueOf(HttpStatus.SC_NOT_ACCEPTABLE));
        }

        Utils.addDataToContextAttributes(FilterConstants.CDR_ARRANGEMENT_ID_KEY,domainListArr[0] );
        Utils.addDataToContextAttributes(FilterConstants.SCV_ID_KEY,domainListArr[1] );
        Utils.addDataToContextAttributes(FilterConstants.ENTITY_REF_KEY,domainListArr[2] );

       *//* CloseableHttpClient closeableHttpClient = FilterUtils.getHttpsClient();


        //Creating a HttpGet object
        HttpGet httpget = new HttpGet("https://abc.123.com/ ");

        //Printing the method used
        System.out.println("Request Type: "+httpget.getMethod());

        //Executing the Get request
        try {
            log.info("AAAAAAAAAAAAAAAAAAAAGoing to Call the endpoint and fail");
            HttpResponse httpresponse = closeableHttpClient.execute(httpget);
        } catch (IOException e) {
            log.error(e.getCause().toString());
        }
*//*
        //apiName=Consumer-Data-Standards apiPublisher=admin apiTier= applicationId=54 applicationName=ADR_Client applicationTier=Unlimited authorized=true authorizedDomains=123456:12334567:true consumerKey=ADR_Client contentAware=false endUserName=admin@carbon.super endUserToken= issuedTime=1607689478681 spikeArrestLimit=0 spikeArrestUnit= stopOnQuotaReach=true subscriber=admin subscriberTenantDomain=carbon.super throttlingDataList=api_level_throttling_key tier=Unlimited keyType=PRODUCTION userType=APPLICATION_USER validationStatus=0 validityPeriod=1606633558067319 scopes=common:customer.detail:read common:customer.basic:read
                //key: 123456780007:MTIzNDU2Nzg6L2Nkcy1hdS92MS9jb21tb24vY3VzdG9tZXI6T0I=:/cds-au/v1/v1/common/customer:GET
        //Bearer 123456780008:MTIzNDU2Nzg6L2Nkcy1hdS92MS9jb21tb24vY3VzdG9tZXI6T0I=:/cds-au/v1/v1/common/customer:GET
*/
        return true;
    }

    public static boolean handleResponse(Response httpResponse) throws Exception {
        // filter response logic
        return true;
    }
}
