package br.com.wareysis.bsm.dto.usuarios;

import java.util.List;

import br.com.wareysis.bsm.enumerations.TiposPerfilUsuarioEnum;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record UsuarioCreateDto(

        @NotBlank(message = "E-mail não pode ser vazio")
        @Email(message = "E-mail inválido")
        String email,

        @NotBlank(message = "Senha não pode ser vazia")
        String senha,

        @NotBlank(message = "Nome completo não pode ser vazio")
        String nomeCompleto,

        String cpf,

        String telefone,

        @NotNull(message = "Lista de perfis não pode ser null")
        @NotEmpty(message = "Lista de perfis não pode ser vazia")
        List<TiposPerfilUsuarioEnum> perfis

) {

}
