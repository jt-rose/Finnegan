package com.finnegan.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionRespository extends CrudRepository <Transaction,
        Long> {

    List<Transaction> findByCategory(String Category);

    List<Transaction> findByAmountOrCategoryOrderByAmountDesc(double amount,
                                                             String category);

    @Query("SELECT t FROM Transaction t WHERE t.amount > ?1 ORDER BY t.amount")
    List<Transaction> findByAmount(double amount);

}
