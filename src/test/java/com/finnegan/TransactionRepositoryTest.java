package com.finnegan;

import com.finnegan.domain.Transaction;
import com.finnegan.domain.TransactionRespository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class TransactionRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TransactionRespository transactionRepo;

    @Test
    public void saveTransaction() {
        var transaction = new Transaction();
        entityManager.persistAndFlush(transaction);
        assertThat(transaction.getId()).isNotNull();
    }

    @Test
    public void deleteTransactions() {

    }

}
