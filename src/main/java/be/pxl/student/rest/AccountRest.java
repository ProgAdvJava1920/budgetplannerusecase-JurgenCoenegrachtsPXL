package be.pxl.student.rest;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;
import be.pxl.student.entity.jpa.AccountJPA;
import be.pxl.student.rest.resource.NewPaymentResource;
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

@Path("accounts")
public class AccountRest {
    private static final Logger LOGGER = LogManager.getLogger(AccountRest.class);

    @GET
    @Path("{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPayments(@PathParam("name") String name) {
        EntityManager entityManager = EntityManagerUtil.createEntityManager();
        AccountJPA accountJPA = new AccountJPA(entityManager);

        // try to find account
        Optional<Account> possibleAccount = accountJPA.getAll()
                .stream()
                .filter(a -> a.getName().equals(name))
                .findFirst();

        entityManager.close();
        if (possibleAccount.isPresent()) {
            return Response.ok(possibleAccount.get().getPayments()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPayment(@PathParam("name") String senderName, NewPaymentResource newPaymentResource) {
        EntityManager entityManager = EntityManagerUtil.createEntityManager();
        AccountJPA accountJPA = new AccountJPA(entityManager);

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
                Payment newPayment = new Payment();
                newPayment.setAccount(possibleSender.get());
                newPayment.setCounterAccount(possibleReceiver.get());
                newPayment.setAmount(newPaymentResource.getAmount());
                newPayment.setCurrency(newPaymentResource.getCurrency());
                newPayment.setDate(Date.from(Instant.now()));

                // TODO: add to database


                // TODO: send back created payment
                return Response.status(Response.Status.CREATED).entity(newPayment).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
