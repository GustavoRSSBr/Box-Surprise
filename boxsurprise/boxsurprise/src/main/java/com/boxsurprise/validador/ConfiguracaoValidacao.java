package com.boxsurprise.validador;

import com.boxsurprise.dtos.request.RequestCadastroDto;
import com.boxsurprise.dtos.request.RequestProdutoDto;
import com.boxsurprise.validador.validadoresObjeto.ValidadorRequestCadastroDto;
import com.boxsurprise.validador.validadoresObjeto.ValidadorRequestProdutoDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfiguracaoValidacao {

    @Bean
    public RegistroEstrategiasValidacao obterRegistro() {
        RegistroEstrategiasValidacao registro = new RegistroEstrategiasValidacao();
        configurarRegistro(registro);
        return registro;
    }

    private void configurarRegistro(RegistroEstrategiasValidacao registro) {
        // Local para registro de estratégia, onde cada classe deve conter apenas uma estratégia de validação
        registro.registrar(RequestCadastroDto.class, new ValidadorRequestCadastroDto());
        registro.registrar(RequestProdutoDto.class, new ValidadorRequestProdutoDto());

        //outros registros
    }
}