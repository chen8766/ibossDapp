package com.chen.web.service.hystrix;

import com.chen.web.domain.PushedTransaction;
import com.chen.web.domain.TransactionReq;
import com.chen.web.service.TransactionService;
import com.netflix.config.ConfigurationManager;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author chen
 * @date 2021-01-20-0:04
 */
@Component
@Scope("prototype")
public class HystrixTransactionService extends HystrixCommand<PushedTransaction> {

    public static final Setter cachedSetter = Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("transactionServiceGroup"))
            .andCommandKey(HystrixCommandKey.Factory.asKey("hystrixTransactionService"));
    @Autowired
    private TransactionService transactionService;

    private TransactionReq transactionReq;

    public HystrixTransactionService() {
        super(cachedSetter);
    }

    @Override
    protected PushedTransaction run() throws Exception {
        // HystrixBadRequestException 不会被作为异常统计
        return transactionService.pushTransaction(transactionReq);
    }

    @Override
    protected PushedTransaction getFallback() {
        return null;
    }

    public void setTransactionReq(TransactionReq transactionReq) {
        this.transactionReq = transactionReq;
    }
}
