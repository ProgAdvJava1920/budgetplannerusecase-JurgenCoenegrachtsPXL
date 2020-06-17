package be.pxl.student.entity.jpa;

import be.pxl.student.entity.DAO;
import be.pxl.student.entity.Payment;
import be.pxl.student.entity.exception.LabelException;
import be.pxl.student.entity.exception.PaymentException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentJPATest {
    private DAO<Payment, PaymentException> dao;
    private EntityManager entityManager;
    private EntityManagerFactory entityManagerFactory;

    @BeforeEach
    void setup() {
        entityManagerFactory = Persistence.createEntityManagerFactory("budgetplanner_test");
        entityManager = entityManagerFactory.createEntityManager();

        dao = new PaymentJPA(entityManager);
    }

    @AfterEach
    void tearDown() {
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void Create() throws PaymentException, LabelException {
        Payment expectedPayment = new Payment();
        expectedPayment.setAmount(500);
        expectedPayment.setDate(Date.from(Instant.now()));

        Payment actualPayment = dao.create(expectedPayment);

        assertEquals(expectedPayment, actualPayment);
    }

    @Test
    void GetById() throws PaymentException {
        Payment payment = dao.getById(1);

        assertNotNull(payment);
        assertEquals(50.0, payment.getAmount());
        assertEquals("EUR", payment.getCurrency());
    }

    @Test
    void GetAll() throws PaymentException {
        List<Payment> allPayments = dao.getAll();

        assertNotNull(allPayments);
        assertEquals(2, allPayments.size());
    }

    @Test
    void Update() throws PaymentException {
        Payment payment = dao.getById(1);
        payment.setAmount(75);
        dao.update(payment);

        assertEquals(75, dao.getById(1).getAmount());
    }

    @Test
    void Delete() throws PaymentException, LabelException {
        Payment expectedPayment = new Payment();
        expectedPayment.setId(1);

        dao.delete(expectedPayment);

        assertNull(dao.getById(1));
    }
}
