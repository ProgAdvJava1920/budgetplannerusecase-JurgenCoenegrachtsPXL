package be.pxl.student.entity.jdbc;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;
import be.pxl.student.entity.exception.PaymentException;
import be.pxl.student.entity.exception.PaymentNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PaymentDAOTest {
    private static final String DB_URL = "jdbc:h2:mem:test;MODE=MySQL;INIT=RUNSCRIPT FROM 'classpath:BudgetPlannerTest.sql'";

    private DAOManager manager;
    private PaymentDAO paymentDAO;

    @BeforeEach
    void setUp() {
        manager = new DAOManager(DB_URL);
        paymentDAO = new PaymentDAO(manager);
    }

    @AfterEach
    void tearDown() {
        manager.close();
    }

    @Test
    void it_should_create_new_payment() throws PaymentException {
        Payment expectedPayment = new Payment(Date.valueOf(LocalDate.now()), 50, "EUR", "nothing");
        expectedPayment.setAccount(new Account());
        expectedPayment.getAccount().setId(1);
        expectedPayment.setCounterAccount(new Account());
        expectedPayment.getCounterAccount().setId(2);

        Payment actualPayment = paymentDAO.create(expectedPayment);

        assertEquals(expectedPayment, actualPayment);

    }

    @Test
    void it_should_set_id_of_new_payment() throws PaymentException {
        Payment expectedPayment = new Payment(Date.valueOf(LocalDate.now()), 50, "EUR", "nothing");
        expectedPayment.setAccount(new Account());
        expectedPayment.getAccount().setId(1);
        expectedPayment.setCounterAccount(new Account());
        expectedPayment.getCounterAccount().setId(2);

        Payment actualPayment = paymentDAO.create(expectedPayment);

        assertNotEquals(0, actualPayment.getId());
    }

    @Test
    void it_should_return_payment_with_id_1() throws PaymentException {
        Payment expectedPayment = new Payment(Date.valueOf(LocalDate.now()), 50, "EUR", "nothing");
        expectedPayment.setId(1);

        Payment foundPayment = paymentDAO.getById(1);

        assertEquals(expectedPayment, foundPayment);
    }

    @Test
    void it_should_return_2_payments() throws PaymentException {
        List<Payment> payments = paymentDAO.getAll();

        assertEquals(2, payments.size());
    }

    @Test
    void it_should_update_existing_payment_with_id_1() throws PaymentException {
        Payment expectedPayment = new Payment(Date.valueOf(LocalDate.now()), 25, "USD", "");
        expectedPayment.setId(1);
        expectedPayment.setAccount(new Account());
        expectedPayment.getAccount().setId(1);
        expectedPayment.setCounterAccount(new Account());
        expectedPayment.getCounterAccount().setId(2);

        Payment updatedPayment = paymentDAO.update(expectedPayment);

        assertEquals(expectedPayment, updatedPayment);
    }

    @Test
    void it_should_delete_payment_with_id_1() throws PaymentException {
        Payment expectedPayment = new Payment(Date.valueOf(LocalDate.now()), 50, "EUR", "nothing");
        expectedPayment.setId(1);

        paymentDAO.delete(expectedPayment);

        assertThrows(PaymentNotFoundException.class, () -> {
            Payment payment = paymentDAO.getById(1);
        });
    }
}