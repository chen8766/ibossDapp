package com.chen.web.eosapi;

import com.chen.web.domain.*;
import feign.Body;
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


    /**
     * 获取所有密钥
     * @param parameters [钱包名称，钱包密码]
     * @return List<List<String>> 密钥
     */
    @RequestLine("POST /wallet/list_keys")
    List<List<String>> listKeys(List<String> parameters);

    /**
     * 导入私钥
     * @param requestFields [walletName, privateKey]
     */
    @Headers("Content-Type: application/json")
    @RequestLine("POST /wallet/import_key")
    void importKey(List<String> requestFields);
}
