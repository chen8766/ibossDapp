package com.chen.web.config;

import com.chen.web.eosapi.ChainApiService;
import com.chen.web.eosapi.IpfsApiService;
import com.chen.web.eosapi.TokenApiService;
import com.chen.web.eosapi.WalletApiService;
import feign.Feign;
import feign.Logger;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import feign.form.FormEncoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import okhttp3.ConnectionPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author chen
 * @date 2020-10-28-23:31
 */
@Configuration
public class FeignClientConfiguration {


    public static final ErrorDecoder.Default DEFAULT_ERROR_DECODER = new ErrorDecoder.Default();
    @Autowired
    private DappConfig dappConfig;

    public static final OkHttpClient OK_HTTP_CLIENT;

    static {
        OK_HTTP_CLIENT = new OkHttpClient(new okhttp3.OkHttpClient.Builder()
                .connectionPool(new ConnectionPool())
                .build());
    }

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
                // .client(OK_HTTP_CLIENT)
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .logger(new Slf4jLogger())
                .logLevel(Logger.Level.FULL)
                .retryer(new Retryer.Default())
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
        // JacksonDecoder对返回的token无法解析
        return Feign.builder()
                .encoder(new JacksonEncoder())
                .logger(new Slf4jLogger())
                .logLevel(Logger.Level.FULL)
                .errorDecoder(new ErrorDecoder.Default())
                .target(TokenApiService.class, dappConfig.getTokenUrl());
    }
}
