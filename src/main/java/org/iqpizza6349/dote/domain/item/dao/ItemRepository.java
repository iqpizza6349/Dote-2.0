package org.iqpizza6349.dote.domain.item.dao;

import org.iqpizza6349.dote.domain.item.entity.Item;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends ReactiveMongoRepository<Item, Long> {
}
