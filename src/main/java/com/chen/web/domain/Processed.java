package com.chen.web.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author chen
 * @date 2020-12-27-1:46
 */
public class Processed {

    private Long blockNum;

    private String blockTime;

    public Long getBlockNum() {
        return blockNum;
    }

    @JsonProperty("block_num")
    public void setBlockNum(Long blockNum) {
        this.blockNum = blockNum;
    }

    public String getBlockTime() {
        return blockTime;
    }

    @JsonProperty("block_time")
    public void setBlockTime(String blockTime) {
        this.blockTime = blockTime;
    }
}
