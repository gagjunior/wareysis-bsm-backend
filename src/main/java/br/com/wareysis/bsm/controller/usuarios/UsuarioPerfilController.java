package br.com.wareysis.bsm.controller.usuarios;

import br.com.wareysis.bsm.dto.usuarios.UsuarioPerfilDeleteDto;
import br.com.wareysis.bsm.service.usuarios.UsuarioPerfilService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/v1/usuarios/perfil")
@Consumes("application/json")
@Produces("application/json")
public class UsuarioPerfilController {

    @Inject
    UsuarioPerfilService service;

    @GET
    @Path("/{idPerfil}")
    public Response findByIdPerfil(@PathParam("idPerfil") String idPerfil) {

        return Response
                .status(Status.OK)
                .entity(service.findDtoListByPerfilId(idPerfil))
                .build();

    }

    @DELETE
    public Response deleteByIdUsuario(UsuarioPerfilDeleteDto dto) {

        service.deleteByIdUsuario(dto);

        return Response
                .status(Status.NO_CONTENT)
                .build();

    }

}
