package com.chen.web.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author chen
 * @date 2020-10-28-23:19
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PushedTransaction {
    private String transactionId;
    private Processed processed;

    public Processed getProcessed() {
        return processed;
    }

    @JsonProperty("processed")
    public void setProcessed(Processed processed) {
        this.processed = processed;
    }

    public String getTransactionId() {
        return transactionId;
    }

    @JsonProperty("transaction_id")
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
