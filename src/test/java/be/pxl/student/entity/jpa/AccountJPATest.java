package be.pxl.student.entity.jpa;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.DAO;
import be.pxl.student.entity.exception.AccountException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountJPATest {
    DAO<Account, AccountException> dao;
    EntityManager entityManager;
    EntityManagerFactory entityManagerFactory;

    @BeforeEach
    void setup() {
        entityManagerFactory = Persistence.createEntityManagerFactory("budgetplanner_test");
        entityManager = entityManagerFactory.createEntityManager();

        dao = new AccountJPA(entityManager);
    }

    @AfterEach
    void tearDown() {
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void create() throws AccountException {
        Account expectedAccount = new Account();
        expectedAccount.setName("willekeurigeNaam33");
        expectedAccount.setIBAN("ditisgeenIBAN");
        Account actualAccount = dao.create(expectedAccount);

        assertEquals(expectedAccount, actualAccount);
    }

    @Test
    void getById() throws AccountException {
        Account account = dao.getById(1);
        Account expectedAccount = new Account(1, "dummyIBAN", "dummyName");

        assertEquals(expectedAccount, account);
    }

    @Test
    void getAll() throws AccountException {
        List<Account> accounts = dao.getAll();
        assertEquals(3, accounts.size());
    }

    @Test
    void update() throws AccountException {
        Account account = dao.getById(1);
        account.setName("andereNaam");
        dao.update(account);
        entityManager.clear(); // cache leegmaken

        assertEquals("andereNaam", dao.getById(1).getName());
    }

    @Test
    void delete() throws AccountException {
        Account expectedAccount = new Account(1, "dummyIBAN", "dummyName");
        dao.delete(expectedAccount);
        entityManager.clear(); // cache leegmaken

        assertNull(this.dao.getById(1));
    }
}