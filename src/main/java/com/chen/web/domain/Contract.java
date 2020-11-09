package com.chen.web.domain;

/**
 * @author chen
 * @date 2020-10-31-15:54
 */
public class Contract {

    private String code;
    private String action;
    private String account;
    private String permission;
    private String eosPublicKey;
    private String eosPrivateKey;
    private String ipfs;
    private String ipfsNode;

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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getEosPublicKey() {
        return eosPublicKey;
    }

    public void setEosPublicKey(String eosPublicKey) {
        this.eosPublicKey = eosPublicKey;
    }

    public String getEosPrivateKey() {
        return eosPrivateKey;
    }

    public void setEosPrivateKey(String eosPrivateKey) {
        this.eosPrivateKey = eosPrivateKey;
    }

    public String getIpfs() {
        return ipfs;
    }

    public void setIpfs(String ipfs) {
        this.ipfs = ipfs;
    }

    public String getIpfsNode() {
        return ipfsNode;
    }

    public void setIpfsNode(String ipfsNode) {
        this.ipfsNode = ipfsNode;
    }
}
