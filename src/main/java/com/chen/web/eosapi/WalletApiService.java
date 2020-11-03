package com.chen.web.eosapi;

import com.chen.web.domain.*;
import feign.Headers;
import feign.RequestLine;

import java.util.List;

/**
 * @author chen
 * @date 2020-10-28-22:40
 */
public interface WalletApiService {

    /**
     * 签名
     * @param signDigestReq 签名请求参数
     * @return
     */
    @Headers("Content-Type: application/json")
    @RequestLine("POST /wallet/sign_digest")
    SignDigestRsp signDigest(SignDigestReq signDigestReq);

    /**
     * 交易签名
     */
    @RequestLine("POST /wallet/sign_transaction")
    @Headers("Content-Type: application/json")
    SignedPackedTransaction signTransaction(List<Object> list);

    /**
     * 打开钱包
     * @param walletName
     */
    @RequestLine("GET /wallet/open")
    void openWallet(String walletName);

    /**
     * 解锁钱包
     * @param requestFields [walletName, walletPassword]
     */
    @Headers("Content-Type: application/json")
    @RequestLine("POST /wallet/unlock")
    void unlockWallet(List<String> requestFields);
}
