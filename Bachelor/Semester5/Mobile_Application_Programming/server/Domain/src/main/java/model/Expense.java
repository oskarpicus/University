package model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Expense {
    private String id;
    private String name;
    private double sum;
    private ExpenseType type;
    private LocalDateTime date;
    private LocalDateTime dueOn;
    private boolean payed;

    public Expense() {
    }

    public Expense(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public ExpenseType getType() {
        return type;
    }

    public void setType(ExpenseType type) {
        this.type = type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public LocalDateTime getDueOn() {
        return dueOn;
    }

    public void setDueOn(LocalDateTime dueOn) {
        this.dueOn = dueOn;
    }

    public boolean isPayed() {
        return payed;
    }

    public void setPayed(boolean payed) {
        this.payed = payed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expense expense = (Expense) o;
        return Objects.equals(id, expense.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Expense{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", sum=" + sum +
                ", type=" + type +
                ", date=" + date +
                ", dueOn=" + dueOn +
                ", payed=" + payed +
                '}';
    }
}
