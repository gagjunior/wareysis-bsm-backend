package br.com.wareysis.bsm.repository.usuarios;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import br.com.wareysis.bsm.entity.usuario.Usuario;

import jakarta.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class UsuarioRepository implements PanacheRepositoryBase<Usuario, UUID> {

    public Optional<Usuario> findByEmail(String email) {

        return find("email = :email", Map.of("email", email)).singleResultOptional();
    }

    public List<Usuario> findByNome(String nome) {

        String param = "%" + nome.toUpperCase() + "%";

        return find("UPPER(nomeCompleto) LIKE :nome", Map.of("nome", param)).list();
    }

}
