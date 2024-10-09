package com.boxsurprise.controller;

import com.boxsurprise.dtos.request.RequestCompraItemDto;
import com.boxsurprise.dtos.response.PedidoResponseDto;
import com.boxsurprise.dtos.response.StandardResponseDTO;
import com.boxsurprise.usecase.CompraService;
import com.boxsurprise.utils.LoggerUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador responsável pelas operações de compra.
 */
@RestController
@RequestMapping("/compra")
@Tag(name = "Compra", description = "Operações relacionadas ao processo de compra e pedidos.")
public class CompraController {

    @Autowired
    private CompraService service;

    private static final Logger LOGGER = LoggerFactory.getLogger(CompraController.class);

    /**
     * Adiciona um item ao pedido do cliente autenticado.
     *
     * @param token   Token JWT do cliente.
     * @param request Dados do item a ser comprado.
     * @return Confirmação da adição do item.
     */
    @Operation(
            summary = "Compra um item",
            description = "Adiciona um item ao pedido do cliente.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Item adicionado ao pedido com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro de negócio"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
            }
    )
    @PostMapping("/comprar-item")
    public ResponseEntity<?> comprarItem(
            @RequestHeader(value = "Authorization") String token,
            @RequestBody RequestCompraItemDto request) {
        LoggerUtils.logRequestStart(LOGGER, "comprarItem", request);
        long startTime = System.currentTimeMillis();
        service.comprarItem(token, request);
        ResponseEntity<StandardResponseDTO> response = ResponseEntity.ok(
                StandardResponseDTO.builder()
                        .mensagem("Item adicionado ao pedido!")
                        .build()
        );
        LoggerUtils.logElapsedTime(LOGGER, "comprarItem", startTime);
        return response;
    }

    /**
     * Busca as informações de um pedido específico.
     *
     * @param idPedido ID do pedido a ser buscado.
     * @return Dados do pedido.
     */
    @Operation(
            summary = "Busca um pedido",
            description = "Retorna as informações de um pedido específico.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
                    @ApiResponse(responseCode = "400", description = "Erro de negócio"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
            }
    )
    @GetMapping("/buscar-pedido")
    public ResponseEntity<?> buscarPedido(@RequestParam Integer idPedido) {
        LoggerUtils.logRequestStart(LOGGER, "buscarPedido", idPedido);
        long startTime = System.currentTimeMillis();
        PedidoResponseDto pedido = service.buscarPedido(idPedido);
        ResponseEntity<StandardResponseDTO> response = ResponseEntity.ok(
                StandardResponseDTO.builder()
                        .mensagem("Pedido encontrado!")
                        .dados(pedido)
                        .build()
        );
        LoggerUtils.logElapsedTime(LOGGER, "buscarPedido", startTime);
        return response;
    }

    /**
     * Finaliza a compra de um pedido específico.
     *
     * @param idPedido ID do pedido a ser finalizado.
     * @return Confirmação da finalização.
     */
    @Operation(
            summary = "Finaliza uma compra",
            description = "Marca um pedido como finalizado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Compra finalizada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro de negócio"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
            }
    )
    @PutMapping("/finalizar-compra")
    public ResponseEntity<?> finalizarCompra(@RequestParam Integer idPedido) {
        LoggerUtils.logRequestStart(LOGGER, "finalizarCompra", idPedido);
        long startTime = System.currentTimeMillis();
        service.finalizarCompra(idPedido);
        StandardResponseDTO response = StandardResponseDTO.builder()
                .mensagem("Compra finalizada com sucesso!")
                .build();
        LoggerUtils.logElapsedTime(LOGGER, "finalizarCompra", startTime);
        return ResponseEntity.ok(response);
    }
}
