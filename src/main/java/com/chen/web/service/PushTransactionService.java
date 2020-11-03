package com.chen.web.service;

import com.chen.web.configuration.ContractConfig;
import com.chen.web.configuration.DappConfig;
import com.chen.web.domain.*;
import com.chen.web.eosapi.ChainApiService;
import com.chen.web.eosapi.WalletApiService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author chen
 * @date 2020-10-29-0:07
 */
@Service
public class PushTransactionService {

    @Autowired
    private ChainApiService chainApiService;

    @Autowired
    private WalletApiService walletApiService;

    @Autowired
    private DappConfig dappConfig;

    @Autowired
    private ContractConfig contractConfig;

    public PushedTransaction pushTransaction(TransactionReq transactionReq) {
        Contract contract = contractConfig.getConfigByCodeAndAction(transactionReq.getCode(), transactionReq.getAction());
        // 1.请求参数序列化
        AbiJsonToBinReq abiJsonToBinReq = new AbiJsonToBinReq();
        BeanUtils.copyProperties(transactionReq, abiJsonToBinReq);
        AbiJsonToBinRsp data = chainApiService.abiJsonToBin(abiJsonToBinReq);

        // 2.获取链信息
        ChainInfo chainInfo = chainApiService.getInfo();
        String headBlockNum = chainInfo.getHeadBlockNum();
        // 3.获取最新区块信息
        Block block = chainApiService.getBlockById(headBlockNum);
        // 4.打开钱包
        walletApiService.openWallet(dappConfig.getWalletName());
        // 5.解锁钱包
        walletApiService.unlockWallet(Arrays.asList(dappConfig.getWalletName(), dappConfig.getWalletPassword()));
        // 6.交易签名
        // 6.1 创建授权
        TransactionAuthorization authorization = new TransactionAuthorization();
        authorization.setActor(contract.getAccount());
        authorization.setPermission(contract.getPermission());
        // 6.2 创建交易action
        TransactionAction transactionAction = new TransactionAction();
        transactionAction.setAccount(transactionReq.getCode());
        transactionAction.setData(data.getBinargs());
        transactionAction.setName(transactionReq.getAction());
        transactionAction.setAuthorization(Collections.singletonList(authorization));
        // 6.3 创建交易
        PackedTransaction packedTransaction = new PackedTransaction();
        packedTransaction.setActions(Collections.singletonList(transactionAction));
        packedTransaction.setExpiration(ZonedDateTime.now(ZoneId.of("UTC")).plusMinutes(3).toLocalDateTime().toString());
        packedTransaction.setRefBlockNum(headBlockNum);
        packedTransaction.setRefBlockPrefix(block.getRefBlockPrefix());

        // 6.4 交易签名
        SignedPackedTransaction signedPackedTransaction =
                walletApiService.signTransaction(Arrays.asList(packedTransaction,
                Collections.singletonList(contract.getEosPublicKey()), chainInfo.getChainId()));
        // 7.交易上链
        return chainApiService.pushTransaction(new PushTransactionReq("none", packedTransaction, signedPackedTransaction.getSignatures()));
    }
}
