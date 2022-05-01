package com.finnegan.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "transactions")
public class Transaction extends TransactionParent {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner", nullable = false)
    private User owner;

    public Transaction(User owner, double amount, TransactionCategory category,
                       String note, Date date) {
        super(amount, category, note, date);
        this.owner = owner;
    }

    public Transaction() {
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Transaction{} " + super.toString();
    }
}
