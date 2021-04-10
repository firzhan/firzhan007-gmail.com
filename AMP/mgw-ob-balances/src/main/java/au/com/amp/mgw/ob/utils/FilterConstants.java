package au.com.amp.mgw.ob.utils;

import org.wso2.micro.gateway.interceptor.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FilterConstants {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String X_FAPI_INTERACTION_ID = "x-fapi-interaction-id";
    public static final String X_REQUEST_ID = "X-Request-Id";
    public static final String CDS_MTLS_CERT_HEADER = "x-cds-mtls-cert";
    public static final String X_FORWARDED_FOR_HEADER = "x-forwarded-for";
    public static final String MODIFIED_TOKEN_KEY = "modified-token";
    public static final String API_CONTEXT_KEY = "api_context";
    public static final String API_VERSION_KEY = "api_version";
    public static final String RESOURCE_PATH_KEY = "matching_resource";
    public static final String REQUEST_METHOD_KEY = "REQUEST_METHOD";
    public static final String AUTHORIZED_DOMAIN_KEY = "authorizedDomains=";
    public static final String CDR_ARRANGEMENT_ID_KEY = "cdr_arrangement_id";
    public static final String SCV_ID_KEY = "scv_id";
    public static final String ENTITY_REF_KEY = "entity_ref";
    public static final String BANKING_ACCOUNTS_RESOURCE_PATH = "/accounts";
    public static final String COMMON_CUSTOMER_DETAIL_SECOND_HALF_RESOURCE_PATH = "/detail";
    public static final String COMMON_CUSTOMER_DETAIL_RESOURCE_PATH = "/common/customer/detail";
    public static final String RAW_PATH_CONTEXT_ATTRIBUTE_KEY = "rawPath";
    public static final String BEARER_PREFIX = "Bearer";
    public static final String NONE = "NONE";
    public static final String ERROR_DATA = "error_data";

    public static final String OB_SUFFIX = "OB";

    public static final List<String> productCategoryList;
    static {
        List<String> productCategoryModifiedList =new ArrayList<>();
        productCategoryModifiedList.add("BUSINESS_LOANS");
        productCategoryModifiedList.add("CRED_AND_CHRG_CARDS");
        productCategoryModifiedList.add("LEASES");
        productCategoryModifiedList.add("MARGIN_LOANS");
        productCategoryModifiedList.add("OVERDRAFTS");
        productCategoryModifiedList.add("PERS_LOANS");
        productCategoryModifiedList.add("REGULATED_TRUST_ACCOUNTS");
        productCategoryModifiedList.add("RESIDENTIAL_MORTGAGES");
        productCategoryModifiedList.add("TERM_DEPOSITS");
        productCategoryModifiedList.add("TRADE_FINANCE");
        productCategoryModifiedList.add("TRAVEL_CARDS");
        productCategoryModifiedList.add("TRANS_AND_SAVINGS_ACCOUNTS");
        productCategoryModifiedList.add("OPEN");
        productCategoryModifiedList.add("CLOSED");
        productCategoryModifiedList.add("ALL");

        productCategoryList = Collections.unmodifiableList(productCategoryModifiedList);
    }

    public static final List<String> openStatusList;
    static {
        List<String> openStatusModifiedList = new ArrayList<>();
        openStatusModifiedList.add("OPEN");
        openStatusModifiedList.add("CLOSED");
        openStatusModifiedList.add("ALL");
        openStatusList = Collections.unmodifiableList(openStatusModifiedList);
    }

    public static final List<String> isOwnedList;
    static {
        List<String> isOwnedModifiedList = new ArrayList<>();
        isOwnedModifiedList.add("true");
        isOwnedModifiedList.add("false");
        isOwnedList = Collections.unmodifiableList(isOwnedModifiedList);
    }
}
