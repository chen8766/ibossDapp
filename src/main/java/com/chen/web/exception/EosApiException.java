package com.chen.web.exception;

import java.util.Objects;

public class EosApiException extends RuntimeException{

    private String message;

    private int code;

    private EosError error;

    private String methodKey;

    @Override
    public String getMessage() {
        return message;
    }

    public String getFormatErrorMsg() {
        String errorMsg = "httpStatus: " + code;
        if (Objects.nonNull(error)) {
            errorMsg += ", code: " + error.getCode() + ", what: " + error.getWhat();
            EosErrorDetails[] details = error.getDetails();
            if (details.length > 0) {
                errorMsg += ", detailMessage: " + details[0].getMessage();
            }
        }
        return errorMsg;
    }

    public String getMethodKey() {
        return methodKey;
    }

    public void setMethodKey(String methodKey) {
        this.methodKey = methodKey;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public EosError getError() {
        return error;
    }

    public void setError(EosError error) {
        this.error = error;
    }

    public String getDetailedMessage() {
        return error == null ? message : message + ": " + error.getWhat();
    }

    public Integer getEosErrorCode() {
        return error == null ? null : error.getCode();
    }

    @Override
    public String toString() {
        return "EosApiException{" +
                "message='" + message + '\'' +
                ", code=" + code +
                ", error=" + error +
                ", methodKey='" + methodKey + '\'' +
                "} " + super.toString();
    }
}
