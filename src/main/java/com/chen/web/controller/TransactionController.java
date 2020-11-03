package com.chen.web.controller;

import com.chen.web.domain.ResultStatus;
import com.chen.web.domain.*;
import com.chen.web.exception.EosApiException;
import com.chen.web.service.PushTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author chen
 * @date 2020-10-31-17:52
 */
@RestController
@RequestMapping("/v1/iboss/dapp")
public class TransactionController {

    @Autowired
    private PushTransactionService pushTransactionService;

    @PostMapping("/push_transaction")
    public ResponseBean<PushedTransaction> pushTransaction(@RequestBody @Valid TransactionReq transactionReq,
                                                           BindingResult bindingResult) {
        try {
            for (ObjectError error : bindingResult.getAllErrors()) {
                throw new IllegalArgumentException(error.getDefaultMessage());
            }
            validateTransactionReq(transactionReq);
            PushedTransaction pushedTransaction = pushTransactionService.pushTransaction(transactionReq);
            return new ResponseBean<>(ResultStatus.PUSH_SUCCESS, ResultStatus.PUSH_SUCCESS_DESC, pushedTransaction);
        } catch (EosApiException e) {
            return new ResponseBean<>(ResultStatus.PUSH_FAILURE, e.getFormatErrorMsg());
        } catch (Exception e) {
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