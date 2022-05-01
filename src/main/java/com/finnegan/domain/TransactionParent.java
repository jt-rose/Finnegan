package com.finnegan.domain;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public class TransactionParent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private long id;

    @Column(name = "amount", nullable = false)
    private double amount;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "note")
    private String note;

    @Column(name = "date", nullable = false)
    private Date date;

    public TransactionParent( double amount, String category,
                       String note,
                       Date date) {
        this.amount = amount;
        this.category = category;
        this.note = note;
        this.date = date;
    }

    public TransactionParent() {
    }

    public long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
