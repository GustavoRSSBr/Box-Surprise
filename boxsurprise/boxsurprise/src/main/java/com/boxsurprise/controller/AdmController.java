package com.boxsurprise.controller;

import com.boxsurprise.dtos.PedidoPessoaResponseDto;
import com.boxsurprise.dtos.RequestStatusItemDto;
import com.boxsurprise.dtos.RequestStatusPedidoDto;
import com.boxsurprise.dtos.request.RequestCadastroDto;
import com.boxsurprise.dtos.response.AnaliseResponseDto;
import com.boxsurprise.dtos.response.PedidoResponseDto;
import com.boxsurprise.dtos.response.StandardResponse;
import com.boxsurprise.gateway.AnaliseServiceChatGPT;
import com.boxsurprise.usecase.AdmService;
import com.boxsurprise.usecase.ProdutoService;
import com.boxsurprise.utils.LoggerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/adm")
public class AdmController {

    @Autowired
    AdmService service;

    private static final Logger LOGGER = LoggerFactory.getLogger(AdmController.class);

    @PostMapping("/gerar-analise/")
    public Mono<String> GerarAnaliseProdutoID(@RequestParam Integer idProduto) {
        LoggerUtils.logRequestStart(LOGGER, "GerarAnaliseProdutoID", idProduto);
        return service.criarAnalise(idProduto);

    }

    @GetMapping("/buscar-analise-produto")
    public ResponseEntity<?> buscarAnalise(@RequestParam Integer idProduto){
        LoggerUtils.logRequestStart(LOGGER, "buscarAnalise", idProduto);
        long startTime = System.currentTimeMillis();

        AnaliseResponseDto analise = service.buscarAnalise(idProduto);

        ResponseEntity<StandardResponse> response = ResponseEntity.ok(
                StandardResponse.builder()
                        .message("Análise encontrada!")
                        .data(analise)
                        .build()
        );
        LoggerUtils.logElapsedTime(LOGGER, "buscarAnalise", startTime);
        return  response;
    }

    @GetMapping("/listar-todos-pedidos-nao-finalizado")
    public ResponseEntity<StandardResponse> listarTodosPedidos() {
        LoggerUtils.logRequestStart(LOGGER, "listarTodosPedidos", null);
        long startTime = System.currentTimeMillis();

        List<PedidoPessoaResponseDto> pedidos = service.listarPedidos();

        StandardResponse response = StandardResponse.builder()
                .message("Pedidos encontrados com sucesso!")
                .data(pedidos)
                .build();

        LoggerUtils.logElapsedTime(LOGGER, "listarTodosPedidos", startTime);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/mudar-status-item")
    public ResponseEntity<StandardResponse> mudarStatusPedidoItem(@RequestBody RequestStatusItemDto request){
        LoggerUtils.logRequestStart(LOGGER, "mudarStatusPedidoItem", null);
        long startTime = System.currentTimeMillis();

        service.mudarStatusPedidoItem(request);

        StandardResponse response = StandardResponse.builder()
                .message("Item atualizado com sucesso!")
                .build();

        LoggerUtils.logElapsedTime(LOGGER, "mudarStatusPedidoItem", startTime);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/mudar-status-pedido")
    public ResponseEntity<StandardResponse> mudarStatusPedido(@RequestBody RequestStatusPedidoDto request){
        LoggerUtils.logRequestStart(LOGGER, "mudarStatusPedido", null);
        long startTime = System.currentTimeMillis();

        service.mudarStatusPedido(request);

        StandardResponse response = StandardResponse.builder()
                .message("Pedido atualizado com sucesso!")
                .build();

        LoggerUtils.logElapsedTime(LOGGER, "mudarStatusPedido", startTime);

        return ResponseEntity.ok(response);
    }




}
