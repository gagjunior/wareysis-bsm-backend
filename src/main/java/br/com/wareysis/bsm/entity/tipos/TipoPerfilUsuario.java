package br.com.wareysis.bsm.entity.tipos;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.Immutable;

import br.com.wareysis.bsm.entity.usuario.UsuarioPerfil;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name = "TIPOS_PERFIL_USUARIO")
@Immutable
public class TipoPerfilUsuario extends PanacheEntityBase implements Serializable {

    @Id
    @Column(name = "ID", length = 10, nullable = false, updatable = false, insertable = false)
    private String id;

    @Column(name = "NOME", length = 50, nullable = false, unique = true, updatable = false, insertable = false)
    private String nome;

    @OneToMany(mappedBy = "perfil", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UsuarioPerfil> usuarios;

    @Column(name = "DH_CRIACAO", updatable = false, insertable = false)
    private LocalDateTime dhCriacao;

    @Column(name = "DH_ALTERACAO", insertable = false, updatable = false)
    private LocalDateTime dhAlteracao;

    public String getId() {

        return id;
    }

    public String getNome() {

        return nome;
    }

    public LocalDateTime getDhCriacao() {

        return dhCriacao;
    }

    public LocalDateTime getDhAlteracao() {

        return dhAlteracao;
    }

}
