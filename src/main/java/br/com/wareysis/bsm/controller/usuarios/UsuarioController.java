package br.com.wareysis.bsm.controller.usuarios;

import br.com.wareysis.bsm.dto.usuarios.UsuarioCreateDto;
import br.com.wareysis.bsm.dto.usuarios.UsuarioUpdateDto;
import br.com.wareysis.bsm.service.usuarios.UsuarioService;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/clientes")
@Consumes("application/json")
@Produces("application/json")
public class UsuarioController {

    @Inject
    UsuarioService service;

    @POST
    public Response create(@Valid UsuarioCreateDto dto) {

        return Response
                .status(Status.CREATED)
                .entity(service.create(dto))
                .build();

    }

    @PUT
    public Response update(@Valid UsuarioUpdateDto dto) {

        return Response
                .status(Status.OK)
                .entity(service.update(dto))
                .build();
    }

    @GET
    public Response findAll() {

        return Response
                .status(Status.OK)
                .entity(service.findAll())
                .build();
    }

    @GET
    @Path("/id/{id}")
    public Response findById(@PathParam("id") String id) {

        return Response
                .status(Status.OK)
                .entity(service.findById(java.util.UUID.fromString(id)))
                .build();
    }

    @GET
    @Path("/email/{email}")
    public Response findByEmail(@PathParam("email") String email) {

        return Response
                .status(Status.OK)
                .entity(service.findByEmail(email))
                .build();
    }

}
