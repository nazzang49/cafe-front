package com.toy.platform.cafe.filter;

import com.toy.platform.cafe.util.RequestInfoUtil;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.Inet4Address;
import java.util.UUID;

/**
 * 매 요청 시 거치는 기본 필터
 */
@Order(0)
public class RequestInitFilter extends OncePerRequestFilter {

    private boolean isInitialized = false;
    private String customLog;
    private String serviceName;
    private String serviceDomain;
    private String hostName;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            if (!isInitialized) {
                doInitialize();
            }
            String requestId = UUID.randomUUID().toString();
//            RequestInfoUtil.setRequestId(requestId);
            ThreadContext.put("requestId", requestId);
            ThreadContext.put("customLog", customLog);
            ThreadContext.put("serviceName", serviceName);
            ThreadContext.put("serviceDomain", serviceDomain);
            ThreadContext.put("hostsName", Inet4Address.getLocalHost().getHostName());
            ThreadContext.put("requestMethod", request.getMethod());
        } catch (Exception e) {
            e.printStackTrace();
        }
        chain.doFilter(request, response);
    }

    private void doInitialize() throws Exception {
        switch (currentProfile()) {
            case "dev" :
                this.customLog = "custom-log-dev";
                this.serviceName = "data-api-dev";
                this.serviceDomain = "service-domain-dev";
                break;
            case "qa" :
                this.customLog = "custom-log-qa";
                this.serviceName = "data-api-qa";
                this.serviceDomain = "service-domain-qa";
                break;
            case "prod" :
                this.customLog = "custom-log-prod";
                this.serviceName = "data-api-prod";
                this.serviceDomain = "service-domain-prod";
                break;
            default:
                this.customLog = "custom-log-default";
                this.serviceName = "data-api-default";
                this.serviceDomain = "service-domain-default";
        }
        this.hostName = Inet4Address.getLocalHost().getHostName();
        this.isInitialized = true;
    }

    private String currentProfile() {
        return System.getenv("APPS_ENV") == null ? "local" : System.getenv("APPS_ENV");
    }
}
