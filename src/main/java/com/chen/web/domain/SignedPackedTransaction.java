package com.chen.web.domain;

import java.util.List;

/**
 * @author chen
 * @date 2020-10-28-23:22
 */
public class SignedPackedTransaction extends PackedTransaction{

    private List<String> signatures;

    public SignedPackedTransaction() {

    }

    public List<String> getSignatures() {
        return signatures;
    }

    public void setSignatures(List<String> signatures) {
        this.signatures = signatures;
    }

}
