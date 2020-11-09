package com.chen.web.config;

import com.chen.web.eosapi.ChainApiService;
import com.chen.web.eosapi.WalletApiService;
import com.chen.web.exception.decoder.EosErrorDecoder;
import feign.Feign;
import feign.Logger;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chen
 * @date 2020-10-28-23:31
 */
@Configuration
public class FeignClientConfiguration {


    @Autowired
    private DappConfig dappConfig;

    @Bean
    public WalletApiService createWalletFeignClient() {
        return Feign.builder()
                .encoder(new JacksonEncoder())
                .logger(new Slf4jLogger())
                .logLevel(Logger.Level.FULL)
                .decoder(new JacksonDecoder())
                .errorDecoder(new EosErrorDecoder())
                .target(WalletApiService.class, dappConfig.getWalletUrl());
    }

    @Bean
    public ChainApiService createChainFeignClient() {
        return Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .logger(new Slf4jLogger())
                .logLevel(Logger.Level.FULL)
                .errorDecoder(new EosErrorDecoder())
                .target(ChainApiService.class, dappConfig.getChainUrl());
    }
}
