package com.chen.web.domain;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.NonNull;

/**
 * @author chen
 * @date 2020-10-29-0:18
 */
public class TransactionReq {

    private String code;

    private String action;

    private ObjectNode args;

    private Contract contract;

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

    public ObjectNode getArgs() {
        return args;
    }

    public void setArgs(ObjectNode args) {
        this.args = args;
    }

    @NonNull
    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    @Override
    public String toString() {
        return "TransactionReq{" +
                "code='" + code + '\'' +
                ", action='" + action + '\'' +
                ", args=" + args +
                ", contract=" + contract +
                '}';
    }
}
