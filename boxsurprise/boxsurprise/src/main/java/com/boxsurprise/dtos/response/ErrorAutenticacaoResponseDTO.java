package com.boxsurprise.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorAutenticacaoResponseDTO  {
    private String mensagem;
    private String path;
    private Integer code;
}
