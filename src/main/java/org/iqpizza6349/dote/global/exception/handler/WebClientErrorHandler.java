package org.iqpizza6349.dote.global.exception.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class WebClientErrorHandler implements ErrorWebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse httpResponse = exchange.getResponse();
        setResponseStatus(httpResponse, ex);
        return httpResponse.writeWith(Mono.fromSupplier(() -> {
            DataBufferFactory bufferFactory = httpResponse.bufferFactory();
            try {
                //Not displaying any error msg to client for internal server error
                String errMsgToSend = (httpResponse.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) ? "" : ex.getMessage();
                return bufferFactory.wrap(new ObjectMapper().writeValueAsBytes(errMsgToSend));
            } catch (JsonProcessingException e) {
                return bufferFactory.wrap(new byte[0]);
            }
        }));
    }

    private void setResponseStatus(ServerHttpResponse httpResponse, Throwable ex) {
        ex.printStackTrace();
        httpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
