package be.pxl.student.rest;

import be.pxl.student.entity.Label;
import be.pxl.student.entity.exception.LabelException;
import be.pxl.student.entity.jpa.LabelJPA;
import be.pxl.student.rest.resource.MessageResource;
import be.pxl.student.rest.resource.NewLabelResource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("api/labels")
public class LabelRest {
    private static final Logger LOGGER = LogManager.getLogger(AccountRest.class);

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        EntityManager entityManager = EntityManagerUtil.createEntityManager();
        LabelJPA labelJPA = new LabelJPA(entityManager);

        return Response.status(Response.Status.OK)
                .entity(labelJPA.getAll())
                .build();
    }

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addNew(NewLabelResource newLabel) {
        EntityManager entityManager = EntityManagerUtil.createEntityManager();
        LabelJPA labelJPA = new LabelJPA(entityManager);

        // convert to label
        Label label = new Label(newLabel.getName());

        // try to add to database
        try {
            label = labelJPA.create(label);
        } catch (LabelException e) {
            // show error if it went wrong
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new MessageResource(e.getMessage()))
                    .build();
        }

        // return if it was created
        return Response.status(Response.Status.CREATED)
                .entity(label)
                .build();
    }

    @DELETE
    @Path("{labelId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteLabel(@PathParam("labelId")int labelId) {
        EntityManager entityManager = EntityManagerUtil.createEntityManager();
        LabelJPA labelJPA = new LabelJPA(entityManager);

        // try to delete label
        try {
            Label label = new Label();
            label.setId(labelId);
            labelJPA.delete(label);
        } catch (LabelException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new MessageResource(e.getMessage()))
                    .build();
        }

        return Response.status(Response.Status.ACCEPTED).build();
    }
}
