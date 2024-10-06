package com.boxsurprise.dtos.request;

import com.boxsurprise.enuns.EnumTamanhoCaixa;
import com.boxsurprise.enuns.EnumTipoProduto;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestCompraItemDto {
    private Integer idEndereco;
    private Integer idProduto;
    private BigDecimal valor;
    private EnumTipoProduto tipoProduto;
    private EnumTamanhoCaixa tamanhoCaixa;
    private String respostasBox;
    private String tema;
    private Integer quantidade;

    public BigDecimal getValorTamanhoCaixa() {
        return tamanhoCaixa.getValor();
    }
}
