package com.chen.web.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author chen
 * @date 2020-11-02-22:44
 */
public class ResponseBean<T> {

    private String respCode;
    private String respDesc;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    public ResponseBean(String respCode, String respDesc, T result) {
        this.respCode = respCode;
        this.respDesc = respDesc;
        this.result = result;
    }

    public ResponseBean(String respCode, String respDesc) {
        this.respCode = respCode;
        this.respDesc = respDesc;
        this.result = null;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespDesc() {
        return respDesc;
    }

    public void setRespDesc(String respDesc) {
        this.respDesc = respDesc;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
