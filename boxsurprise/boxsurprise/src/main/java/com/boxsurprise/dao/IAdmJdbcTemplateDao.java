package com.boxsurprise.dao;

import com.boxsurprise.dtos.PedidoPessoaResponseDto;
import com.boxsurprise.dtos.RequestStatusItemDto;
import com.boxsurprise.dtos.RequestStatusPedidoDto;
import com.boxsurprise.dtos.response.AnaliseResponseDto;

import java.util.List;

public interface IAdmJdbcTemplateDao {
    void salvarAnalise(Integer idProduto, String resposta);

    AnaliseResponseDto buscarAnalise(Integer idProduto);

    List<PedidoPessoaResponseDto> listarPedidos();

    void mudarStatusPedidoItem(RequestStatusItemDto request);

    void mudarStatusPedido(RequestStatusPedidoDto request);
}
