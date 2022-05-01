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
    private Date start;

    // end is nullable for recurring transactions without an end date
    @Column()
    private Date end;

    @Column(columnDefinition = "ENUM('DAILY', 'WEEKLY', 'MONTHLY', " +
            "'QUARTERLY')",
            nullable = false)
    @Enumerated(EnumType.STRING)
    private RepetitionCycle cycle;

    public RecurringTransaction(User owner, double amount,
                                String category, String note, Date date,
                                Date start, Date end, RepetitionCycle cycle) {
        super(amount, category, note, date);
        this.owner = owner;
        this.start = start;
        this.end = end;
        this.cycle = cycle;
    }

    public RecurringTransaction() {
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
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
                ", start=" + start +
                ", end=" + end +
                ", cycle=" + cycle +
                "} " + super.toString();
    }
}
