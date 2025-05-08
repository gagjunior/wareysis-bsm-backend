package br.com.wareysis.bsm.repository.usuarios;

import br.com.wareysis.bsm.entity.usuario.UsuarioPerfil;
import br.com.wareysis.bsm.entity.usuario.UsuarioPerfilId;

import jakarta.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class UsuarioPerfilRepository implements PanacheRepositoryBase<UsuarioPerfil, UsuarioPerfilId> {

}
