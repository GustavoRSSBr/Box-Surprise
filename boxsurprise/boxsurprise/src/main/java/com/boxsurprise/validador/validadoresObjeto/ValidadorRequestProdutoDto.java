package com.boxsurprise.validador.validadoresObjeto;

import com.boxsurprise.dtos.request.RequestProdutoDto;
import com.boxsurprise.enuns.ErrorCode;
import com.boxsurprise.exceptions.NegocioException;

import java.math.BigDecimal;

public class ValidadorRequestProdutoDto implements IEstrategiaValidacao<RequestProdutoDto>{

    @Override
    public boolean validar(RequestProdutoDto objeto) {
        if (objeto == null) {
            throw new NegocioException(ErrorCode.NAO_PODE_SER_NULO.getCustomMessage());
        }

        if (objeto.getTitulo() == null || objeto.getTitulo().trim().isEmpty()) {
            throw new NegocioException(ErrorCode.TITULO_VAZIO.getCustomMessage());
        }

        if (objeto.getValor() == null || objeto.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new NegocioException(ErrorCode.VALOR_OBRIGATORIO.getCustomMessage());
        }

        if (objeto.getTipoProduto() == null) {
            throw new NegocioException(ErrorCode.TIPO_PRODUTO_OBRIGATORIO.getCustomMessage());
        }

        if (objeto.getTamanhoCaixa() == null) {
            throw new NegocioException(ErrorCode.TAMANHO_OBRIGATORIO.getCustomMessage());
        }

        return true;
    }
}
