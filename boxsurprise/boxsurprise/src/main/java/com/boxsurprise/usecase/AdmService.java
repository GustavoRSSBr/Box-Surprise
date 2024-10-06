package com.boxsurprise.usecase;

import com.boxsurprise.dao.IAdmJdbcTemplateDao;
import com.boxsurprise.dao.IProdutoJdbcTemplateDao;
import com.boxsurprise.dao.impl.AdmJdbcTemplateImpl;
import com.boxsurprise.dao.impl.ProdutoJdbcTemplateDaoImpl;
import com.boxsurprise.dtos.PedidoPessoaResponseDto;
import com.boxsurprise.dtos.RequestStatusItemDto;
import com.boxsurprise.dtos.RequestStatusPedidoDto;
import com.boxsurprise.dtos.response.AnaliseResponseDto;
import com.boxsurprise.dtos.response.PedidoResponseDto;
import com.boxsurprise.dtos.response.ProdutoResponseDto;
import com.boxsurprise.enuns.ErrorCode;
import com.boxsurprise.exceptions.NegocioException;
import com.boxsurprise.gateway.AnaliseServiceChatGPT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Service
public class AdmService {

    @Autowired
    IProdutoJdbcTemplateDao produtoJdbcTemplateDao;

    @Autowired
    IAdmJdbcTemplateDao admDao;

    @Autowired
    AnaliseServiceChatGPT chatGPT;

    public Mono<String> criarAnalise(Integer idProduto) {

        ProdutoResponseDto produto = produtoJdbcTemplateDao.buscarProdutoPorId(idProduto);

        String perfil;

        if (produto.getTipoProduto().equals("CAIXA_PERSONALIDADE") && produto.getRespostasBox() != null) {
            perfil = produto.getRespostasBox();
        } else if (produto.getTipoProduto().equals("CAIXA_TEMATICA") && produto.getTema() != null) {
            perfil = produto.getTema();
        } else {
            throw new NegocioException(ErrorCode.ANALISE_INDISPONIVEL.getCustomMessage());
        }

        Mono<String> respostaChat = chatGPT.criarAnalise(perfil)
                .map(response -> response.getChoices().getFirst().getMessage().getContent());

        // Persistir a resposta do ChatGPT usando um Scheduler para não bloquear o fluxo
        return respostaChat
                .flatMap(resposta -> Mono.fromCallable(() -> {
                            // Persistência com JDBC
                            admDao.salvarAnalise(idProduto, resposta);
                            return resposta;  // Retorna a resposta após a persistência
                        })
                        .subscribeOn(Schedulers.boundedElastic()));  // Move a persistência para um Scheduler apropriado
    }

    public AnaliseResponseDto buscarAnalise(Integer idProduto) {
        return admDao.buscarAnalise(idProduto);
    }



    public List<PedidoPessoaResponseDto> listarPedidos() {
        List<PedidoPessoaResponseDto> pedidos = admDao.listarPedidos();

        if (pedidos == null || pedidos.isEmpty()){
            throw new NegocioException(ErrorCode.NENHUM_DADO_ENCONTRADO.getCustomMessage());
        }

        return pedidos;
    }

    public void mudarStatusPedidoItem(RequestStatusItemDto request) {

        admDao.mudarStatusPedidoItem(request);
    }

    public void mudarStatusPedido(RequestStatusPedidoDto request) {

        admDao.mudarStatusPedido(request);
    }
}
