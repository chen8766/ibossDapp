package com.chen.web.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author chen
 * @date 2020-11-12-0:46
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class IpfsServiceTest {

    @Autowired
    private IpfsService ipfsService;

    @Test
    public void pushIpfsAsDag() throws Exception {
    }
}