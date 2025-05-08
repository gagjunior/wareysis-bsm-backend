package br.com.wareysis.bsm.repository.usuarios;

import br.com.wareysis.bsm.entity.tipos.TipoPerfilUsuario;

import jakarta.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class TipoPerfilUsuarioRepository implements PanacheRepositoryBase<TipoPerfilUsuario, String> {

}
