package au.com.amp.esi.meta.cache.poller.utils;

public final class MetaCachePollerConstant {

    private MetaCachePollerConstant() {}

    public static final String X_VERSION_HEADER_NAME = "x-v";
    public static final String ACCEPT_HEADER_NAME = "Accept";
    public static final String DATA_RECIPIENT_STATUS_URL_FORMAT = MetaCachePollerConstant.DATA_RECIPIENT_URL_FORMAT +
            "/status";
    public static final String DATA_RECIPIENT_URL_FORMAT = "https://%s/cdr-register/v1/banking/data-recipients";
    public static final String DATA_RECIPIENT_SOFTWARE_PRODUCTS_STATUS_URL_FORMAT = "https://%s/cdr-register/v1" +
            "/banking/data-recipients/brands/software-products/status";
    public static final String DATA_RECIPIENT_EXECUTION_TIME_KEY = "DATA_RECIPIENT_EXECUTION_TIME_KEY";

}
