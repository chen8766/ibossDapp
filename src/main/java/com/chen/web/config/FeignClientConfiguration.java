package com.chen.web.config;

import com.chen.web.eosapi.ChainApiService;
import com.chen.web.eosapi.IpfsApiService;
import com.chen.web.eosapi.TokenApiService;
import com.chen.web.eosapi.WalletApiService;
import com.chen.web.exception.decoder.EosErrorDecoder;
import feign.Feign;
import feign.Logger;
import feign.Response;
import feign.codec.ErrorDecoder;
import feign.form.FormEncoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author chen
 * @date 2020-10-28-23:31
 */
@Configuration
public class FeignClientConfiguration {


    public static final ErrorDecoder.Default DEFAULT_ERROR_DECODER = new ErrorDecoder.Default();
    @Autowired
    private DappConfig dappConfig;

    @Bean
    public WalletApiService createWalletFeignClient() {
        return Feign.builder()
                .encoder(new JacksonEncoder())
                .logger(new Slf4jLogger())
                .logLevel(Logger.Level.FULL)
                .decoder(new JacksonDecoder())
                .errorDecoder(new ErrorDecoder.Default())
                .target(WalletApiService.class, dappConfig.getWalletUrl());
    }

    @Bean
    public ChainApiService createChainFeignClient() {
        return Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .logger(new Slf4jLogger())
                .logLevel(Logger.Level.BASIC)
                .errorDecoder(new ErrorDecoder.Default())
                .target(ChainApiService.class, dappConfig.getChainUrl());
    }

    @Bean
    public IpfsApiService createIpfsFeignClient() {
        return Feign.builder()
                .encoder(new FormEncoder(new JacksonEncoder()))
                .decoder(new JacksonDecoder())
                .logger(new Slf4jLogger())
                .logLevel(Logger.Level.FULL)
                .errorDecoder(DEFAULT_ERROR_DECODER)
                .target(IpfsApiService.class, dappConfig.getIpfsUrl());
    }

    @Bean
    public TokenApiService createTokenFeignClient() {
        return Feign.builder()
                .encoder(new JacksonEncoder())
                .logger(new Slf4jLogger())
                .logLevel(Logger.Level.FULL)
                .errorDecoder(new ErrorDecoder.Default())
                .target(TokenApiService.class, dappConfig.getTokenUrl());
    }
}
