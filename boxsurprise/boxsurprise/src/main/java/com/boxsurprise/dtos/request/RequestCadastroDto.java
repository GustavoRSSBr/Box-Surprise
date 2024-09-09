package com.boxsurprise.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestCadastroDto {
    private String nome;
    private String telefone;
    private String cpf;
    private String dataNascimento;
    private String email;
    private String senha;
}

