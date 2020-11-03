package com.chen.web.domain;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author chen
 * @date 2020-10-29-0:18
 */
public class TransactionReq {

    private String code;

    private String action;

    private Object args;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Object getArgs() {
        return args;
    }

    public void setArgs(Object args) {
        this.args = args;
    }

    @Override
    public String toString() {
        return "PushTransactionDTO{" +
                "code='" + code + '\'' +
                ", action='" + action + '\'' +
                ", args=" + args +
                '}';
    }
}
