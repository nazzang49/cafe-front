package com.toy.platform.cafe.exception;

import lombok.Getter;

/**
 * 응답 및 에러 코드 관리
 */
@Getter
public enum ErrorCode {

    /* 기본 */
    INVALID_INPUT_VALUE(400, "9000", " Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, "9001", "Method Not Allowed"),
    ENTITY_NOT_FOUND(404, "9002", " Entity Not Found : %s"),
    INTERNAL_SERVER_ERROR(500, "9003", "Internal Server Error"),
    DUPLICATE_ENTITY(409, "9004", "Duplicate Entity"),
    BAD_REQUEST(400, "9005", "Bad Request"),
    UNAUTHORIZED(401, "9006", "Unauthorized Account"),
    NOT_FOUND(404, "9007", "Not Found"),
    PERMISSION_DENIED(403, "9008", "Permission Denied"),
    ;

    private final int status;
    private final String message;
    private final String code;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }
}
