package com.chen.web.eosapi;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

/**
 * @author chen
 * @date 2020-11-12-23:00
 */
public interface TokenApiService {

    /**
     * 获取token
     * @param digest 自定义明文
     * @param signDigest 签名
     * @param accountName 账号
     * @return token
     */
    @Headers("Content-Type: application/json")
    @RequestLine("POST /v0/token/authorize")
    String authorize(@Param("digest") String digest, @Param("signDigest") String signDigest, @Param("account") String accountName);
}
