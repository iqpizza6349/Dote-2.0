package org.iqpizza6349.dote.domain.member.dao;

import org.iqpizza6349.dote.domain.member.entity.Member;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends ReactiveMongoRepository<Member, String> {
}
