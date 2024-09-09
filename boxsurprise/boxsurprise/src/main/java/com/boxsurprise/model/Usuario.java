package com.boxsurprise.model;

import com.boxsurprise.enuns.TipoUsuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    private Long idUsuario;
    private String email;
    private String senha;
    private Pessoa pessoa; // Associação com a classe Pessoa
    private TipoUsuario tipoUsuario;
}

