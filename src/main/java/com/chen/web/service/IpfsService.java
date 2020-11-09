package com.chen.web.service;

import com.chen.web.domain.account.Account;
import com.chen.web.domain.account.Permission;
import com.chen.web.eosapi.ChainApiService;
import com.cmcc.eos.crypto.ec.EosPublicKey;
import com.cmcc.eos.crypto.util.ECCUtil;
import org.bouncycastle.jce.interfaces.ECPublicKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author chen
 * @date 2020-11-10-0:03
 */
@Service
public class IpfsService {

    @Autowired
    private ChainApiService chainApiService;

    @Autowired
    private


    public void pushIpfsAsDag() throws Exception {
        //1.获取加密账号公钥
        Account account = chainApiService.getAccount("eosio.token");
        String key = null;
        for (Permission permission : account.getPermissions()) {
            if (Objects.equals("default", permission.getPermName())) {
                key = permission.getRequiredAuth().getKeys().get(0).getKey();
                break;
            }
        }
        EosPublicKey eosPublicKey = new EosPublicKey(Objects.requireNonNull(key));
        ECPublicKey ecPublicKey = eosPublicKey.getECPublicKey();
        //2.加密
        String plainData = "hello, nihao";

        byte[] encrypt = ECCUtil.publicEncrypt(plainData.getBytes(StandardCharsets.UTF_8), ecPublicKey);
        String encryptedStr = Base64.getEncoder().encodeToString(encrypt);
        //3.推送到ipfs

    }

}
