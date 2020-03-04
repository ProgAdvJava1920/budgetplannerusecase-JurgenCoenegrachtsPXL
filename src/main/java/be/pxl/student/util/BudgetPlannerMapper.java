package be.pxl.student.util;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BudgetPlannerMapper {

    // TODO: move to property file
    public static final String DATE_PATTERN = "EEE MMM d HH:mm:ss z yyyy";
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DATE_PATTERN, Locale.US);

    public List<Account> mapAccounts(List<String> accountLines) {
        //       v---- cursor here, alt+enter, 2nd option (convert...)
        //return accountLines.stream().map(this::mapDataLineToAccount).collect(Collectors.toList());

        List<Account> list = new ArrayList<>();
        for (String accountLine : accountLines) {
            Account account = mapDataLineToAccount(accountLine);

            if (!list.contains(account)) {
                list.add(account);
            }
        }

        return list;
    }

    public Account mapDataLineToAccount(String line) {
        String[] items = line.split(",");

        String name = items[0];
        String iban = items[1];
        Account newAccount = new Account(name, iban);

        return newAccount;
    }

    public Date convertToDate(String dateString) throws ParseException {
        return DATE_FORMAT.parse(dateString);
    }

    public String convertDateToString(Date date) {
        return DATE_FORMAT.format(date);
    }
}
