package com.finnegan;

import com.finnegan.domain.Transaction;
import com.finnegan.domain.TransactionRespository;
import com.finnegan.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class TransactionRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private TransactionRespository transactionRepo;

    @Test
    public void saveTransaction() {
        var user = userRepo.findByUsername("user");
        var transaction = new Transaction(user, 300.00, "GROCERIES", "Some " +
                "note", new Date());
        entityManager.persistAndFlush(transaction);
        assertThat(transaction.getId()).isNotNull();
    }

    @Test
    public void deleteTransactions() {
        var user = userRepo.findByUsername("user");
        var transaction1 = new Transaction(user, 300.00, "GROCERIES", "Some " +
                "note", new Date());
        var transaction2 = new Transaction(user, 600.00, "GROCERIES", "Some " +
                "note", new Date());
        entityManager.persistAndFlush(transaction1);
        entityManager.persistAndFlush(transaction2);

        // delete entities
        transactionRepo.deleteAll();
        assertThat(transactionRepo.findAll()).isEmpty();
    }

}
