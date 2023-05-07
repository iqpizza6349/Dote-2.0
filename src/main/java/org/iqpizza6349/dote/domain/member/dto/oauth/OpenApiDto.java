package org.iqpizza6349.dote.domain.member.dto.oauth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@AllArgsConstructor @NoArgsConstructor
public class OpenApiDto implements Serializable {

    private String message;

    @JsonProperty("data")
    private UserInfo info;
}
