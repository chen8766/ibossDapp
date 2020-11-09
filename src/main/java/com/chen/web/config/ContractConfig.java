package com.chen.web.config;

import com.chen.web.domain.Contract;
import com.chen.web.eosapi.WalletApiService;
import com.chen.web.exception.EosApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author chen
 * @date 2020-10-31-16:13
 */
@SuppressWarnings("ConfigurationProperties")
@Component
@ConfigurationProperties
@Slf4j
public class ContractConfig {

    @Autowired
    private WalletApiService walletApiService;

    @Autowired
    private DappConfig dappConfig;

    List<Contract> contracts;

    public Contract getConfigByCodeAndAction(String code, String action) {
        for (Contract contract : contracts) {
            if (contract.getCode().equals(code) && contract.getAction().equals(action)) {
                return contract;
            }
        }
        return null;
    }

    @PostConstruct
    public void importKeys() {
        try {
            walletApiService.openWallet(dappConfig.getWalletName());
            walletApiService.unlockWallet(Arrays.asList(dappConfig.getWalletName(), dappConfig.getWalletPassword()));

            List<List<String>> keys = walletApiService.listKeys(Arrays.asList(dappConfig.getWalletName(), dappConfig.getWalletPassword()));
            // index = 1 处为私钥
            Set<String> privateKeys = keys.stream().map(strings -> strings.get(1)).collect(Collectors.toSet());
            for (Contract contract : contracts) {
                String eosPrivateKey = contract.getEosPrivateKey();
                if (StringUtils.isEmpty(eosPrivateKey) || privateKeys.contains(eosPrivateKey)) {
                    continue;
                }
                // 导入私钥
                log.info("Import the private key: {}", eosPrivateKey);
                walletApiService.importKey(Arrays.asList(dappConfig.getWalletName(), eosPrivateKey));
            }
        } catch (EosApiException e) {
            log.error("Exception at importKeys, {}", e.getFormatErrorMsg(), e);
        } catch (Exception e) {
            log.error("Exception at importKeys, {}", e.getMessage(), e);
        }
    }

    public List<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }
}
