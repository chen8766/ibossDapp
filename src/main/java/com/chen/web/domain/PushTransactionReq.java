package com.chen.web.domain;

import java.util.List;

/**
 * @author chen
 * @date 2020-10-28-23:19
 */
public class PushTransactionReq {
    private String compression;
    private PackedTransaction transaction;
    private List<String> signatures;

    public PushTransactionReq() {
    }

    public PushTransactionReq(String compression, PackedTransaction transaction, List<String> signatures) {
        this.compression = compression;
        this.transaction = transaction;
        this.signatures = signatures;
    }

    public String getCompression() {
        return compression;
    }

    public void setCompression(String compression) {
        this.compression = compression;
    }

    public PackedTransaction getTransaction() {
        return transaction;
    }

    public void setTransaction(PackedTransaction transaction) {
        this.transaction = transaction;
    }

    public List<String> getSignatures() {
        return signatures;
    }

    public void setSignatures(List<String> signatures) {
        this.signatures = signatures;
    }
}
