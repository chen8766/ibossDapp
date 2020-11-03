package com.chen;

import com.chen.web.domain.account.Account;
import com.chen.web.domain.account.Permission;
import com.chen.web.eosapi.ChainApiService;
import com.chen.web.exception.EosBusinessException;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.junit.Test;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Map;

public class MyTest {

    @Test
    public void test() {
        try {
            Map<String, String> request = Collections.singletonMap("account_name", "eosio");
            ChainApiService apiService = Feign.builder()
                    .encoder(new JacksonEncoder())
                    .decoder(new JacksonDecoder())
                    .target(ChainApiService.class, "http://192.168.115.113:8888/v1");
            Account account = apiService.getAccount(request);
            String key = null;
            for (Permission permission : account.getPermissions()) {
                if ("default".equals(permission.getPermName())) {
                    key = permission.getRequiredAuth().getKeys().get(0).getKey();
                    break;
                }
            }
            if (StringUtils.isEmpty(key)) {
                throw new EosBusinessException("获取的eosio账号的default公钥为空");
            }
            System.out.println("获取eosio账号的default公钥为：" + key);
        } catch (EosBusinessException e) {
            e.printStackTrace();
        }
    }
}
