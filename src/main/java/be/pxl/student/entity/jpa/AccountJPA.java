package be.pxl.student.entity.jpa;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.DAO;
import be.pxl.student.entity.exception.AccountException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

// normaal gezien moet dit AccountDAO noemen
public class AccountJPA implements DAO<Account, AccountException> {
    EntityManager entityManager;

    public AccountJPA(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Account create(Account account) {
        entityManager.getTransaction().begin();
        entityManager.persist(account);
        entityManager.getTransaction().commit();
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
        entityManager.getTransaction().begin();
        entityManager.merge(account);
        //entityManager.persist(account);
        entityManager.getTransaction().commit();

        return account;
    }

    @Override
    public Account delete(Account account) {
        entityManager.getTransaction().begin();
        Account attachedAccount = entityManager.find(Account.class, account.getId());
        entityManager.remove(attachedAccount);
        entityManager.getTransaction().commit();
        return attachedAccount;

        // zorgt voor error: removing a detached instance
        //entityManager.remove(account);
        //return account;
    }
}
