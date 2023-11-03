package com.karenrodrigues.projetospring.requests;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
public class UsuarioPutRequestBody {
    private Integer id;
    private String nome;
    private String profissao;
    private int idade;
}
