package com.boxsurprise.dao;

import com.boxsurprise.dtos.response.PedidoResponseDto;
import com.boxsurprise.dtos.request.RequestCompraItemDto;

public interface ICompraJdbcTemplateDao {
    void processarPedido(RequestCompraItemDto request);

    PedidoResponseDto buscarPedido(Integer idPedido);

    void finalizarCompra(Integer idPedido, String string);
}
