package com.boxsurprise.usecase;

import com.boxsurprise.dao.impl.CompraJdbcTemplateDao;
import com.boxsurprise.dtos.response.PedidoResponseDto;
import com.boxsurprise.dtos.request.RequestCompraItemDto;
import com.boxsurprise.validador.Validador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompraService {

    @Autowired
    Validador validador;

    @Autowired
    CompraJdbcTemplateDao repository;


    public void comprarItem(RequestCompraItemDto request) {

        repository.processarPedido(request);

    }

    public PedidoResponseDto buscarPedido(Integer idPedido) {
        return repository.buscarPedido(idPedido);
    }
}
