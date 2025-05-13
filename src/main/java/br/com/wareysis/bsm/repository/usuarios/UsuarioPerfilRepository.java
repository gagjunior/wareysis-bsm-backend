package br.com.wareysis.bsm.repository.usuarios;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import br.com.wareysis.bsm.dto.usuarios.UsuarioPerfilDto;
import br.com.wareysis.bsm.entity.usuario.UsuarioPerfil;
import br.com.wareysis.bsm.entity.usuario.UsuarioPerfilId;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class UsuarioPerfilRepository implements PanacheRepositoryBase<UsuarioPerfil, UsuarioPerfilId> {

    public List<UsuarioPerfilDto> findDtoListByPerfilId(String idPerfil) {

        List<Object[]> resultados = executarConsultaUsuarioPerfilPorUsuarioId(idPerfil);

        if (resultados.isEmpty()) {
            throw new NotFoundException("Nenhum vínculo encontrado para o usuário");
        }

        return resultados.stream()
                .map(this::mapearParaDto)
                .toList();
    }

    private List<Object[]> executarConsultaUsuarioPerfilPorUsuarioId(String idPerfil) {

        @SuppressWarnings("unchecked")
        List<Object[]> resultList = getEntityManager().createQuery(
                        """
                                    SELECT 
                                        u.id, 
                                        u.nomeCompleto,
                                        tpu.id,
                                        tpu.nome,
                                        up.dhCriacao,
                                        up.dhAlteracao
                                    FROM 
                                        Usuario u 
                                        JOIN UsuarioPerfil up ON u.id = up.id.idUsuario
                                        JOIN TipoPerfilUsuario tpu ON up.id.idPerfil = tpu.id
                                    WHERE 
                                        tpu.id = :idPerfil
                                """
                )
                .setParameter("idPerfil", idPerfil)
                .getResultList();

        return resultList;
    }

    private UsuarioPerfilDto mapearParaDto(Object[] linha) {

        return new UsuarioPerfilDto(
                (UUID) linha[0],
                (String) linha[1],
                (String) linha[2],
                (String) linha[3],
                (LocalDateTime) linha[4],
                (LocalDateTime) linha[5]
        );
    }
}
