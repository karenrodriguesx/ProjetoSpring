package com.karenrodrigues.projetospring.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuarios")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String nome;
    private String profissao;
    private int idade;

    public Usuario(Integer id) {
        this.id = id;
    }

    public Usuario(UsuarioDTO data) {
        this.nome = data.getNome();
        this.profissao = data.getProfissao();
        this.idade = data.getIdade();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
