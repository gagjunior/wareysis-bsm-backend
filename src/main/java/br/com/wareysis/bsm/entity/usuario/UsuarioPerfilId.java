package br.com.wareysis.bsm.entity.usuario;

import java.io.Serializable;
import java.util.UUID;

import br.com.wareysis.bsm.enumerations.TiposPerfilUsuarioEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public record UsuarioPerfilId(

        @Column(name = "id_usuario")
        UUID idUsuario,

        @Enumerated(EnumType.STRING)
        @Column(name = "id_perfil")
        TiposPerfilUsuarioEnum idPerfil

) implements Serializable {

}
