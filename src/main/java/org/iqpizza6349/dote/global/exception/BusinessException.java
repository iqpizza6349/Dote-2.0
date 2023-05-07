package org.iqpizza6349.dote.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class BusinessException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final String message;
}
