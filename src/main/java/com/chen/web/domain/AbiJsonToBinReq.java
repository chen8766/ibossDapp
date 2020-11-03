package com.chen.web.domain;

/**
 * @author chen
 * @date 2020-10-28-23:04
 */
public class AbiJsonToBinReq {
    /**
     * 合约
     */
    private String code;
    /**
     * action
     */
    private String action;
    /**
     * 合约参数
     */
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
        return "AbiJsonToBinReq{" +
                "code='" + code + '\'' +
                ", action='" + action + '\'' +
                ", args=" + args +
                '}';
    }
}
