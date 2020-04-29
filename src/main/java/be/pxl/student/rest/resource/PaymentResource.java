package be.pxl.student.rest.resource;

import be.pxl.student.entity.Payment;

import java.util.Date;

public class PaymentResource {
    private int id;                 // primary key
    private AccountResource account;          // foreign key
    private AccountResource counterAccount;   // foreign key
    private Date date;
    private float amount;
    private String currency;
    private String detail;

    public PaymentResource() {
    }

    public PaymentResource(Payment payment) {
        id = payment.getId();
        account = new AccountResource(payment.getAccount());
        counterAccount = new AccountResource(payment.getCounterAccount());
        date = payment.getDate();
        amount = payment.getAmount();
        currency = payment.getCurrency();
        detail = payment.getDetail();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AccountResource getAccount() {
        return account;
    }

    public void setAccount(AccountResource account) {
        this.account = account;
    }

    public AccountResource getCounterAccount() {
        return counterAccount;
    }

    public void setCounterAccount(AccountResource counterAccount) {
        this.counterAccount = counterAccount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
