package org.iqpizza6349.dote.domain.member.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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

}
