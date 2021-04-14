package com.toy.platform.cafe.log;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.contrib.json.classic.JsonLayout;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Marker;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.net.Inet4Address;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class LogFormatterConverter extends JsonLayout {

    private String indexName;
    private String customLog;
    private String serviceName;
    private String serviceDomain;

    @Override
    protected void addCustomDataToJsonMap(Map<String, Object> map, ILoggingEvent event) {
        try {
            setDataEyeFormat();

            /* Custom Log vs General Log */
//            if (isDataEyeFormatMarker(event.getMarker())) {
//                map.clear();
//
//                Map data = parseJson(event.getFormattedMessage());
//
//                map.put("log_time", convertToHeaderDateFormat(event.getTimeStamp()));
//                map.put("index_name", indexName);
//                map.put("custom_log", customLog);
//                map.put("service_name", serviceName);
//                map.put("service_domain", serviceDomain);
//                map.put("hostname", Inet4Address.getLocalHost().getHostName());
//                map.put("request_id", (event.getMDCPropertyMap().get("requestId") == null) ? "system" : event.getMDCPropertyMap().get("requestId"));
//                map.put("log_type", event.getLevel().toString());
//
//                data.remove("index_name");
//                data.put("log_thread", event.getThreadName());
//                data.put("log_logger", event.getLoggerName());
//
//                map.put("data", data);
//            } else {
//
//            }

            map.clear();
            Map data = new HashMap();
            map.put("log_time", convertToHeaderDateFormat(event.getTimeStamp()));
            map.put("index_name", indexName);
            map.put("custom_log", customLog);
            map.put("service_name", serviceName);
            map.put("service_domain", serviceDomain);
            map.put("hostname", Inet4Address.getLocalHost().getHostName());
            map.put("request_id", (event.getMDCPropertyMap().get("requestId") == null) ? "system" : event.getMDCPropertyMap().get("requestId"));
            map.put("log_type", event.getLevel().toString());

            data.remove("index_name");
            data.put("log_thread", event.getThreadName());
            data.put("log_logger", event.getLoggerName());
            data.put("message", event.getFormattedMessage());
            map.put("data", data);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        super.addCustomDataToJsonMap(map, event);
    }

    private Map parseJson(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper() ;
        return mapper.readValue(json, Map.class);
    }

    private JSONObject parseDataEyeFormatData(String message) throws JSONException {
        return new JSONObject(message);
    }

    private String convertToHeaderDateFormat(Long timeMillis) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return simpleDateFormat.format(timeMillis);
    }

//    private boolean isDataEyeFormatMarker(Marker marker) {
//        return marker == DataEyeFormat.marker;
//    }

    private void setDataEyeFormat() {
        switch (currentProfile()) {
            case "dev" :
                this.indexName = "03090bb8-2370-42bf-b003-bc4421bb4cfb";
                this.customLog = "cf049221c0bd8d370e90a7b2f14004c2";
                this.serviceName = "cpay-admin-payments-dev";
                this.serviceDomain = "admin-payments-tdev.cafe24.com";
                break;
            case "dev1" :
                this.indexName = "20b7bf0f-8a9e-4e23-8258-90aa2a295855";
                this.customLog = "cf049221c0bd8d370e90a7b2f14004c2";
                this.serviceName = "cpay-admin-payments-dev1";
                this.serviceDomain = "cpay-dev1-admin-payments.cafe24.com";
                break;
            case "dev2" :
                this.indexName = "20b7bf0f-8a9e-4e23-8258-90aa2a295855";
                this.customLog = "cf049221c0bd8d370e90a7b2f14004c2";
                this.serviceName = "cpay-admin-payments-dev2";
                this.serviceDomain = "cpay-dev2-admin-payments.cafe24.com";
                break;
            case "dev3" :
                this.indexName = "20b7bf0f-8a9e-4e23-8258-90aa2a295855";
                this.customLog = "cf049221c0bd8d370e90a7b2f14004c2";
                this.serviceName = "cpay-admin-payments-dev3";
                this.serviceDomain = "cpay-dev3-admin-payments.cafe24.com";
                break;
            case "qa" :
                this.indexName = "20b7bf0f-8a9e-4e23-8258-90aa2a295855";
                this.customLog = "cf049221c0bd8d370e90a7b2f14004c2";
                this.serviceName = "cpay-admin-payments-qa";
                this.serviceDomain = "cpay-qa-admin-payments.cafe24.com";
                break;
            case "stage" :
                this.indexName = "20b7bf0f-8a9e-4e23-8258-90aa2a295855";
                this.customLog = "cf049221c0bd8d370e90a7b2f14004c2";
                this.serviceName = "cpay-admin-payments-stage";
                this.serviceDomain = "cpay-stage-admin-payments.cafe24.com";
                break;
            case "prod" :
                this.indexName = "b9978013-72e3-4398-96a9-3bbfb9928c71";
                this.customLog = "cf049221c0bd8d370e90a7b2f14004c2";
                this.serviceName = "cpay-admin-payments-prod";
                this.serviceDomain = "admin-payments.cafe24.com";
                break;
            default:
                this.indexName = "custom-index-default";
                this.customLog = "custom-log-default";
                this.serviceName = "service-name-default";
                this.serviceDomain = "service-domain-default";
        }
    }

    private String currentProfile() {
        return System.getenv("APPS_ENV") == null ? "local" : System.getenv("APPS_ENV");
    }
}