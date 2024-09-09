package com.boxsurprise.controller;

import com.boxsurprise.gateway.AnaliseServiceChatGPT;
import com.boxsurprise.usecase.AdmService;
import com.boxsurprise.usecase.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/adm")
public class AdmController {

    @Autowired
    AdmService service;

    @PostMapping("/gerar-analise/{idProduto}")
    public Mono<String> GerarAnaliseProdutoID(@PathVariable Integer idProduto) {
        return service.criarAnalise(idProduto);
    }


}
