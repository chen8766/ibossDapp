package com.chen.web.domain;

/**
 * @author chen
 * @date 2020-10-29-0:45
 */
public class TransactionAuthorization {
    private String actor;

    private String permission;

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
