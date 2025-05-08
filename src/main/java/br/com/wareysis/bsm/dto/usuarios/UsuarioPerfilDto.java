package br.com.wareysis.bsm.dto.usuarios;

import java.time.LocalDateTime;
import java.util.UUID;

public record UsuarioPerfilDto(
        UUID idUsuario,

        String idPerfil,

        LocalDateTime dhCriacao,

        LocalDateTime dhAlteracao
) {

}
