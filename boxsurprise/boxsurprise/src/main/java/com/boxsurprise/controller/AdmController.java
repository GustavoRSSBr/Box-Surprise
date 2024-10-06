package com.boxsurprise.controller;

import com.boxsurprise.dtos.response.PedidoPessoaResponseDto;
import com.boxsurprise.dtos.response.RequestStatusItemDto;
import com.boxsurprise.dtos.response.RequestStatusPedidoDto;
import com.boxsurprise.dtos.response.AnaliseResponseDto;
import com.boxsurprise.dtos.response.StandardResponseDTO;
import com.boxsurprise.usecase.AdmService;
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

        ResponseEntity<StandardResponseDTO> response = ResponseEntity.ok(
                StandardResponseDTO.builder()
                        .mensagem("Análise encontrada!")
                        .dados(analise)
                        .build()
        );
        LoggerUtils.logElapsedTime(LOGGER, "buscarAnalise", startTime);
        return  response;
    }

    @GetMapping("/listar-todos-pedidos-nao-finalizado")
    public ResponseEntity<?> listarTodosPedidos() {
        LoggerUtils.logRequestStart(LOGGER, "listarTodosPedidos", null);
        long startTime = System.currentTimeMillis();

        List<PedidoPessoaResponseDto> pedidos = service.listarPedidos();

        StandardResponseDTO response = StandardResponseDTO.builder()
                .mensagem("Pedidos encontrados com sucesso!")
                .dados(pedidos)
                .build();

        LoggerUtils.logElapsedTime(LOGGER, "listarTodosPedidos", startTime);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/mudar-status-item")
    public ResponseEntity<?> mudarStatusPedidoItem(@RequestBody RequestStatusItemDto request){
        LoggerUtils.logRequestStart(LOGGER, "mudarStatusPedidoItem", null);
        long startTime = System.currentTimeMillis();

        service.mudarStatusPedidoItem(request);

        StandardResponseDTO response = StandardResponseDTO.builder()
                .mensagem("Item atualizado com sucesso!")
                .build();

        LoggerUtils.logElapsedTime(LOGGER, "mudarStatusPedidoItem", startTime);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/mudar-status-pedido")
    public ResponseEntity<?> mudarStatusPedido(@RequestBody RequestStatusPedidoDto request){
        LoggerUtils.logRequestStart(LOGGER, "mudarStatusPedido", null);
        long startTime = System.currentTimeMillis();

        service.mudarStatusPedido(request);

        StandardResponseDTO response = StandardResponseDTO.builder()
                .mensagem("Pedido atualizado com sucesso!")
                .build();

        LoggerUtils.logElapsedTime(LOGGER, "mudarStatusPedido", startTime);

        return ResponseEntity.ok(response);
    }




}
