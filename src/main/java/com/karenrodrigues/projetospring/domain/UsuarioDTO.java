package com.karenrodrigues.projetospring.domain;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UsuarioDTO {
    private Integer id;
    private String nome;
    private Integer idade;
    private String profissao;

}
