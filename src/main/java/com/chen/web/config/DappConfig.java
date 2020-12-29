package com.chen.web.config;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.security.Security;

/**
 * @author chen
 * @date 2020-10-31-15:01
 */

@Component
@Configuration
@Slf4j
public class DappConfig {

    @Value("${dapp.serviceUrl.chainUrl}")
    private String chainUrl;
    @Value("${dapp.serviceUrl.walletUrl}")
    private String walletUrl;
    @Value("${dapp.serviceUrl.ipfsUrl}")
    private String ipfsUrl;
    @Value("${dapp.serviceUrl.tokenUrl}")
    private String tokenUrl;

    @Value("${dapp.wallet.name}")
    private String walletName;
    @Value("${dapp.wallet.password}")
    private String walletPassword;
    @Value("${dapp.wallet.unlockTime}")
    private String walletUnlockTime;

    static {
        try {
            Security.insertProviderAt(new BouncyCastleProvider(), 1);
        } catch (Exception e) {
            log.error("注册BouncyCastleProvider异常 {}", e.getMessage(), e);
        }
    }

    public String getChainUrl() {
        return chainUrl;
    }

    public String getWalletUrl() {
        return walletUrl;
    }

    public String getIpfsUrl() {
        return ipfsUrl;
    }

    public String getTokenUrl() {
        return tokenUrl;
    }

    public String getWalletName() {
        return walletName;
    }

    public String getWalletPassword() {
        return walletPassword;
    }

    public String getWalletUnlockTime() {
        return walletUnlockTime;
    }
}
