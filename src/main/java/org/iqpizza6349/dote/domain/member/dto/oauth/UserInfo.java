package org.iqpizza6349.dote.domain.member.dto.oauth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor @NoArgsConstructor
public class UserInfo {

    private int grade;
    private int room;
    private int number;
    private String name;

}
