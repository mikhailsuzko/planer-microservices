package com.sma.micro.planner.todo.feign;

import com.google.common.io.CharStreams;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.charset.Charset;


@Slf4j
@Service
public class FeignExceptionHandler implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        if (response.status() == 406) {
            return new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, readMessage(response));
        }
        return null;
    }

    private String readMessage(Response response) {
        try (var reader = response.body().asReader(Charset.defaultCharset())){
            return CharStreams.toString(reader);

        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return "Error 406";
    }
}
