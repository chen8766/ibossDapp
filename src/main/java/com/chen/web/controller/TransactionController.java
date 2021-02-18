package com.chen.web.controller;

import com.chen.web.config.ContractConfig;
import com.chen.web.domain.*;
import com.chen.web.eosapi.ChainApiService;
import com.chen.web.service.TransactionService;
import com.chen.web.service.hystrix.HystrixTransactionService;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author chen
 * @date 2020-10-31-17:52
 */
@RestController
@RequestMapping("/v1/iboss/dapp")
@Slf4j
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ChainApiService chainApiService;

    @Autowired
    private ContractConfig contractConfig;

    @Autowired
    private ApplicationContext context;

    @PostMapping("/push_transaction")
    public ResponseBean<PushedTransaction> pushTransaction(@RequestBody TransactionReq transactionReq) {
        try {
            validateTransactionReq(transactionReq);
            transactionReq.setContract(contractConfig.getConfigByCodeAndAction(transactionReq.getCode(), transactionReq.getAction()));
            PushedTransaction pushedTransaction = transactionService.pushTransaction(transactionReq);
            return new ResponseBean<>(ResultStatus.PUSH_SUCCESS, ResultStatus.PUSH_SUCCESS_DESC, pushedTransaction);
        } catch (FeignException e) {
            log.error("接口请求失败: {}", e.contentUTF8(), e);
            return new ResponseBean<>(ResultStatus.PUSH_FAILURE, e.getMessage() + e.contentUTF8());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ResponseBean<>(ResultStatus.PUSH_FAILURE, e.getMessage());
        }
    }

    private void validateTransactionReq(TransactionReq transactionReq) {
        if (StringUtils.isEmpty(transactionReq.getAction())) {
            throw new IllegalArgumentException("action is empty");
        }
        if (StringUtils.isEmpty(transactionReq.getCode())) {
            throw new IllegalArgumentException("code is empty");
        }
        if (StringUtils.isEmpty(transactionReq.getArgs())) {
            throw new IllegalArgumentException("args is empty");
        }
    }

    @PostMapping("/confirm_transaction")
    public Map<String, String> confirmTransaction(@RequestBody ConfirmReq confirmReq) {
        HashMap<String, String> result = new HashMap<>();
        try {
            return transactionService.confirmTransaction(confirmReq);
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.put("respCode", "20000");
        result.put("respDesc", "上链失败");
        result.put("block_num", String.valueOf(confirmReq.getBlockNum()));
        result.put("trxId", confirmReq.getTransactionId());
        return result;
    }

    @PostMapping("/push_transaction2")
    public ResponseBean<PushedTransaction> pushTransaction2(@RequestBody TransactionReq transactionReq) {
        try {
            validateTransactionReq(transactionReq);
            transactionReq.setContract(contractConfig.getConfigByCodeAndAction(transactionReq.getCode(), transactionReq.getAction()));

            HystrixTransactionService hystrixTransactionService = context.getBean(HystrixTransactionService.class);
            hystrixTransactionService.setTransactionReq(transactionReq);
            PushedTransaction pushedTransaction = hystrixTransactionService.execute();

            if (Objects.isNull(pushedTransaction)) {
                return new ResponseBean<>(ResultStatus.PUSH_FAILURE, "服务不可用");
            }
            return new ResponseBean<>(ResultStatus.PUSH_SUCCESS, ResultStatus.PUSH_SUCCESS_DESC, pushedTransaction);
        } catch (FeignException e) {
            log.error("接口请求失败: {}", e.contentUTF8(), e);
            return new ResponseBean<>(ResultStatus.PUSH_FAILURE, e.getMessage() + e.contentUTF8());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ResponseBean<>(ResultStatus.PUSH_FAILURE, e.getMessage());
        }
    }

    @GetMapping("/getBlockById")
    public Block getBlockById(String blockNum) {
        return chainApiService.getBlockById2(blockNum);
    }

    @GetMapping("/getInfo")
    public ChainInfo getInfo() {
        return chainApiService.getInfo2();
    }
}