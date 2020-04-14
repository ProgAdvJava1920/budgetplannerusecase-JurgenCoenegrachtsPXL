package be.pxl.student.entity.jpa;

import be.pxl.student.entity.DAO;
import be.pxl.student.entity.Payment;
import be.pxl.student.entity.exception.PaymentException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PaymentJPATest {
    DAO<Payment, PaymentException> dao;
    EntityManager entityManager;
    EntityManagerFactory entityManagerFactory;

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
    void Create() {

    }
}
