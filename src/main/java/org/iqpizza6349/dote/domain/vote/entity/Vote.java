package org.iqpizza6349.dote.domain.vote.entity;

import lombok.*;
import org.iqpizza6349.dote.domain.item.entity.Item;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Definition of Vote Document
 * this document contains member, and item documents
 * storage member's reference id, also item's reference id
 */
@Getter
@Builder
@ToString
@Document(collection = "vote")
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Vote {

    @Id
    private Long id;

    private Long seq;

    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime until;

    @DBRef
    private List<Item> items;

    public void addItem(Item item) {
        this.items.add(item);
    }

    public void removeItem(Item item) {
        this.items.remove(item);
    }
}
