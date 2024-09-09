package com.boxsurprise.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestCadastroEnderecoDto {
    private String cep;
    private String numero;
    private String complemento;
}
