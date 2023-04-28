package org.iqpizza6349.dote.dao;

import org.iqpizza6349.dote.domain.member.dao.MemberRepository;
import org.iqpizza6349.dote.domain.member.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.*;

@DataMongoTest
@DirtiesContext
@TestPropertySource(properties = {"spring.config.location=classpath:application-test.properties"})
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository repository;

    @DisplayName("회원 도큐먼트에 객체 삽입")
    @Test
    void insertMember() {
        // given
        final String name = "iqpizza6349";
        Member iqpizza6349 = Member.builder()
                .grade(3)
                .room(2)
                .number(16)
                .name(name)
                .build();

        // when
        Mono<Member> memberMono = repository.save(iqpizza6349);

        // then
        StepVerifier
                .create(memberMono)
                .assertNext(member -> {
                    assertThat(member).isNotNull();
                    assertThat(member.getGrade()).isEqualTo(3);
                    assertThat(member.getRoom()).isEqualTo(2);
                    assertThat(member.getNumber()).isEqualTo(16);
                    assertThat(member.getName()).isEqualTo(name);
                })
                .expectComplete()
                .log()
                .verify();
    }

    @DisplayName("회원 도큐먼트에서 객체 조회")
    @Test
    void selectMember() {
        // given
        final String fakeId = "1234567";

        // when
        Mono<Member> memberMono = repository.findById(fakeId);

        // then
        StepVerifier
                .create(memberMono)
                .expectNextCount(0)
                .expectComplete()
                .log()
                .verify();
    }

    @DisplayName("회원 도큐먼트에서 컬렉션 삭제")
    @Test
    void removeMember() {
        // given
        final Member fakeMember = new Member("1", 1, 1, 1, "test");

        // when
        Mono<Void> deleteMono = repository.delete(fakeMember);

        // then
        StepVerifier
                .create(deleteMono)
                .expectNextCount(0)
                .expectComplete()
                .log()
                .verify();
    }
}
