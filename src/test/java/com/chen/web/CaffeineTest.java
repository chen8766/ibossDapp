package com.chen.web;

import com.chen.web.domain.Block;
import com.chen.web.eosapi.ChainApiService;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.AssertionErrors;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @author chen
 * @date 2021-02-18-2:22
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CaffeineTest {

    @Autowired
    private ChainApiService chainApiService;

    @Test
    public void caffeineTest() throws InterruptedException {
        LoadingCache<Object, Block> cache = Caffeine.newBuilder()
                .expireAfterWrite(500, TimeUnit.MILLISECONDS)
                .maximumSize(1000)
                .build(key -> chainApiService.getBlockById2(String.valueOf(key)));

        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Thread thread = new Thread(() -> {
                Block block = null;
                block = cache.get("1029772");
                AssertionErrors.assertTrue("success", block != null);
            });
            threads.add(thread);
        }
        for (Thread thread : threads) {
            thread.start();
        }
        TimeUnit.SECONDS.sleep(2);
    }
}
