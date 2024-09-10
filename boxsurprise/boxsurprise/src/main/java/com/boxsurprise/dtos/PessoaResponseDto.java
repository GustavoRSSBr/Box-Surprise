package com.boxsurprise.dtos;

import com.boxsurprise.dtos.response.EnderecoResponseDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PessoaResponseDto {
    private Integer idPessoa;
    private String nome;
    private String telefone;
    private String cpf;
    private String dataNascimento;
    private String email;

    private List<EnderecoResponseDto> enderecos;
}
