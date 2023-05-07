package org.iqpizza6349.dote.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor @NoArgsConstructor
public class LoginRequest {

    @NotBlank(message = "code는 필수 입력값입니다.")
    private String code;

}
