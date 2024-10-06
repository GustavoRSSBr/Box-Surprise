package com.boxsurprise.usecase;

import com.boxsurprise.config.SegurancaConfig;
import com.boxsurprise.dao.IClienteJdbcTemplateDao;
import com.boxsurprise.dao.impl.CompraJdbcTemplateDao;
import com.boxsurprise.dtos.response.PedidoResponseDto;
import com.boxsurprise.dtos.request.RequestCompraItemDto;
import com.boxsurprise.enuns.StatusPedido;
import com.boxsurprise.validador.Validador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompraService {

    @Autowired
    Validador validador;

    @Autowired
    CompraJdbcTemplateDao repository;

    @Autowired
    IClienteJdbcTemplateDao clienteJdbcTemplateDao;

    @Autowired
    SegurancaConfig seguranca;


    public void comprarItem(String token ,RequestCompraItemDto request) {
        String email = seguranca.buscarEmailToken(token);
        Integer idPessoa = clienteJdbcTemplateDao.buscarIdPessoaPorEmail(email);
        repository.processarPedido(idPessoa,request);

    }

    public PedidoResponseDto buscarPedido(Integer idPedido) {
        return repository.buscarPedido(idPedido);
    }

    public void finalizarCompra(Integer idPedido) {
        repository.finalizarCompra(idPedido, StatusPedido.PROCESSANDO.toString());
    }
}
