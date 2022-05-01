package com.finnegan.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecurringTransactionRepository extends CrudRepository<RecurringTransaction, Long> {
    List<RecurringTransaction> findRecurringTransactionByOwner(User owner);
}
