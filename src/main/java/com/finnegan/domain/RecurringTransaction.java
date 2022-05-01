package com.finnegan.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "recurring_transactions")
public class RecurringTransaction extends TransactionParent {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner", nullable = false)
    private User owner;

    @Column(nullable = false)
    private Date startDate;

    // end is nullable for recurring transactions without an end date
    @Column()
    private Date endDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RepetitionCycle cycle;

    public RecurringTransaction(User owner, double amount,
                                TransactionCategory category, String note, Date date,
                                Date startDate, Date endDate, RepetitionCycle cycle) {
        super(amount, category, note, date);
        this.owner = owner;
        this.startDate = startDate;
        this.endDate = endDate;
        this.cycle = cycle;
    }

    public RecurringTransaction() {
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date start) {
        this.startDate = start;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date end) {
        this.endDate = end;
    }

    public RepetitionCycle getCycle() {
        return cycle;
    }

    public void setCycle(RepetitionCycle cycle) {
        this.cycle = cycle;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "RecurringTransaction{" +
                "owner=" + owner +
                ", start=" + startDate +
                ", end=" + endDate +
                ", cycle=" + cycle +
                "} " + super.toString();
    }
}
