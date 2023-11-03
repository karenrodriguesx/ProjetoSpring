package com.karenrodrigues.projetospring.util;

import com.karenrodrigues.projetospring.requests.UsuarioPostRequestBody;

public class UsuarioPostRequestBodyCreator {
    public static UsuarioPostRequestBody createUsuarioPostRequestBody() {
        return UsuarioPostRequestBody.builder()
                .nome(UsuarioCreator.createUsuarioToBeSaved().getNome())
                .profissao(UsuarioCreator.createUsuarioToBeSaved().getProfissao())
                .idade(UsuarioCreator.createUsuarioToBeSaved().getIdade())
                .build();
    }
}
