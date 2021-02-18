package com.chen.web.service;

import com.chen.web.config.DappConfig;
import com.chen.web.domain.*;
import com.chen.web.eosapi.ChainApiService;
import com.chen.web.eosapi.WalletApiService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * @author chen
 * @date 2020-10-29-0:07
 */
@Service
public class TransactionService {

    @Autowired
    private ChainApiService chainApiService;

    @Autowired
    private WalletApiService walletApiService;

    @Autowired
    private DappConfig dappConfig;

    @Autowired
    private IpfsService ipfsService;

    public PushedTransaction pushTransaction(TransactionReq transactionReq) throws Exception {
        transactionReq = ipfsService.pushIpfsAsDag(transactionReq);

        // 1.请求参数序列化
        AbiJsonToBinReq abiJsonToBinReq = new AbiJsonToBinReq();
        BeanUtils.copyProperties(transactionReq, abiJsonToBinReq);
        AbiJsonToBinRsp data = chainApiService.abiJsonToBin(abiJsonToBinReq);

        // 2.获取链信息
        ChainInfo chainInfo = chainApiService.getInfo2();
        String irreversibleBlockNum = chainInfo.getLastIrreversibleBlockNum();
        // 3.获取最新区块信息
        Block block = chainApiService.getBlockById2(irreversibleBlockNum);
        // 6.交易签名
        // 6.1 创建授权
        TransactionAuthorization authorization = new TransactionAuthorization();
        authorization.setActor(transactionReq.getContract().getAccount());
        authorization.setPermission(transactionReq.getContract().getPermission());
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
        packedTransaction.setRefBlockNum(irreversibleBlockNum);
        packedTransaction.setRefBlockPrefix(block.getRefBlockPrefix());

        // 6.4 交易签名
        SignedPackedTransaction signedPackedTransaction =
                walletApiService.signTransaction(Arrays.asList(packedTransaction,
                        Collections.singletonList(transactionReq.getContract().getEosPublicKey()), chainInfo.getChainId()));
        // 7.交易上链
        return chainApiService.pushTransaction(new PushTransactionReq("none", packedTransaction, signedPackedTransaction.getSignatures()));
    }


    public Map<String, String> confirmTransaction(ConfirmReq confirmReq) {
        ChainInfo info = chainApiService.getInfo2();
        String lastIrreversibleBlockNum = info.getLastIrreversibleBlockNum();
        HashMap<String, String> result = new HashMap<>();
        if (Long.parseLong(lastIrreversibleBlockNum) < confirmReq.getBlockNum()) {
            result.put("respCode", "10000");
            result.put("respDesc", "交易可逆，请稍后再进行交易确认");
            result.put("block_num", String.valueOf(confirmReq.getBlockNum()));
            result.put("trxId", confirmReq.getTransactionId());
            return result;
        }


        int count = 6;
        long blockNum = confirmReq.getBlockNum();
        for (int i = 0; i < count; i++) {
            Block block;
            if (i == 0) {
                block = chainApiService.getBlockById2(String.valueOf(blockNum));
            } else {
                info = chainApiService.getInfo2();
                if (Long.parseLong(info.getLastIrreversibleBlockNum()) < blockNum) {
                    result.put("respCode", "10000");
                    result.put("respDesc", "交易可逆，请稍后再进行交易确认");
                    result.put("block_num", String.valueOf(confirmReq.getBlockNum()));
                    result.put("trxId", confirmReq.getTransactionId());
                    return result;
                } else {
                    block = chainApiService.getBlockById2(String.valueOf(blockNum));
                }
            }
            for (Transaction transaction : block.getTransactions()) {
                Map<String, String> trx = (Map<String, String>) transaction.getTrx();
                String trxId = trx.get("id");
                if (Objects.equals(confirmReq.getTransactionId(), trxId)) {
                    if (i > 0) {
                        result.put("respCode", "00000");
                        result.put("respDesc", "交易上链成功,存储区块：" + blockNum);
                    } else {
                        result.put("respCode", "00000");
                        result.put("respDesc", "交易上链成功,存储区块");
                    }
                    result.put("block_num", String.valueOf(confirmReq.getBlockNum()));
                    result.put("trxId", confirmReq.getTransactionId());
                    return result;
                }
            }
            blockNum++;
        }

        result.put("respCode", "20000");
        result.put("respDesc", "上链失败，区块:" + confirmReq.getBlockNum() +"中找不到对应的交易id");
        result.put("block_num", String.valueOf(confirmReq.getBlockNum()));
        result.put("trxId", confirmReq.getTransactionId());
        return result;
    }
}
