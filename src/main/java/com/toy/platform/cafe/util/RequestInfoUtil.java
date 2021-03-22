package com.toy.platform.cafe.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import java.time.LocalDateTime;

/**
 * 요청 관련 기본 정보
 */
public class RequestInfoUtil {

    private static final String REQUEST_ID = "requestId";
    private static final String REQUEST_AT = "requestAt";
    private static final String REQUEST_DATE = "requestDate";

    /**
     * 로그 추적용 UUID 및 요청시간 저장
     *
     * @param obj
     */
    public static void setRequestId(String obj) {
        setAttr(REQUEST_ID, obj);
        setAttr(REQUEST_AT, LocalDateTime.now());
    }

    /**
     * 로그 추적용 UUID
     *
     * @return
     */
    public static String getRequestId() {
        return (String) getAttr(REQUEST_ID);
    }

    /**
     * 요청시간
     *
     * @return
     */
    public static LocalDateTime getRequestAt() {
        return (LocalDateTime) getAttr(REQUEST_AT);
    }

    /**
     * 요청 시간 반환
     *
     * @return
     */
    public static LocalDateTime getRequestDate() {
        return (LocalDateTime) getAttr(REQUEST_DATE);
    }

    /**
     * 객체 호출 from Request
     *
     * @param name
     * @return
     */
    public static Object getAttr(String name) {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getAttribute(name, ServletRequestAttributes.SCOPE_REQUEST);
    }

    /**
     * 객체 저장 to Request
     *
     * @param key
     * @param obj
     */
    public static void setAttr(String key, Object obj) {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        attr.setAttribute(key, obj, ServletRequestAttributes.SCOPE_REQUEST);
    }
}
