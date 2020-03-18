package be.pxl.student.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaymentDAOTest {
    private static final String DB_URL = "jdbc:h2:mem:test;MODE=MySQL;INIT=RUNSCRIPT FROM 'classpath:BudgetPlannerTest.sql'";

    PaymentDAO paymentDAO;

    @BeforeEach
    void setUp() {
        paymentDAO = new PaymentDAO(DB_URL);
    }

    @Test
    void create() {
        fail("Not yet implemented");
    }

    @Test
    void getById() {
        fail("Not yet implemented");
    }

    @Test
    void getAll() {
        fail("Not yet implemented");
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