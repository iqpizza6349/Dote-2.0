package org.iqpizza6349.dote.domain.item.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Definition about Item Document
 * Item document is used by Vote document like..
 * <pre>
 *     List<Item> items = new ArrayList<>();
 * </pre>
 * Item properties is next below...
 * <p>
 *     id               -> primary key that generate by 'seq' collection
 *     seq              -> remember primary key('id'), which is make auto_increment
 *     name             -> item's name
 *     number_of_votes  -> literal number of votes
 * </p>
 */
@Getter
@Builder
@ToString
@Document(collection = "item")
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {

    @Id
    private Long id;

    private Long seq;

    private String name;

    @Field(name = "number_of_votes")
    private String numberOfVotes;

}
