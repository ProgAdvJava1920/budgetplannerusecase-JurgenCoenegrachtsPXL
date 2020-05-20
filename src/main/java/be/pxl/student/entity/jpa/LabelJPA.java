package be.pxl.student.entity.jpa;

import be.pxl.student.entity.DAO;
import be.pxl.student.entity.Label;
import be.pxl.student.entity.Payment;
import be.pxl.student.entity.exception.LabelException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class LabelJPA implements DAO<Label, LabelException> {
    private EntityManager entityManager;

    public LabelJPA(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Label create(Label label) throws LabelException {
        // check name uniqueness
        String normalizedLabelName = label.getName().toUpperCase();
        //if (getAll().stream().filter(l -> l.getName().toUpperCase().equals(normalizedLabelName)).findFirst().isPresent()) {
        if (getAll().stream().anyMatch(l -> l.getName().toUpperCase().equals(normalizedLabelName))) {
            throw new LabelException(String.format("There already exists a label with name %s", label.getName()));
        }

        // add to database
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
    public Label delete(Label label) throws LabelException {
        // check if label is in use
        Query query = entityManager.createQuery("SELECT p FROM Payment WHERE p.label.id =  :labelId", Payment.class);
        query.setParameter("labelId", label.getId());
        if (query.getResultList().size() > 0) {
            throw new LabelException(String.format("Label [%S] is in use. Remove the payments first or change their label."));
        }

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Label attachedLabel = entityManager.find(Label.class, label.getId());
        entityManager.remove(attachedLabel);

        transaction.commit();
        return attachedLabel;
    }
}
