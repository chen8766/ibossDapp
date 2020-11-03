package com.chen.web.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.List;

/**
 * @author chen
 * @date 2020-10-28-23:22
 */

public class SignTransactionReq {

    private PackedTransaction transaction;

    private List<String> publicKeys;

    private String chain;

    public SignTransactionReq() {
    }

    public SignTransactionReq(PackedTransaction transaction, List<String> publicKeys, String chain) {
        this.transaction = transaction;
        this.publicKeys = publicKeys;
        this.chain = chain;
    }

    public PackedTransaction getTransaction() {
        return transaction;
    }

    public void setTransaction(PackedTransaction transaction) {
        this.transaction = transaction;
    }

    public List<String> getPublicKeys() {
        return publicKeys;
    }

    public void setPublicKeys(List<String> publicKeys) {
        this.publicKeys = publicKeys;
    }

    public String getChain() {
        return chain;
    }

    public void setChain(String chain) {
        this.chain = chain;
    }
}
