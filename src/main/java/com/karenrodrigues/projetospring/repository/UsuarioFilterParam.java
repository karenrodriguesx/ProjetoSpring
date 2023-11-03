package com.karenrodrigues.projetospring.repository;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class UsuarioFilterParam {
    private String nome;
    private String profissao;
    private Integer idade;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate releaseDate;
}
