package br.com.wareysis.bsm.entity.usuario;

import java.io.Serializable;
import java.time.LocalDateTime;

import br.com.wareysis.bsm.entity.tipos.TipoPerfilUsuario;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
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

    @MapsId("idUsuario")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", insertable = false, updatable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_perfil", insertable = false, updatable = false)
    private TipoPerfilUsuario perfil;

    public TipoPerfilUsuario getPerfil() {

        return perfil;
    }

    public void setPerfil(TipoPerfilUsuario perfil) {

        this.perfil = perfil;
    }

    public Usuario getUsuario() {

        return usuario;
    }

    public void setUsuario(Usuario usuario) {

        this.usuario = usuario;
    }

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
