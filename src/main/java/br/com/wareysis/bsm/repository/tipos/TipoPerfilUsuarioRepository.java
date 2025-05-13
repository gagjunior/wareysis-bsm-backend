package br.com.wareysis.bsm.repository.tipos;

import java.util.List;
import java.util.Map;

import br.com.wareysis.bsm.entity.tipos.TipoPerfilUsuario;

import jakarta.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class TipoPerfilUsuarioRepository implements PanacheRepositoryBase<TipoPerfilUsuario, String> {

    public List<TipoPerfilUsuario> findByNome(String nome) {

        String param = "%" + nome.toUpperCase() + "%";

        return find("UPPER(nome) LIKE :nome", Map.of("nome", param)).list();
    }

}
