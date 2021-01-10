package com.chen.web.eosapi;

import com.chen.web.domain.*;
import com.chen.web.domain.account.Account;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

/**
 * @author chen
 * @date 2020-10-28-22:40
 */
public interface ChainApiService {

    /**
     * 获取最新区块信息
     * @return 最新区块信息
     */
    @RequestLine("GET /chain/get_info")
    ChainInfo getInfo();

    /**
     * 序列化请求参数
     * @param req json格式请求参数
     * @return 序列化的请求参数
     */
    @Headers("Content-Type: application/json")
    @RequestLine("POST /chain/abi_json_to_bin")
    AbiJsonToBinRsp abiJsonToBin(AbiJsonToBinReq req);

    /**
     * 获取区块信息
     * @param blocNumOrId 区块编号
     * @return 区块信息
     */
    @Headers("Content-Type: application/json")
    @RequestLine("POST /chain/get_block")
    Block getBlockById(@Param("block_num_or_id") String blocNumOrId);

    /**
     * 数据上链
     * @param pushTransactionReq 上链请求参数
     * @return 返回上链的区块信息
     */
    @Headers("Content-Type: application/json")
    @RequestLine("POST /chain/push_transaction")
    PushedTransaction pushTransaction(PushTransactionReq pushTransactionReq);

    @Headers("Content-Type: application/json")
    @RequestLine("POST /chain/get_account")
    Account getAccount(@Param("account_name") String accountName);
}
