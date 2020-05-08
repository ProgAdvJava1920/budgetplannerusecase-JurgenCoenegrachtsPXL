package be.pxl.student.entity.jpa;

import be.pxl.student.entity.DAO;
import be.pxl.student.entity.Label;
import be.pxl.student.entity.exception.LabelException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LabelJPATest {
    DAO<Label, LabelException> dao;
    EntityManager entityManager;
    EntityManagerFactory entityManagerFactory;

    @BeforeEach
    void setup() {
        entityManagerFactory = Persistence.createEntityManagerFactory("budgetplanner_test");
        entityManager = entityManagerFactory.createEntityManager();

        dao = new LabelJPA(entityManager);
    }

    @AfterEach
    void tearDown() {
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void create() throws LabelException {
        Label expectedLabel = new Label("testName", "testDescription");

        Label actualLabel = dao.create(expectedLabel);

        assertEquals(expectedLabel, actualLabel);
    }

    @Test
    void getById() throws LabelException {
        Label label = dao.getById(1);

        Label expectedLabel = new Label(1, "dummyName", "dummyDescription");

        assertEquals(expectedLabel, label);
    }

    @Test
    void getAll() throws LabelException {
        List<Label> all = dao.getAll();
        assertEquals(2, all.size());
    }

    @Test
    void update() throws LabelException {
        Label label = dao.getById(1);
        label.setName("coolName");

        dao.update(label);
        entityManager.clear(); // cache leegmaken

        assertEquals("coolName", dao.getById(1).getName());
    }

    @Test
    void delete() throws LabelException {
        Label expectedLabel = new Label(1, "dummyName", "dummyDescription");
        dao.delete(expectedLabel);
        entityManager.clear(); // cache leegmaken

        assertNull(this.dao.getById(1));
    }
}