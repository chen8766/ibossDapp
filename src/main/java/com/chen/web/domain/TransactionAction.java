package com.chen.web.domain;

import java.util.List;

/**
 * @author chen
 * @date 2020-10-29-0:47
 */
public class TransactionAction {

    private String account;

    private String name;

    private List<TransactionAuthorization> authorization;

    private String data;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TransactionAuthorization> getAuthorization() {
        return authorization;
    }

    public void setAuthorization(List<TransactionAuthorization> authorization) {
        this.authorization = authorization;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
