package be.pxl.student;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;
import be.pxl.student.entity.exception.AccountException;
import be.pxl.student.entity.exception.PaymentException;
import be.pxl.student.entity.jpa.AccountJPA;
import be.pxl.student.entity.jpa.PaymentJPA;
import be.pxl.student.util.BudgetPlannerException;
import be.pxl.student.util.BudgetPlannerImporter;
import be.pxl.student.util.BudgetPlannerMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class BudgetPlanner {
    private static Logger logger = LogManager.getLogger(BudgetPlanner.class);
    private static EntityManagerFactory entityManagerFactory;

    private static AccountJPA accountJPA;
    private static PaymentJPA paymentJPA;

    public static void main(String[] args) {
        String csvFile = "src/main/resources/account_payments_big.csv";

        try {
            logger.info("Starting csv import");
            List<String> csvLines = BudgetPlannerImporter.readCsvFile(Paths.get(csvFile));
            logger.info("Csv import done");

            logger.info("Starting account mapping");
            List<Account> accounts = new BudgetPlannerMapper().mapAccounts(csvLines);
            accounts.forEach(logger::debug);
            logger.info("Account mapping done");

            // set up DAO objects
            entityManagerFactory = Persistence.createEntityManagerFactory("budgetplanner_pu");
            accountJPA = new AccountJPA(entityManagerFactory.createEntityManager());
            paymentJPA = new PaymentJPA(entityManagerFactory.createEntityManager());

            // insert objects into database
            int counter = 0;
            for (Account account : accounts) {
                insertIntoDatabase(account);
                counter++;
            }
            logger.info(String.format("Inserted %d accounts into database", counter));
            
            counter = 0;
            for (Account a : accounts) {
                for (Payment payment : a.getPayments()) {
                    insertIntoDatabase(payment);
                    counter++;
                }
            }
            logger.info(String.format("Inserted %d payments into database", counter));

            /*
            // set up db
            entityManagerFactory = Persistence.createEntityManagerFactory("budgetplanner_pu");
            entityManager = entityManagerFactory.createEntityManager();

            // put all accounts and their payments into the database
            entityManager.getTransaction().begin();
            accounts.forEach(entityManager::persist);
            entityManager.getTransaction().commit();

            // put all payments into database
            entityManager.getTransaction().begin();
            accounts.stream()
                    .flatMap(a -> a.getPayments().stream())
                    .forEach(entityManager::persist);

            entityManager.getTransaction().commit();
            // stick in database
            //accounts.forEach(BudgetPlanner::insertIntoDatabase);
            */

        } catch (BudgetPlannerException e) {
            logger.error("Exception importing accounts", e);
        }
    }

    private static void insertIntoDatabase(Account account) {
        accountJPA.create(account);
    }

    private static void insertIntoDatabase(Payment payment) {
        paymentJPA.create(payment);
    }
}
