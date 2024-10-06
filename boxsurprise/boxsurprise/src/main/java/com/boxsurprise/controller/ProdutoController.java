package com.boxsurprise.controller;

import com.boxsurprise.dtos.request.RequestProdutoDto;
import com.boxsurprise.dtos.response.ProdutoResponseDto;
import com.boxsurprise.dtos.response.StandardResponseDTO;
import com.boxsurprise.usecase.ProdutoService;
import com.boxsurprise.utils.LoggerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClienteController.class);

    @Autowired
    private ProdutoService service;

    @PostMapping("/cadastrar-produto")
    public ResponseEntity<?> cadastrarProduto(@RequestBody RequestProdutoDto request) {
        LoggerUtils.logRequestStart(LOGGER, "cadastrarProduto", request);
        long startTime = System.currentTimeMillis();

        service.salvarDadosProduto(request);

        ResponseEntity<StandardResponseDTO> response = ResponseEntity.ok(
                StandardResponseDTO.builder()
                        .mensagem("Produto cadastrado com sucesso!")
                        .build()
        );

        LoggerUtils.logElapsedTime(LOGGER, "cadastrarProduto", startTime);
        return response;
    }

    @GetMapping("/buscar-produto-cadastrado")
    public ResponseEntity<?> buscarProdutoCadastradoPorId(@RequestParam Integer idProduto) {
        long startTime = System.currentTimeMillis();

        ProdutoResponseDto produtoResponse = service.buscarProdutoPorId(idProduto);

        LoggerUtils.logElapsedTime(LOGGER, "buscarProdutoPorId", startTime);

        return new ResponseEntity<>(produtoResponse, HttpStatus.OK);
    }


    @GetMapping("/listar-produtos-cadastrados")
    public ResponseEntity<?> listarProdutosCadastrados() {
        long startTime = System.currentTimeMillis();

        List<ProdutoResponseDto> response = service.listarProdutosCadastrados();

        LoggerUtils.logElapsedTime(LOGGER, "listar", startTime);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/alterar-produto-cadastrado")
    public ResponseEntity<?> alterarProdutoCadastrado( @RequestParam Integer idProduto, @RequestBody RequestProdutoDto request) {
        LoggerUtils.logRequestStart(LOGGER, "alterarProdutoCadastrado", request);
        long startTime = System.currentTimeMillis();

        service.alterarProdutoCadastrado(idProduto, request);

        ResponseEntity<StandardResponseDTO> response = ResponseEntity.ok(
                StandardResponseDTO.builder()
                        .mensagem("Produto alterado com sucesso!")
                        .build()
        );

        LoggerUtils.logElapsedTime(LOGGER, "alterarProdutoCadastrado", startTime);

        return response;
    }
}
