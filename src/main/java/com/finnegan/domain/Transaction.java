package com.finnegan.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private long id;

    // private long user_id; FK
    // private long account_id; FK

    @Column(name = "amount", nullable = false)
    private double amount;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "note")
    private String note;

    // recurringSource FK

    @Column(name = "date", nullable = false)
    private Date date;

    public Transaction(double amount, String category, String note,
                       Date date) {
        this.amount = amount;
        this.category = category;
        this.note = note;
        this.date = date;
    }

    public Transaction() {
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

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", amount=" + amount +
                ", category='" + category + '\'' +
                ", note='" + note + '\'' +
                ", date=" + date +
                '}';
    }
}
