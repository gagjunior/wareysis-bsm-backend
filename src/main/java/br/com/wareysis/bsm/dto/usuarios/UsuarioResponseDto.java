package br.com.wareysis.bsm.dto.usuarios;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import br.com.wareysis.bsm.dto.tipos.TipoPerfilUsuarioDto;

public record UsuarioResponseDto(
        UUID id,

        String email,

        String nomeCompleto,

        String cpf,

        String telefone,

        Boolean emailVerificado,

        Boolean habilitado,

        Boolean alterarSenha,

        List<TipoPerfilUsuarioDto> perfis,

        LocalDateTime dhCriacao,

        LocalDateTime dhAlteracao
) {

}
