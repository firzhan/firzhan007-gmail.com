package au.com.amp.mgw.ob.filter;

import org.ballerinalang.jvm.values.ObjectValue;
import org.wso2.micro.gateway.interceptor.Caller;
import org.wso2.micro.gateway.interceptor.Request;
import org.wso2.micro.gateway.interceptor.Response;

public class EmptyFilter {

    public static boolean handleRequest(Caller caller, Request httpRequest, ObjectValue filterContext){
        return true;
    }


    public static boolean handleResponse(Response httpResponse) throws
            Exception {
        // filter response logic
        return true;
    }
}
