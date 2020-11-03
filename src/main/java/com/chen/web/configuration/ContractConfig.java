package com.chen.web.configuration;

import com.chen.web.domain.Contract;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author chen
 * @date 2020-10-31-16:13
 */
@SuppressWarnings("ConfigurationProperties")
@Component
@ConfigurationProperties
public class ContractConfig {

    List<Contract> contracts;

    public Contract getConfigByCodeAndAction(String code, String action) {
        for (Contract contract : contracts) {
            if (contract.getCode().equals(code) && contract.getAction().equals(action)) {
                return contract;
            }
        }
        return null;
    }

    public List<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }
}
