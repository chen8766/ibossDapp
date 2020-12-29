package com.chen.web.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

/**
 * @author chen
 * @date 2020-10-28-23:24
 */
@JsonFormat(shape = JsonFormat.Shape.ARRAY)
public class SignDigestReq {

    private String digest;

    private String publicKey;

    public SignDigestReq() {
    }

    public SignDigestReq(String digest, String publicKey) {
        this.digest = digest;
        this.publicKey = publicKey;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    @Override
    public String toString() {
        return "SignDigestReq{" +
                "digest='" + digest + '\'' +
                ", publicKey='" + publicKey + '\'' +
                '}';
    }
}
