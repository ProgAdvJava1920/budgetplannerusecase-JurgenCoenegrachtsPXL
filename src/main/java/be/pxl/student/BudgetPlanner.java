package be.pxl.student;

import be.pxl.student.entity.Account;
import be.pxl.student.util.BudgetPlannerException;
import be.pxl.student.util.BudgetPlannerImporter;
import be.pxl.student.util.BudgetPlannerMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.Persistence;
import java.nio.file.Paths;
import java.util.List;

public class BudgetPlanner {
    private static Logger logger = LogManager.getLogger(BudgetPlanner.class);

    public static void main(String[] args) {
        String csvFile = "src/main/resources/account_payments.csv";

        try {
            logger.info("Starting csv import");
            List<String> csvLines = BudgetPlannerImporter.readCsvFile(Paths.get(csvFile));
            logger.info("Csv import done");

            logger.info("Starting account mapping");
            List<Account> accounts = new BudgetPlannerMapper().mapAccounts(csvLines);
            accounts.forEach(logger::debug);
            logger.info("Account mapping done");

            // stick in database
            accounts.forEach(BudgetPlanner::insertIntoDatabase);

        } catch (BudgetPlannerException e) {
            logger.error("Exception importing accounts", e);
        }
    }

    public static void insertIntoDatabase(Object o) {
        Persistence.createEntityManagerFactory("budgetplanner_pu").createEntityManager().persist(o);
    }
}
