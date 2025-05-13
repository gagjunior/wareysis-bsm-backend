package br.com.wareysis.bsm.dto.usuarios;

import java.time.LocalDateTime;
import java.util.UUID;

public record UsuarioPerfilDto(

        UUID idUsuario,

        String nomeUsuario,

        String idPerfil,

        String nomePerfil,

        LocalDateTime dhCriacao,

        LocalDateTime dhAlteracao

) {

}
