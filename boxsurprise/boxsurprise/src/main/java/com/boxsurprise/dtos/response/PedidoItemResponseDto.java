package com.boxsurprise.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PedidoItemResponseDto {
    private Integer idPedidoItem;
    private Integer idProduto;
    private String produtoTitulo;
    private String produtoDescricao;
    private Double produtoValor;
    private Integer produtoQuantidade;
    private ProdutoResponseDto produto;
}

