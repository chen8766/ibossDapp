package com.chen.web.domain;

/**
 * @author chen
 * @date 2020-10-31-18:18
 */
public class ConfirmReq {

    private long blockNum;

    private String transactionId;

    public long getBlockNum() {
        return blockNum;
    }

    public void setBlockNum(long blockNum) {
        this.blockNum = blockNum;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public String toString() {
        return "ConfirmReq{" +
                "blockNum=" + blockNum +
                ", transactionId='" + transactionId + '\'' +
                '}';
    }
}
