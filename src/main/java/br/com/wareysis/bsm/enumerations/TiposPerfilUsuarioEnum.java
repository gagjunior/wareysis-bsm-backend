package br.com.wareysis.bsm.enumerations;

import java.util.Arrays;

public enum TiposPerfilUsuarioEnum {
    ADM("ADMINISTRADOR"),
    PROF("PROFISSIONAL"),
    CLI("CLIENTE");

    private final String descricao;

    TiposPerfilUsuarioEnum(String descricao) {

        this.descricao = descricao;
    }

    public String getDescricao() {

        return descricao;
    }

    public static TiposPerfilUsuarioEnum fromDescricao(String descricao) {

        return Arrays.stream(values())
                .filter(perfil -> perfil.descricao.equalsIgnoreCase(descricao))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                String.format("Nenhum perfil de usuário encontrado com a descrição: %s", descricao)
                        )
                );
    }
}
