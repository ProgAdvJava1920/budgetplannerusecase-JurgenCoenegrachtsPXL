package be.pxl.student.util;

import be.pxl.student.entity.Payment;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Util class to import csv file
 */
public class BudgetPlannerImporter {
    public static List<String> readCsvFile(Path path) throws BudgetPlannerException {
        ArrayList<String> output = new ArrayList<>();

        try  (BufferedReader reader = Files.newBufferedReader(path)) {
            reader.readLine(); // ignore header line

            String line;
            while ((line = reader.readLine()) != null) {
                output.add(line);
            }
        } catch (IOException | NullPointerException e) {
            throw new BudgetPlannerException("Something went wrong reading csv file", e);
        }

        return output;
    }


}
