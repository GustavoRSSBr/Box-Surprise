package com.boxsurprise.dtos.response;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnaliseResponseDto {
    private Integer idAnalise;
    private Integer idProduto;
    private String analise;
}

