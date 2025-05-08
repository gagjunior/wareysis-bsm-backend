package br.com.wareysis.bsm.mapper.tipos;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.com.wareysis.bsm.dto.tipos.TipoPerfilUsuarioDto;
import br.com.wareysis.bsm.entity.tipos.TipoPerfilUsuario;

@Mapper(componentModel = "cdi")
public interface TipoPerfilUsuarioMapper {

    // Instância do Mapper
    TipoPerfilUsuarioMapper INSTANCE = Mappers.getMapper(TipoPerfilUsuarioMapper.class);

    // Método de mapeamento
    TipoPerfilUsuarioDto toDto(TipoPerfilUsuario entity);
}
