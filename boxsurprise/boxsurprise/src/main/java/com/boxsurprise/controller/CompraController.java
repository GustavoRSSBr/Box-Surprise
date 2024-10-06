package com.boxsurprise.controller;


import com.boxsurprise.dtos.response.PedidoResponseDto;
import com.boxsurprise.dtos.request.RequestCompraItemDto;
import com.boxsurprise.dtos.response.StandardResponse;
import com.boxsurprise.dtos.response.StandardResponseDTO;
import com.boxsurprise.usecase.CompraService;
import com.boxsurprise.utils.LoggerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/compra")
public class CompraController {

    @Autowired
    CompraService service;

    private static final Logger LOGGER = LoggerFactory.getLogger(CompraController.class);


    @PostMapping("/comprar-item")
    public ResponseEntity<?> comprarItem(@RequestHeader(value = "Authorization") String token, @RequestBody RequestCompraItemDto request) {
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

    @GetMapping("/buscar-pedido")
    public ResponseEntity<?> buscarPedido(@RequestParam Integer idPedido){
        LoggerUtils.logRequestStart(LOGGER, "buscarPedido", idPedido);
        long startTime = System.currentTimeMillis();

        PedidoResponseDto pedido = service.buscarPedido(idPedido);
            ResponseEntity<StandardResponse> response = ResponseEntity.ok(
                StandardResponse.builder()
                        .message("Pedido encontrado!")
                        .data(pedido)
                        .build()
            );

            LoggerUtils.logElapsedTime(LOGGER, "buscarPedido", startTime);
            return  response;
    }

    @PutMapping("/finalizar-compra")
    public ResponseEntity<StandardResponse> finalizarCompra(@RequestParam Integer idPedido){
        LoggerUtils.logRequestStart(LOGGER, "finalizarCompra", idPedido);
        long startTime = System.currentTimeMillis();

        service.finalizarCompra(idPedido);

        StandardResponse response = StandardResponse.builder()
                .message("Compra finalizada com sucesso!")
                .build();

        LoggerUtils.logElapsedTime(LOGGER, "buscarPedido", startTime);

        return ResponseEntity.ok(response);
    }
}
