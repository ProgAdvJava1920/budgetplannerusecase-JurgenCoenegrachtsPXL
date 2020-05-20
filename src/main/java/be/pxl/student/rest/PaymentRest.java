package be.pxl.student.rest;

import be.pxl.student.entity.Label;
import be.pxl.student.entity.Payment;
import be.pxl.student.entity.jpa.AccountJPA;
import be.pxl.student.entity.jpa.LabelJPA;
import be.pxl.student.entity.jpa.PaymentJPA;

import javax.persistence.EntityManager;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("api/payments")
public class PaymentRest {

    @POST
    @Path("{paymentId}/link/{labelId}")
    public Response linkPaymentLabel(@PathParam("paymentId")int paymentId, @PathParam("labelId")int labelId) {
        EntityManager entityManager = EntityManagerUtil.createEntityManager();
        PaymentJPA paymentJPA = new PaymentJPA(entityManager);
        LabelJPA labelJPA = new LabelJPA(entityManager);

        // try to find payment
        Payment payment = paymentJPA.getById(paymentId);
        if (payment == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        // payment can only have one label
        if (payment.getLabel() != null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        // try to find label
        Label label = labelJPA.getById(labelId);
        if (label == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        // link them and update payment
        payment.setLabel(label);
        paymentJPA.update(payment);

        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Path("{paymentId}")
    public Response linkPaymentLabel(@PathParam("paymentId")int paymentId) {
        EntityManager entityManager = EntityManagerUtil.createEntityManager();
        PaymentJPA paymentJPA = new PaymentJPA(entityManager);

        // try to find payment
        Payment payment = paymentJPA.getById(paymentId);
        if (payment == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        // delete payment
        paymentJPA.delete(payment);

        return Response.status(Response.Status.OK).build();
    }
}
