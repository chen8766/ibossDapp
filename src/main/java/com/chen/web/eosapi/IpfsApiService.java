package com.chen.web.eosapi;

import com.fasterxml.jackson.databind.node.ObjectNode;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.form.FormData;

/**
 * @author chen
 * @date 2020-10-28-22:40
 */
public interface IpfsApiService {

    /**
     * 将输入存储为DAG对象
     * @param file 文件
     * @param token token
     * @param chainName 链id
     * @return 存储文件的哈希值
     */
    @Headers({"Content-Type: application/json", "Authorization: Bearer {token}", "chainName: {chainName}"})
    @RequestLine("POST /v0/object/put?pin=true&datafieldenc=base64")
    ObjectNode putObject(@Param("file")FormData file, @Param("token") String token, @Param("chainName") String chainName);
}
