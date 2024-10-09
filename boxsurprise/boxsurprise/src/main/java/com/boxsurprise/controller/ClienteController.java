package com.boxsurprise.controller;

import com.boxsurprise.dtos.request.LoginRequestDTO;
import com.boxsurprise.dtos.request.RequestCadastroDto;
import com.boxsurprise.dtos.request.RequestCadastroEnderecoDto;
import com.boxsurprise.dtos.response.EnderecoResponseDto;
import com.boxsurprise.dtos.response.IdResponseDTO;
import com.boxsurprise.dtos.response.PedidoPessoaResponseDto;
import com.boxsurprise.dtos.response.PessoaResponseDto;
import com.boxsurprise.dtos.response.StandardResponseDTO;
import com.boxsurprise.dtos.response.TokenResponseDTO;
import com.boxsurprise.usecase.ClienteService;
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
 * Controlador responsável pelas operações relacionadas ao cliente.
 */
@RestController
@RequestMapping("/cliente")
@Tag(name = "Cliente", description = "Operações relacionadas ao cadastro e gestão de clientes.")
public class ClienteController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClienteController.class);

    @Autowired
    private ClienteService service;

    /**
     * Cadastra um novo cliente no sistema.
     *
     * @param request Dados do cliente a ser cadastrado.
     * @return Confirmação do cadastro.
     */
    @Operation(
            summary = "Cadastra um novo cliente",
            description = "Registra um cliente no sistema com as informações fornecidas.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cliente cadastrado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro de negócio"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
            }
    )
    @PostMapping("/cadastrar-cliente")
    public ResponseEntity<?> cadastrar(@RequestBody RequestCadastroDto request) {
        LoggerUtils.logRequestStart(LOGGER, "cadastrarCliente", request);
        long startTime = System.currentTimeMillis();
        service.salvarDadosCadastrais(request);
        ResponseEntity<StandardResponseDTO> response = ResponseEntity.ok(
                StandardResponseDTO.builder()
                        .mensagem("Cliente cadastrado com sucesso!")
                        .build()
        );
        LoggerUtils.logElapsedTime(LOGGER, "cadastrarCliente", startTime);
        return response;
    }

    /**
     * Realiza a autenticação de um cliente.
     *
     * @param loginRequestDTO Dados de login (email e senha).
     * @return Token JWT para autenticação nas próximas requisições.
     */
    @Operation(
            summary = "Realiza login",
            description = "Autentica o cliente e retorna um token JWT.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro de negócio"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
            }
    )
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        LOGGER.info("Início do método login com request: {}", loginRequestDTO.getEmail());
        long startTime = System.currentTimeMillis();
        TokenResponseDTO tokenResponseDTO = new TokenResponseDTO(service.autenticar(loginRequestDTO));
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        LOGGER.info("Tempo decorrido: " + elapsedTime + " milissegundos");
        return ResponseEntity.ok(
                StandardResponseDTO.builder()
                        .mensagem("Usuário logado com sucesso!")
                        .dados(tokenResponseDTO)
                        .build()
        );
    }

    /**
     * Cadastra um novo endereço para o cliente autenticado.
     *
     * @param token   Token JWT do cliente.
     * @param request Dados do endereço a ser cadastrado.
     * @return ID do endereço cadastrado.
     */
    @Operation(
            summary = "Cadastra um novo endereço",
            description = "Registra um endereço para o cliente autenticado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Endereço cadastrado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro de negócio"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
            }
    )
    @PostMapping("/cadastrar-endereco")
    public ResponseEntity<?> cadastrarEndereco(
            @RequestHeader(value = "Authorization") String token,
            @RequestBody RequestCadastroEnderecoDto request) {
        LoggerUtils.logRequestStart(LOGGER, "cadastrarEndereco", request);
        long startTime = System.currentTimeMillis();
        IdResponseDTO enderecoId = new IdResponseDTO(service.cadastrarEndereco(token, request));
        ResponseEntity<StandardResponseDTO> response = ResponseEntity.ok(
                StandardResponseDTO.builder()
                        .mensagem("Endereço cadastrado com sucesso!")
                        .dados(enderecoId)
                        .build()
        );
        LoggerUtils.logElapsedTime(LOGGER, "cadastrarEndereco", startTime);
        return response;
    }

    /**
     * Lista todos os endereços associados ao cliente autenticado.
     *
     * @param token Token JWT do cliente.
     * @return Lista de endereços.
     */
    @Operation(
            summary = "Lista endereços do cliente",
            description = "Retorna uma lista de endereços associados ao cliente autenticado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Endereços listados com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro de negócio"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
            }
    )
    @GetMapping("/listar-endereco-pessoa")
    public ResponseEntity<?> listarEnderecoPessoa(@RequestHeader(value = "Authorization") String token) {
        LoggerUtils.logRequestStart(LOGGER, "listarEnderecoPessoa", token);
        long startTime = System.currentTimeMillis();
        List<EnderecoResponseDto> enderecos = service.listarEnderecoPessoa(token);
        StandardResponseDTO response = StandardResponseDTO.builder()
                .mensagem("Endereços encontrados com sucesso!")
                .dados(enderecos)
                .build();
        LoggerUtils.logElapsedTime(LOGGER, "listarEnderecoPessoa", startTime);
        return ResponseEntity.ok(response);
    }

    /**
     * Lista todos os pedidos realizados pelo cliente autenticado.
     *
     * @param token Token JWT do cliente.
     * @return Lista de pedidos.
     */
    @Operation(
            summary = "Lista pedidos do cliente",
            description = "Retorna uma lista de pedidos do cliente autenticado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pedidos listados com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro de negócio"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
            }
    )
    @GetMapping("/listar-pedidos-por-pessoa")
    public ResponseEntity<?> listarPedidoPessoa(@RequestHeader(value = "Authorization") String token) {
        LoggerUtils.logRequestStart(LOGGER, "listarPedidoPessoa", token);
        long startTime = System.currentTimeMillis();
        List<PedidoPessoaResponseDto> pedidos = service.listarPedidoPessoa(token);
        StandardResponseDTO response = StandardResponseDTO.builder()
                .mensagem("Pedidos encontrados com sucesso!")
                .dados(pedidos)
                .build();
        LoggerUtils.logElapsedTime(LOGGER, "listarPedidoPessoa", startTime);
        return ResponseEntity.ok(response);
    }

    /**
     * Busca as informações de um cliente pelo email.
     *
     * @param email Email do cliente a ser buscado.
     * @return Dados do cliente.
     */
    @Operation(
            summary = "Busca dados do cliente",
            description = "Retorna as informações do cliente com base no email fornecido.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
                    @ApiResponse(responseCode = "400", description = "Erro de negócio"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
            }
    )
    @GetMapping("/buscar-pessoa")
    public ResponseEntity<?> buscarPessoa(@RequestParam String email) {
        LoggerUtils.logRequestStart(LOGGER, "buscarPessoa", email);
        long startTime = System.currentTimeMillis();
        PessoaResponseDto pessoa = service.buscarPessoa(email);
        StandardResponseDTO response = StandardResponseDTO.builder()
                .mensagem("Pessoa encontrada com sucesso!")
                .dados(pessoa)
                .build();
        LoggerUtils.logElapsedTime(LOGGER, "buscarPessoa", startTime);
        return ResponseEntity.ok(response);
    }
}
