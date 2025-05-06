package br.com.wareysis.bsm.entity.usuario;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record UsuarioPerfilId(

        @Column(name = "id_usuario")
        UUID idUsuario,

        @Column(name = "id_perfil")
        String idPerfil

) {

}
