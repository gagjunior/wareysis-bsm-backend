package br.com.wareysis.bsm.entity.usuario;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.runtime.annotations.RegisterForReflection;

@Entity
@Table(name = "USUARIOS")
@RegisterForReflection
public class Usuario extends PanacheEntityBase {

    @Id
    public UUID id;

    @Column(nullable = false, unique = true)
    public String email;

    @Column(name = "nome_completo", nullable = false)
    public String nomeCompleto;

    public String cpf;

    public String telefone;

    @Column(name = "email_verificado")
    public boolean emailVerificado;

    public boolean habilitado;

    @Column(name = "alterar_senha")
    public boolean alterarSenha;

    @Column(name = "dh_criacao")
    public LocalDateTime dhCriacao;

    @Column(name = "dh_alteracao")
    public LocalDateTime dhAlteracao;

    public Usuario() {

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

