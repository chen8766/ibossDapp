package com.chen.web.exception.decoder;

import com.chen.web.exception.EosApiException;
import feign.Response;
import feign.codec.ErrorDecoder;
import feign.jackson.JacksonDecoder;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

/**
 * @author chen
 * @date 2020-10-31-17:12
 */
public class EosErrorDecoder implements ErrorDecoder {


    private final ErrorDecoder defaultDecoder = new ErrorDecoder.Default();


    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            response = response.toBuilder().status(200).build();
            Reader reader = response.body().asReader(StandardCharsets.UTF_8);
            EosApiException eosApiException = (EosApiException) new JacksonDecoder().decode(response, EosApiException.class);
            eosApiException.setMethodKey(methodKey);
            return eosApiException;
        } catch (IOException e) {
            return defaultDecoder.decode(methodKey, response);
        }
    }
}
