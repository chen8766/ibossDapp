package com.chen.web.service;

import com.chen.web.domain.Contract;
import com.chen.web.domain.TransactionReq;
import com.chen.web.domain.account.Account;
import com.chen.web.domain.account.Permission;
import com.chen.web.eosapi.ChainApiService;
import com.chen.web.eosapi.IpfsApiService;
import com.chen.web.util.DateUtils;
import com.eos.crypto.ec.EosPublicKey;
import com.eos.crypto.util.ECCUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import feign.form.FormData;
import org.bouncycastle.jce.interfaces.ECPublicKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Collections;
import java.util.Objects;

/**
 * @author chen
 * @date 2020-11-10-0:03
 */
@Service
public class IpfsService {

    public static final String APPLICATION_OCTET_STREAM = "application/octet-stream";
    public static final String SEPARATOR = "_";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private ChainApiService chainApiService;

    @Autowired
    private IpfsApiService ipfsApiService;

    @Autowired
    private TokenService tokenService;

    public TransactionReq pushIpfsAsDag(TransactionReq transactionReq) throws Exception {
        //1.获取加密账号公钥
        Contract contract = transactionReq.getContract();
        if (!Boolean.parseBoolean(contract.getIpfs())) {
            return transactionReq;
        }
        Account account = chainApiService.getAccount(contract.getAccount());
        String key = null;
        for (Permission permission : account.getPermissions()) {
            if (Objects.equals("active", permission.getPermName())) {
                key = permission.getRequiredAuth().getKeys().get(0).getKey();
                break;
            }
        }
        EosPublicKey eosPublicKey = new EosPublicKey(Objects.requireNonNull(key, "加密公钥为空"));
        ECPublicKey ecPublicKey = eosPublicKey.getECPublicKey();
        //2.加密
        ObjectNode argsNode = transactionReq.getArgs();
        String content = argsNode.get("content").asText();
        byte[] encrypt = ECCUtil.publicEncrypt(content.getBytes(StandardCharsets.UTF_8), ecPublicKey);
        String encryptedStr = Base64.getEncoder().encodeToString(encrypt);

        //3.获取token
        String token = tokenService.authorize(contract.getAccount(), contract.getEosPublicKey());

        //4.推送到ipfs
        String data = objectMapper.writeValueAsString(Collections.singletonMap("Data", encryptedStr));
        FormData formData = new FormData(APPLICATION_OCTET_STREAM,
                transactionReq.getCode() + SEPARATOR + contract.getAccount() + SEPARATOR + DateUtils.getCurrentDateTime(),
                data.getBytes(StandardCharsets.UTF_8));
        ObjectNode jsonNodes = ipfsApiService.putObject(formData, token, chainApiService.getInfo().getChainId());
        String hash = jsonNodes.get("hash").asText();

        argsNode.remove("content");
        argsNode.put("memo", hash);
        return transactionReq;
    }

}
