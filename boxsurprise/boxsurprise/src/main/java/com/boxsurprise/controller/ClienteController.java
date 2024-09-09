package com.boxsurprise.controller;

import com.boxsurprise.dtos.request.RequestCadastroDto;
import com.boxsurprise.dtos.request.RequestCadastroEnderecoDto;
import com.boxsurprise.dtos.response.EnderecoResponseDto;
import com.boxsurprise.dtos.response.StandardResponse;
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
        ResponseEntity<StandardResponse> response = ResponseEntity.ok(
                StandardResponse.builder()
                        .message("Cliente cadastrado com sucesso!")
                        .build()
        );
        LoggerUtils.logElapsedTime(LOGGER, "cadastrarCliente", startTime);
        return response;
    }

    @PostMapping("/cadastrar-endereco/{idCliente}")
    public ResponseEntity<?> cadastrarEndereco(@PathVariable Integer idCliente, @RequestBody RequestCadastroEnderecoDto request) {
        LoggerUtils.logRequestStart(LOGGER, "cadastrarEndereco", request);
        long startTime = System.currentTimeMillis();

        Integer enderecoId = service.cadastrarEndereco(idCliente, request);

        ResponseEntity<StandardResponse> response = ResponseEntity.ok(
                StandardResponse.builder()
                        .message("Endereço cadastrado com sucesso!")
                        .data(enderecoId)
                        .build()
        );

        LoggerUtils.logElapsedTime(LOGGER, "cadastrarEndereco", startTime);
        return response;
    }

    @GetMapping("/listar-enderecos/{idPessoa}")
    public ResponseEntity<StandardResponse> listarEnderecoPessoa(@PathVariable Integer idPessoa) {
        List<EnderecoResponseDto> enderecos = service.listarEnderecoPessoa(idPessoa);

        StandardResponse response = StandardResponse.builder()
                .message("Endereços encontrados com sucesso!")
                .data(enderecos)
                .build();

        return ResponseEntity.ok(response);
    }



}

