package be.pxl.student.rest;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;
import be.pxl.student.entity.jpa.AccountJPA;
import be.pxl.student.entity.jpa.PaymentJPA;
import be.pxl.student.rest.resource.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.sql.Date;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("api/accounts")
public class AccountRest {
    private static final Logger LOGGER = LogManager.getLogger(AccountRest.class);

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response CreateAccount(NewAccountResource newAccountResource) {
        EntityManager entityManager = EntityManagerUtil.createEntityManager();
        AccountJPA accountJPA = new AccountJPA(entityManager);

        // check if an account with this name already exists, if one was given
        if (!newAccountResource.getName().equals("")) {
            Optional<Account> possibleAccount = accountJPA.getAll().stream()
                    .filter(a -> a.getName().equals(newAccountResource.getName()))
                    .findFirst();
            if (possibleAccount.isPresent()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new MessageResource(String.format("There already exists an account with name [%s]", newAccountResource.getName())))
                        .build();
            }
        }

        // check if account with this IBAN already exists
        Optional<Account> possibleAccount = accountJPA.getAll().stream()
                .filter(a -> a.getIBAN().equals(newAccountResource.getIban()))
                .findFirst();

        // if there is one and its name is not filled, fill in name
        if (possibleAccount.isPresent()) {
            Account existingAccount = possibleAccount.get();

            if (existingAccount.getName().equals("")) {
                existingAccount.setName(newAccountResource.getName());
                existingAccount = accountJPA.update(existingAccount);

                return Response.status(Response.Status.CREATED)
                        .entity(new AccountResource(existingAccount))
                        .build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new MessageResource(String.format("There already exists an account with iban [%s] which has a name", newAccountResource.getIban())))
                        .build();
            }
        }

        // create and add new account
        Account newAccount = new Account();
        newAccount.setIBAN(newAccountResource.getIban());
        newAccount.setName(newAccountResource.getName());

        newAccount = accountJPA.create(newAccount);

        return Response.status(Response.Status.CREATED)
                .entity(new AccountResource(newAccount))
                .build();
    }

    @GET
    @Path("{accountName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPayments(@PathParam("accountName") String accountName) {
        EntityManager entityManager = EntityManagerUtil.createEntityManager();
        AccountJPA accountJPA = new AccountJPA(entityManager);

        // try to find account
        Optional<Account> possibleAccount = accountJPA.getAll()
                .stream()
                .filter(a -> a.getName().equals(accountName))
                .findFirst();

        entityManager.close();
        if (possibleAccount.isPresent()) {
            return Response.ok(possibleAccount.get().getPayments()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("{accountName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPayment(@PathParam("accountName") String senderName, NewPaymentResource newPaymentResource) {
        AccountJPA accountJPA = new AccountJPA(EntityManagerUtil.createEntityManager());
        PaymentJPA paymentJPA = new PaymentJPA(EntityManagerUtil.createEntityManager());

        // find sender
        Optional<Account> possibleSender = accountJPA.getAll().stream()
                .filter(account -> account.getName().equals(senderName))
                .findFirst();

        if (possibleSender.isPresent()) {
            // find receiver
            Optional<Account> possibleReceiver = accountJPA.getAll().stream()
                    .filter(account -> account.getName().equals(newPaymentResource.getCounterAccount()))
                    .findFirst();

            if (possibleReceiver.isPresent()) {
                // create new payment
                Payment newPayment = mapNewPaymentToPayment(newPaymentResource, possibleSender.get(), possibleReceiver.get());

                // add to database
                paymentJPA.create(newPayment);

                // send back created payment
                return Response.status(Response.Status.CREATED).entity(new PaymentResource(newPayment)).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    private Payment mapNewPaymentToPayment(NewPaymentResource newPaymentResource, Account possibleSender, Account possibleReceiver) {
        Payment newPayment = new Payment();
        newPayment.setAccount(possibleSender);
        newPayment.setCounterAccount(possibleReceiver);
        newPayment.setAmount(newPaymentResource.getAmount());
        newPayment.setCurrency(newPaymentResource.getCurrency());
        newPayment.setDate(Date.from(Instant.now()));
        return newPayment;
    }
}
