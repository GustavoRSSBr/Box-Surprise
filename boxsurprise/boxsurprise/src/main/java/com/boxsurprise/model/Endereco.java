package com.boxsurprise.model;

import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Endereco {
    private Integer idEndereco;
    private String cep;
    private String rua;
    private String numero;
    private String complemento;
    private String cidade;
    private String estado;
}
