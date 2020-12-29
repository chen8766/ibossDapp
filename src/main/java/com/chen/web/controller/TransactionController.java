package com.chen.web.controller;

import com.chen.web.config.ContractConfig;
import com.chen.web.domain.*;
import com.chen.web.exception.EosApiException;
import com.chen.web.service.TransactionService;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private ContractConfig contractConfig;

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
    public String confirmTransaction(@RequestBody ConfirmReq confirmReq) {
        //todo 交易确认
        System.out.println(confirmReq);
        return "";
    }
}