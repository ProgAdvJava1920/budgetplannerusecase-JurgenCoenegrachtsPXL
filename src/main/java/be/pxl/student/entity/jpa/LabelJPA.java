package be.pxl.student.entity.jpa;

import be.pxl.student.entity.DAO;
import be.pxl.student.entity.Label;
import be.pxl.student.entity.exception.LabelException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class LabelJPA implements DAO<Label, LabelException> {
    private EntityManager entityManager;

    public LabelJPA(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Label create(Label label) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(label);

        transaction.commit();
        return label;
    }

    @Override
    public Label getById(int id) {
        return entityManager.find(Label.class, id);
    }

    @Override
    public List<Label> getAll() {
        TypedQuery<Label> query = entityManager.createNamedQuery("label.findAll", Label.class);
        return query.getResultList();
    }

    @Override
    public Label update(Label label) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.merge(label);
        //entityManager.persist(account);
        transaction.commit();

        return label;
    }

    @Override
    public Label delete(Label label) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Label attachedLabel = entityManager.find(Label.class, label.getId());
        entityManager.remove(attachedLabel);

        transaction.commit();
        return attachedLabel;
    }
}
