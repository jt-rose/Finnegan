package com.finnegan.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import java.util.List;

public interface TransactionRepository extends PagingAndSortingRepository <Transaction,
        Long> {

    List<Transaction> findByOwner(User owner);
    List<Transaction> findByCategory(TransactionCategory category);

    List<Transaction> findByAmountOrCategoryOrderByAmountDesc(double amount,
                                                              TransactionCategory category);

    Page<Transaction> findByOwnerOrderByDateDesc(User owner, Pageable pageable);

    @Query("SELECT t FROM Transaction t WHERE t.amount > ?1 ORDER BY t.amount")
    List<Transaction> findByAmount(double amount);

    @Query("SELECT sum(t.amount) FROM Transaction t WHERE t.owner = ?1")
    double findAccountSum(User owner);
}
