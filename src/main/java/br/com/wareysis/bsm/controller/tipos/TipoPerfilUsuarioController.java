package br.com.wareysis.bsm.controller.tipos;

import br.com.wareysis.bsm.service.tipos.TipoPerfilUsuarioService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/v1/tipo-perfil-usuario")
@Consumes("application/json")
@Produces("application/json")
public class TipoPerfilUsuarioController {

    @Inject
    TipoPerfilUsuarioService service;

    @GET
    public Response findAll() {

        return Response
                .status(Response.Status.OK)
                .entity(service.findAll())
                .build();
    }

    @GET
    @Path("/{id}")
    public Response findById(String id) {

        return Response
                .status(Status.OK)
                .entity(service.findById(id))
                .build();
    }

    @GET
    @Path("/nome/{nome}")
    public Response findByNome(String nome) {

        return Response
                .status(Status.OK)
                .entity(service.findByNome(nome))
                .build();
    }

}
