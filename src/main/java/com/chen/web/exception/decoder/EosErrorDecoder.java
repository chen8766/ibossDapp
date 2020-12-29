package com.chen.web.exception.decoder;

import com.chen.web.exception.EosApiException;
import feign.Response;
import feign.codec.ErrorDecoder;
import feign.jackson.JacksonDecoder;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author chen
 * @date 2020-10-31-17:12
 */
public class EosErrorDecoder implements ErrorDecoder {

    public static final Logger log = LoggerFactory.getLogger(EosErrorDecoder.class);

    private final ErrorDecoder defaultDecoder = new ErrorDecoder.Default();


    @Override
    public Exception decode(String methodKey, Response response) {
        String s = null;
        try {
            s = IOUtils.toString(response.body().asInputStream(), "utf8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return defaultDecoder.decode(methodKey, response);
    }
}
