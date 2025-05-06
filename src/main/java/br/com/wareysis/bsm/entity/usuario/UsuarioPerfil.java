package br.com.wareysis.bsm.entity.usuario;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name = "USUARIOS_PERFIL")
public class UsuarioPerfil extends PanacheEntityBase implements Serializable {

    @EmbeddedId
    private UsuarioPerfilId id;

    @Column(name = "dh_criacao")
    public LocalDateTime dhCriacao;

    @Column(name = "dh_alteracao")
    public LocalDateTime dhAlteracao;

    public UsuarioPerfilId getId() {

        return id;
    }

    public void setId(UsuarioPerfilId id) {

        this.id = id;
    }

    @PrePersist
    public void prePersist() {

        dhCriacao = LocalDateTime.now();
        dhAlteracao = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {

        dhAlteracao = LocalDateTime.now();
    }

}
