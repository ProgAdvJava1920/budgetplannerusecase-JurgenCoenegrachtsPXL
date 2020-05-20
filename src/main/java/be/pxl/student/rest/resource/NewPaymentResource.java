package be.pxl.student.rest.resource;

import java.util.Date;

public class NewPaymentResource {
    private float amount;
    private String counterAccount;
    private String currency;
    private Date date;

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getCounterAccount() {
        return counterAccount;
    }

    public void setCounterAccount(String counterAccount) {
        this.counterAccount = counterAccount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
