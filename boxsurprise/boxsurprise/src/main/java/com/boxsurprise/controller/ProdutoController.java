package com.boxsurprise.controller;

import com.boxsurprise.dtos.request.RequestProdutoDto;
import com.boxsurprise.dtos.response.ProdutoResponseDto;
import com.boxsurprise.dtos.response.StandardResponseDTO;
import com.boxsurprise.usecase.ProdutoService;
import com.boxsurprise.utils.LoggerUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador responsável pelas operações relacionadas aos produtos.
 */
@RestController
@RequestMapping("/produto")
@Tag(name = "Produto", description = "Operações de cadastro, busca e alteração de produtos.")
public class ProdutoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProdutoController.class);

    @Autowired
    private ProdutoService service;

    /**
     * Cadastra um novo produto no sistema.
     *
     * @param request Dados do produto a ser cadastrado.
     * @return Confirmação do cadastro.
     */
    @Operation(
            summary = "Cadastra um novo produto",
            description = "Adiciona um produto ao sistema.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Produto cadastrado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro de negócio"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
            }
    )
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

    /**
     * Busca um produto cadastrado pelo ID.
     *
     * @param idProduto ID do produto a ser buscado.
     * @return Dados do produto.
     */
    @Operation(
            summary = "Busca um produto por ID",
            description = "Retorna as informações de um produto com base no ID fornecido.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Produto encontrado"),
                    @ApiResponse(responseCode = "400", description = "Erro de negócio"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
            }
    )
    @GetMapping("/buscar-produto-cadastrado")
    public ResponseEntity<?> buscarProdutoCadastradoPorId(@RequestParam Integer idProduto) {
        long startTime = System.currentTimeMillis();
        ProdutoResponseDto produtoResponse = service.buscarProdutoPorId(idProduto);
        LoggerUtils.logElapsedTime(LOGGER, "buscarProdutoPorId", startTime);
        return ResponseEntity.ok(produtoResponse);
    }

    /**
     * Lista todos os produtos cadastrados no sistema.
     *
     * @return Lista de produtos.
     */
    @Operation(
            summary = "Lista todas as caixas pré definidas",
            description = "Retorna uma lista de todos os produtos no sistema (CAIXAS PRÉ-DEFINIDAS).",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Produtos listados com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro de negócio"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
            }
    )
    @GetMapping("/listar-produtos-cadastrados")
    public ResponseEntity<?> listarProdutosCadastrados() {
        long startTime = System.currentTimeMillis();
        List<ProdutoResponseDto> response = service.listarProdutosCadastrados();
        LoggerUtils.logElapsedTime(LOGGER, "listarProdutosCadastrados", startTime);
        return ResponseEntity.ok(response);
    }

    /**
     * Altera os dados de um produto cadastrado.
     *
     * @param idProduto ID do produto a ser alterado.
     * @param request   Novos dados do produto.
     * @return Confirmação da alteração.
     */
    @Operation(
            summary = "Altera um produto cadastrado",
            description = "Atualiza as informações de um produto existente.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Produto alterado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro de negócio"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
            }
    )
    @PutMapping("/alterar-produto-cadastrado")
    public ResponseEntity<?> alterarProdutoCadastrado(
            @RequestParam Integer idProduto,
            @RequestBody RequestProdutoDto request) {
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
