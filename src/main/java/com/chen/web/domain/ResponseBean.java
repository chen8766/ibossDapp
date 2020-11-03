package com.chen.web.domain;

/**
 * @author chen
 * @date 2020-11-02-22:44
 */
public class ResponseBean<T> {

    private String respCode;
    private String respDesc;
    private T data;

    public ResponseBean(String respCode, String respDesc, T data) {
        this.respCode = respCode;
        this.respDesc = respDesc;
        this.data = data;
    }

    public ResponseBean(String respCode, String respDesc) {
        this.respCode = respCode;
        this.respDesc = respDesc;
        this.data = null;
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
