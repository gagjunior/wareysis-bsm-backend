package br.com.wareysis.bsm.controller.usuarios;

import br.com.wareysis.bsm.dto.usuarios.UsuarioCreateDto;
import br.com.wareysis.bsm.service.usuarios.UsuarioService;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

@Path("/clientes")
@Consumes("application/json")
@Produces("application/json")
public class UsuarioController {

    @Inject
    UsuarioService service;

    @POST
    public Response create(@Valid UsuarioCreateDto dto) {

        return Response.ok(service.create(dto)).build();

    }

}
