package be.pxl.student.entity.jpa;

import be.pxl.student.entity.DAO;
import be.pxl.student.entity.Payment;
import be.pxl.student.entity.exception.PaymentException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class PaymentJPA implements DAO<Payment, PaymentException> {

    private EntityManager entityManager;

    public PaymentJPA(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Payment create(Payment payment) throws PaymentException {
        entityManager.getTransaction().begin();
        entityManager.persist(payment);
        entityManager.getTransaction().commit();

        return payment;
    }

    @Override
    public Payment getById(int id) throws PaymentException {
        return entityManager.find(Payment.class, id);
    }

    @Override
    public List<Payment> getAll() throws PaymentException {
        TypedQuery<Payment> query = entityManager.createNamedQuery("findAll", Payment.class);
        return query.getResultList();
    }

    @Override
    public Payment update(Payment payment) throws PaymentException {
        entityManager.getTransaction().begin();
        entityManager.merge(payment);
        entityManager.getTransaction().commit();

        return payment;
    }

    @Override
    public Payment delete(Payment payment) throws PaymentException {
        entityManager.getTransaction().begin();
        Payment attachedPayment = entityManager.find(Payment.class, payment.getId());
        entityManager.remove(attachedPayment);
        entityManager.getTransaction().commit();
        return attachedPayment;
    }
}
