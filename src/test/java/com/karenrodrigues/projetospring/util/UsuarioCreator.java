package com.karenrodrigues.projetospring.util;

import com.karenrodrigues.projetospring.domain.Usuario;

public class UsuarioCreator {

    public static Usuario createUsuarioToBeSaved() {
        return Usuario.builder()
                .nome("Karen")
                .profissao("Dev")
                .idade(20)
                .build();
    }

}
