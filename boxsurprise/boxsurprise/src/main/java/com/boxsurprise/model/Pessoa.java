package com.boxsurprise.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pessoa {
    private Long idPessoa;
    private String nome;
    private String telefone;
    private String cpf;
    private String dataNascimento;
}
