package br.com.wareysis.bsm.mapper.tipos;

import java.util.List;

import org.mapstruct.Mapper;

import br.com.wareysis.bsm.dto.tipos.TipoPerfilUsuarioDto;
import br.com.wareysis.bsm.entity.tipos.TipoPerfilUsuario;

@Mapper(componentModel = "cdi")
public interface TipoPerfilUsuarioMapper {

    // Método de mapeamento entre a entidade TipoPerfilUsuario e o DTO TipoPerfilUsuarioDto
    TipoPerfilUsuarioDto toDto(TipoPerfilUsuario entity);

    // Método para mapear uma lista de TipoPerfilUsuario para uma lista de TipoPerfilUsuarioDto
    List<TipoPerfilUsuarioDto> toDtoList(List<TipoPerfilUsuario> entities);
}
