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
public interface IpfsApiService {
    @Headers("Content-Type: application/json")
    @RequestLine("POST /")
    Account getAccount(@Param("account_name") String accountName);
}
