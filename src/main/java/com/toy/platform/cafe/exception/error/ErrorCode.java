package com.toy.platform.cafe.exception.error;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum ErrorCode {

    /* common */
    INVALID_INPUT_VALUE(400, "9000", " Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, "9001", "Method Not Allowed"),
    ENTITY_NOT_FOUND(404, "9002", " Entity Not Found : %s"),
    INTERNAL_SERVER_ERROR(500, "9003", "Internal Server Error"),
    DUPLICATE_ENTITY(409, "9004", "Duplicate Entity"),
    BAD_REQUEST(400, "9005", "Bad Request"),
    UNAUTHORIZED(401, "9006", "Unauthorized Account"),
    NOT_FOUND(404, "9007", "Not Found"),
    PERMISSION_DENIED(403, "9008", "Permission Denied"),
    FAIL_TO_REQUEST(500, "5000", "통신 실패"),

    /* custom */
    FAIL_TO_SEARCH_LOCATION(400, "9008", "유사 이미지 검색 실패"),
    NOT_FOUND_UPLOAD_IMG(400, "9008", "업로드 이미지 없음"),
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
