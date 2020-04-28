package be.pxl.student.util;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;
//import jdk.jshell.spi.ExecutionControl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class BudgetPlannerMapper {

    // TODO: move to property file
    public static final String DATE_PATTERN = "EEE MMM d HH:mm:ss z yyyy";
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DATE_PATTERN, Locale.US);
    public static final int CSV_ITEM_COUNT = 7;

    private Map<String, Account> accountMap = new HashMap<>();

    public List<Account> mapAccounts(List<String> accountLines) {
        //       v---- cursor here, alt+enter, 2nd option (convert...)
        //return accountLines.stream().map(this::mapDataLineToAccount).collect(Collectors.toList());

        //List<Account> list = new ArrayList<>();
        for (String accountLine : accountLines) {
            Account account = null;

            // skip bad lines, continue parsing
            try {
                // parse account (and payment) from line
                account = mapDataLineToAccount(accountLine);

                // if account isn't in the map yet, put it in
                accountMap.putIfAbsent(account.getIBAN(), account);


                /*
                if (!list.contains(account)) {
                    list.add(account);
                } else {
                    list.get(list.indexOf(account)).getPayments().add(account.getPayments().get(0));
                }
                */
            } catch (ParseException | BudgetPlannerException e) {
                System.err.println(String.format("Could not parse line [%s]", accountLine));
            }
        }

        //return list;
        return new ArrayList<>(accountMap.values());
    }

    public Account mapDataLineToAccount(String line) throws ParseException, BudgetPlannerException {
        String[] items = line.split(",");

        if (items.length != 7) {
            throw new BudgetPlannerException(String.format("Expected %d fields in line, but found %d", CSV_ITEM_COUNT, items.length));
        }

        String name = items[0];
        String iban = items[1];
        String counterIban = items[2];

        // get existing account from map, if it doesn't doesn't exist in the map create a new account
        Account account = accountMap.getOrDefault(iban, new Account(name, iban));
        if (account.getName() == null) {
            account.setName(name);
        }

        // get existing counter account from map, if it doesn't exist, create one
        Account counterAccount = accountMap.getOrDefault(counterIban, new Account(iban));
        accountMap.putIfAbsent(counterIban, counterAccount);

        //Account account = new Account(name, iban);
        Payment payment = mapItemsToPayment(items);
        payment.setAccount(account);
        payment.setCounterAccount(counterAccount);

        account.getPayments().add(payment);

        return account;
    }

    public Date convertToDate(String dateString) throws ParseException {
        return DATE_FORMAT.parse(dateString);
    }

    public String convertDateToString(Date date) {
        return DATE_FORMAT.format(date);
    }

    public Payment mapItemsToPayment(String[] items) throws ParseException {
        return new Payment(
                // IBAN
                convertToDate(items[3]),        // transaction date
                Float.parseFloat(items[4]),     // amount
                items[5],                       // currency
                items[6]                        // comment
        );
    }
}
