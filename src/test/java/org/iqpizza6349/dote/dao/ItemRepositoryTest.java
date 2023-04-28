package org.iqpizza6349.dote.dao;

import org.iqpizza6349.dote.domain.item.dao.ItemRepository;
import org.iqpizza6349.dote.domain.item.entity.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataMongoTest
@DirtiesContext
@TestPropertySource(properties = {"spring.config.location=classpath:application-test.properties"})
class ItemRepositoryTest {

    @Autowired
    private ItemRepository repository;

    @Test
    @DisplayName("아이템 저장")
    void saveItem() {
        // given
        final String itemName = "테스트";
        Item testItem = Item.builder()
                .name(itemName)
                .build();
    
        // when
        Mono<Item> itemMono = repository.save(testItem);
        
        // then
        StepVerifier
                .create(itemMono)
                .assertNext(item -> {
                    assertThat(item).isNotNull();
                    assertThat(item.getName()).isEqualTo(itemName);
                })
                .expectComplete()
                .verify();
    }
    
    @DisplayName("아이템 삭제")
    @Test
    void deleteItem() {
        // given
        final Item fakeItem = new Item("1", "1", 0);

        // when
        Mono<Void> deleteMono = repository.delete(fakeItem);
        
        // then
        StepVerifier
                .create(deleteMono)
                .expectNextCount(0)
                .expectComplete()
                .verify();
    }

    @BeforeEach
    void deleteAll() {
        repository.deleteAll().block();
    }

    @Test
    @DisplayName("득표수 현황(득표수를 기준으로 내림차순 정렬) 조회")
    void selectOrderByDescending() {
        // given
        Item apple = new Item("1", "apple", 10);
        Item koreanPear = new Item("2", "korean pear", 30);
        Item orange = new Item("3", "stupid Orange", 20);
        repository.saveAll(List.of(apple, koreanPear, orange))
                .subscribe();

        // when
        Flux<Item> itemFlux = repository.findAll(Sort.by("numberOfVotes").descending());
        itemFlux.log().subscribe();

        // then
        StepVerifier
                .create(itemFlux)
                .expectNext(koreanPear, orange, apple)
                .expectComplete()
                .log()
                .verify();
    }
}