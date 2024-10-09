package com.boxsurprise.controller;

import com.boxsurprise.dtos.response.AnaliseResponseDto;
import com.boxsurprise.dtos.response.PedidoPessoaResponseDto;
import com.boxsurprise.dtos.response.RequestStatusItemDto;
import com.boxsurprise.dtos.response.RequestStatusPedidoDto;
import com.boxsurprise.dtos.response.StandardResponseDTO;
import com.boxsurprise.usecase.AdmService;
import com.boxsurprise.utils.LoggerUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador responsável por operações administrativas.
 */
@RestController
@RequestMapping("/adm")
@Tag(name = "Administração", description = "Operações de administração, como análise e status de pedidos.")
public class AdmController {

    @Autowired
    private AdmService service;

    private static final Logger LOGGER = LoggerFactory.getLogger(AdmController.class);

    /**
     * Gera uma análise para um produto específico.
     *
     * @param idProduto ID do produto para o qual a análise será gerada.
     * @return Confirmação da geração da análise.
     */
    @Operation(
            summary = "Gera uma análise para um produto utilizando Inteligência Artificial",
            description = "Cria uma análise para o produto especificado pelo ID.",
            parameters = {
                    @Parameter(name = "idProduto", description = "ID do produto", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Análise gerada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro de negócio"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
            }
    )
    @PostMapping("/gerar-analise/")
    public Mono<String> gerarAnaliseProdutoID(@RequestParam Integer idProduto) {
        LoggerUtils.logRequestStart(LOGGER, "GerarAnaliseProdutoID", idProduto);
        return service.criarAnalise(idProduto);
    }

    /**
     * Busca a análise de um produto específico.
     *
     * @param idProduto ID do produto cuja análise será buscada.
     * @return Análise do produto.
     */
    @Operation(
            summary = "Busca análise de um produto",
            description = "Retorna a análise do produto especificado pelo ID.",
            parameters = {
                    @Parameter(name = "idProduto", description = "ID do produto", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Análise encontrada"),
                    @ApiResponse(responseCode = "400", description = "Erro de negócio"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
            }
    )
    @GetMapping("/buscar-analise-produto")
    public ResponseEntity<?> buscarAnalise(@RequestParam Integer idProduto) {
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
        return response;
    }

    /**
     * Lista todos os pedidos que não foram finalizados.
     *
     * @return Lista de pedidos pendentes.
     */
    @Operation(
            summary = "Lista todos os pedidos não finalizados",
            description = "Retorna uma lista de pedidos não finalizados.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pedidos listados com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro de negócio"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
            }
    )
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

    /**
     * Altera o status de um item específico de um pedido.
     *
     * @param request Objeto contendo o ID do item e o novo status.
     * @return Confirmação da atualização.
     */
    @Operation(
            summary = "Muda o status de um item de pedido",
            description = "Atualiza o status de um item específico de um pedido.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Item atualizado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro de negócio"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
            }
    )
    @PutMapping("/mudar-status-item")
    public ResponseEntity<?> mudarStatusPedidoItem(@RequestBody RequestStatusItemDto request) {
        LoggerUtils.logRequestStart(LOGGER, "mudarStatusPedidoItem", request);
        long startTime = System.currentTimeMillis();
        service.mudarStatusPedidoItem(request);
        StandardResponseDTO response = StandardResponseDTO.builder()
                .mensagem("Item atualizado com sucesso!")
                .build();
        LoggerUtils.logElapsedTime(LOGGER, "mudarStatusPedidoItem", startTime);
        return ResponseEntity.ok(response);
    }

    /**
     * Altera o status de um pedido completo.
     *
     * @param request Objeto contendo o ID do pedido e o novo status.
     * @return Confirmação da atualização.
     */
    @Operation(
            summary = "Muda o status de um pedido",
            description = "Atualiza o status de um pedido completo.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pedido atualizado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro de negócio"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
            }
    )
    @PutMapping("/mudar-status-pedido")
    public ResponseEntity<?> mudarStatusPedido(@RequestBody RequestStatusPedidoDto request) {
        LoggerUtils.logRequestStart(LOGGER, "mudarStatusPedido", request);
        long startTime = System.currentTimeMillis();
        service.mudarStatusPedido(request);
        StandardResponseDTO response = StandardResponseDTO.builder()
                .mensagem("Pedido atualizado com sucesso!")
                .build();
        LoggerUtils.logElapsedTime(LOGGER, "mudarStatusPedido", startTime);
        return ResponseEntity.ok(response);
    }
}
