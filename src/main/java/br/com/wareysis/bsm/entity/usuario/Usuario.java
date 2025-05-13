package br.com.wareysis.bsm.entity.usuario;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.runtime.annotations.RegisterForReflection;

@Entity
@Table(name = "USUARIOS")
@RegisterForReflection
public class Usuario extends PanacheEntityBase implements Serializable {

    @Id
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "nome_completo", nullable = false)
    private String nomeCompleto;

    private String cpf;

    private String telefone;

    @Column(name = "email_verificado")
    private Boolean emailVerificado;

    private Boolean habilitado;

    @Column(name = "alterar_senha")
    private Boolean alterarSenha;

    @Column(name = "dh_criacao")
    private LocalDateTime dhCriacao;

    @Column(name = "dh_alteracao")
    private LocalDateTime dhAlteracao;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<UsuarioPerfil> perfis;

    public List<UsuarioPerfil> getPerfis() {

        return perfis;
    }

    public void setPerfis(List<UsuarioPerfil> perfis) {

        this.perfis = perfis;
    }

    public UUID getId() {

        return id;
    }

    public void setId(UUID id) {

        this.id = id;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public String getNomeCompleto() {

        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {

        this.nomeCompleto = nomeCompleto;
    }

    public String getCpf() {

        return cpf;
    }

    public void setCpf(String cpf) {

        this.cpf = cpf;
    }

    public String getTelefone() {

        return telefone;
    }

    public void setTelefone(String telefone) {

        this.telefone = telefone;
    }

    public Boolean isEmailVerificado() {

        return emailVerificado;
    }

    public void setEmailVerificado(Boolean emailVerificado) {

        this.emailVerificado = emailVerificado;
    }

    public Boolean isHabilitado() {

        return habilitado;
    }

    public void setHabilitado(Boolean habilitado) {

        this.habilitado = habilitado;
    }

    public Boolean isAlterarSenha() {

        return alterarSenha;
    }

    public void setAlterarSenha(Boolean alterarSenha) {

        this.alterarSenha = alterarSenha;
    }

    public LocalDateTime getDhCriacao() {

        return dhCriacao;
    }

    public void setDhCriacao(LocalDateTime dhCriacao) {

        this.dhCriacao = dhCriacao;
    }

    public LocalDateTime getDhAlteracao() {

        return dhAlteracao;
    }

    public void setDhAlteracao(LocalDateTime dhAlteracao) {

        this.dhAlteracao = dhAlteracao;
    }

    @PrePersist
    private void prePersist() {

        dhCriacao = LocalDateTime.now();
        dhAlteracao = LocalDateTime.now();
    }

    @PreUpdate
    private void preUpdate() {

        dhAlteracao = LocalDateTime.now();
    }
}

