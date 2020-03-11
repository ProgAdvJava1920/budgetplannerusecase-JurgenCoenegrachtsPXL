package be.pxl.student.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountDAOImplTest {
    // jdbc: jdbc gebruiken
    // h2: H2 database
    // mem: in geheugen opslaan
    // MODE: voor MySQL commando's te gebruiken
    // INIT: run script voor te beginnen
    private static final String DB_URL = "jdbc:h2:mem:test;MODE=MySQL;INIT=RUNSCRIPT FROM 'classpath:BudgetPlannerTest.sql'";

    @Test
    void create() {
        fail("Not yet implemented");
    }

    @Test
    void getAll() {
        fail("Not yet implemented");
    }

    @Test
    void it_should_return_account_with_id_1() throws AccountException {
        AccountDAO dao = new AccountDAOImpl(DB_URL);
        Account account = dao.getById(1);

        Account expectedAccount = new Account(1, "dummyIBAN", "dummyName");

        assertEquals(expectedAccount, account);
    }

    @Test
    void update() {
        fail("Not yet implemented");
    }

    @Test
    void delete() {
        fail("Not yet implemented");
    }
}