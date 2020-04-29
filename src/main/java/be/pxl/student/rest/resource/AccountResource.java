package be.pxl.student.rest.resource;

import be.pxl.student.entity.Account;

public class AccountResource {
    private int id;
    private String IBAN;
    private String name;

    public AccountResource() {
    }

    public AccountResource(Account account) {
        id = account.getId();
        IBAN = account.getIBAN();
        name = account.getName();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
