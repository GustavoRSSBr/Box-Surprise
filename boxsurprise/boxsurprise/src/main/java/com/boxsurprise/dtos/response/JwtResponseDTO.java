package com.boxsurprise.dtos.response;

import com.boxsurprise.enuns.TipoUsuario;
import lombok.Data;

@Data
public class JwtResponseDTO {
    private int id;
    private String email;
    private String senha;
    private TipoUsuario tipoUsuario;
}
