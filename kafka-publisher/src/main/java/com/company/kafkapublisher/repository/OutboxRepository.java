package com.company.kafkapublisher.repository;

import com.company.kafkapublisher.entity.Outbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

//@Repository // you can not write :)
public interface OutboxRepository extends JpaRepository<Outbox,UUID> {
}
