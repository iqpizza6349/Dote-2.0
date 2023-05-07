package org.iqpizza6349.dote.global.constants;

import lombok.Getter;

@Getter
public enum BaseUrl {
    AUTH("https://dauth.b1nd.com/api"),
    OPEN_API("http://open.dodam.b1nd.com/api"),
    TEST("http://localhost:8080")
    ;
    private final String endPoint;

    BaseUrl(String endPoint) {
        this.endPoint = endPoint;
    }
}
