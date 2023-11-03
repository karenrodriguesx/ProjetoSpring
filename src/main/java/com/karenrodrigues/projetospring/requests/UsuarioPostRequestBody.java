package com.karenrodrigues.projetospring.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioPostRequestBody {
    @NotEmpty (message = "Por favor, informe um valor para o campo nome")
    @Schema (description = "Nome do usuario", example = "My name")
    private String nome;

    @NotEmpty (message = "Por favor, informe um valor para o campo profissao")
    @Schema (description = "Profissão do usuario", example = "Developer")
    private String profissao;

    @NotNull (message = "Por favor, informe um valor para o campo idade")
    @Schema (description = "Idade do usuario, somente numeros", example = "20")
    private int idade;
}
