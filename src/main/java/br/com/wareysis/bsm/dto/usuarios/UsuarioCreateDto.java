package br.com.wareysis.bsm.dto.usuarios;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioCreateDto(

        @NotBlank(message = "E-mail não pode ser vazio")
        @Email(message = "E-mail inválido")
        String email,

        @NotBlank(message = "Senha não pode ser vazia")
        String senha,

        @NotBlank(message = "Nome completo não pode ser vazio")
        String nomeCompleto,

        String cpf,

        String telefone

) {

}
