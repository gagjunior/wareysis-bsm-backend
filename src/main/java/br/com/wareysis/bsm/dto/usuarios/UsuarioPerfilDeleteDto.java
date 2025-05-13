package br.com.wareysis.bsm.dto.usuarios;

import java.util.List;
import java.util.UUID;

import br.com.wareysis.bsm.enumerations.TiposPerfilUsuarioEnum;

import jakarta.validation.constraints.NotNull;

public record UsuarioPerfilDeleteDto(
        @NotNull(message = "ID do usuário não pode ser null")
        UUID idUsuario,

        List<TiposPerfilUsuarioEnum> perfis
) {

}
