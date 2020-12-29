package com.chen.web.service;

import com.chen.web.config.ContractConfig;
import com.chen.web.domain.Contract;
import com.chen.web.domain.SignDigestReq;
import com.chen.web.eosapi.TokenApiService;
import com.chen.web.eosapi.WalletApiService;
import com.eos.crypto.util.HexUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author chen
 * @date 2020-11-12-23:09
 */
@Service
public class TokenService {

    @Autowired
    private WalletApiService walletApiService;

    @Autowired
    private TokenApiService tokenApiService;

    public static final String DIGEST = "hello_gmcc";

    public String authorize(String accountName, String eosPublicKey) {
        String signDigest = walletApiService.signDigest(new SignDigestReq(HexUtils.toHex(DIGEST.getBytes()), eosPublicKey));
        return tokenApiService.authorize(DIGEST, signDigest, accountName);
    }

}
