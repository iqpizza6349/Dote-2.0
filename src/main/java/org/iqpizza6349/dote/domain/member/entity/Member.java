package org.iqpizza6349.dote.domain.member.entity;

import lombok.*;
import org.iqpizza6349.dote.global.exception.BusinessException;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.HttpStatus;

/**
 * Definition about member document
 * member's all-properties is belongs to dodam-oauth
 * collection property is next below..
 * <p>
 *     uniqueId -> which is primary key of dodam oauth
 *     grade    -> which is student's grade. ex) first-grade, second-grade
 *     room     -> which is student's classroom. ex) A-classroom, B-classroom
 *     number   -> which is student's student-id
 *     name     -> student's name
 * </p>
 * if member is not student, grade, room, number will null.
 * as you know grade, room, number is nullable
 */
@Getter
@Builder
@ToString
@EqualsAndHashCode
@Document(collection = "member")
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    private String id;

    private int grade;

    private int room;

    private int number;

    private String name;

    public static class NotFoundException extends BusinessException {
        public NotFoundException() {
            super(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다.");
        }
    }

    public static class UnAuthenticationException extends BusinessException {
        public UnAuthenticationException() {
            super(HttpStatus.UNAUTHORIZED, "토큰이 입력되지 않았습니다.");
        }
    }

    public static class ForbiddenException extends BusinessException {
        public ForbiddenException() {
            super(HttpStatus.FORBIDDEN, "접근할 수 있는 권한이 없습니다.");
        }
    }

    public static class AlreadyExistedException extends BusinessException {
        public AlreadyExistedException() {
            super(HttpStatus.CONFLICT, "이미 가입된 회원입니다.");
        }
    }
}
