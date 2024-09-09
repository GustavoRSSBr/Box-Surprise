package com.boxsurprise.usecase;

import aj.org.objectweb.asm.commons.Remapper;
import com.boxsurprise.dao.impl.ProdutoJdbcTemplateDaoImpl;
import com.boxsurprise.dtos.response.ChatGPTResponseDto;
import com.boxsurprise.dtos.response.ProdutoResponseDto;
import com.boxsurprise.dtos.request.RequestProdutoDto;
import com.boxsurprise.enuns.ErrorCode;
import com.boxsurprise.exceptions.NegocioException;
import com.boxsurprise.gateway.AnaliseServiceChatGPT;
import com.boxsurprise.model.Produto;
import com.boxsurprise.validador.Validador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Service
public class ProdutoService {
    @Autowired
    AnaliseServiceChatGPT chatGPT;

    @Autowired
    ProdutoJdbcTemplateDaoImpl produtoJdbcTemplateDao;

    @Autowired
    Validador validador;

    public void salvarDadosProduto(RequestProdutoDto request) {

        validador.validar(request);

        Produto produto = Produto.builder()
                .titulo(request.getTitulo())
                .descricao(request.getDescricao())
                .valor(request.getValor())
                .tipoProduto(request.getTipoProduto().toString())
                .tamanhoCaixa(request.getTamanhoCaixa().toString())
                .build();

        produtoJdbcTemplateDao.cadastrarProduto(produto);
    }

    public ProdutoResponseDto buscarProdutoPorId(Integer id) {
        return produtoJdbcTemplateDao.buscarProdutoPorId(id);
    }

    public List<ProdutoResponseDto> listarProdutosCadastrados() {
        return produtoJdbcTemplateDao.listarProdutosCadastrados();
    }

    public void alterarProdutoCadastrado(Integer idProduto, RequestProdutoDto request) {
        validador.validar(request);
        produtoJdbcTemplateDao.alterarProdutoCadastrado(idProduto, request);
    }



}
