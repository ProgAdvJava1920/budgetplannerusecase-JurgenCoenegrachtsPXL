package be.pxl.student.entity.jpa;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.DAO;
import be.pxl.student.entity.exception.AccountException;

import javax.persistence.*;
import java.util.List;

// normaal gezien moet dit AccountDAO noemen
public class AccountJPA implements DAO<Account, AccountException> {
    EntityManager entityManager;

    public AccountJPA(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Account create(Account account) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(account);

        transaction.commit();
        return account;
    }

    @Override
    public Account getById(int id) {
        return entityManager.find(Account.class, id);
    }

    @Override
    public List<Account> getAll() {
        TypedQuery<Account> query = entityManager.createNamedQuery("account.findAll", Account.class);
        return query.getResultList();
    }

    @Override
    public Account update(Account account) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.merge(account);
        //entityManager.persist(account);
        transaction.commit();

        return account;
    }

    @Override
    public Account delete(Account account) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Account attachedAccount = entityManager.find(Account.class, account.getId());
        entityManager.remove(attachedAccount);

        transaction.commit();
        return attachedAccount;

        // zorgt voor error: removing a detached instance
        //entityManager.remove(account);
        //return account;
    }
}
