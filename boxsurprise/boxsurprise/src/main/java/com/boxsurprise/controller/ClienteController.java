package com.boxsurprise.controller;

import com.boxsurprise.dtos.response.PedidoPessoaResponseDto;
import com.boxsurprise.dtos.response.PessoaResponseDto;
import com.boxsurprise.dtos.request.LoginRequestDTO;
import com.boxsurprise.dtos.request.RequestCadastroDto;
import com.boxsurprise.dtos.request.RequestCadastroEnderecoDto;
import com.boxsurprise.dtos.response.*;
import com.boxsurprise.usecase.ClienteService;
import com.boxsurprise.utils.LoggerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClienteController.class);

    @Autowired
    private ClienteService service;

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

    @PostMapping("/cadastrar-endereco")
    public ResponseEntity<?> cadastrarEndereco(@RequestHeader(value = "Authorization") String token, @RequestBody RequestCadastroEnderecoDto request) {
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

    @GetMapping("/buscar-pessoa")
    public ResponseEntity<?> buscarPessoa(@RequestParam String email) {
        LoggerUtils.logRequestStart(LOGGER, "buscarPessoa", email);
        long startTime = System.currentTimeMillis();

        System.out.println(email);

        PessoaResponseDto pessoa = service.buscarPessoa(email);

        StandardResponseDTO response = StandardResponseDTO.builder()
                .mensagem("Pessoa encontrada com sucesso!")
                .dados(pessoa)
                .build();

        LoggerUtils.logElapsedTime(LOGGER, "buscarPessoa", startTime);

        return ResponseEntity.ok(response);
    }





}

