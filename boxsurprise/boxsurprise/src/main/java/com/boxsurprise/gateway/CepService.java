package com.boxsurprise.gateway;

import com.boxsurprise.dtos.response.EnderecoViaCepResponseDto;
import com.boxsurprise.enuns.ErrorCode;
import com.boxsurprise.exceptions.CepException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CepService {
    private static final String CEP_API_URL = "https://viacep.com.br/ws/%s/json/";

    public EnderecoViaCepResponseDto buscaEnderecoPor(String cep) {
        if (!isValidCep(cep)) {
            throw new CepException(ErrorCode.CEP_INVALIDO.getCustomMessage() + cep);
        }

        RestTemplate restTemplate = new RestTemplate();
        EnderecoViaCepResponseDto enderecoViaCepResponseDto = restTemplate.getForObject(String.format(CEP_API_URL, cep), EnderecoViaCepResponseDto.class);

        if (enderecoViaCepResponseDto == null) {
            throw new CepException(ErrorCode.CEP_NAO_ENCONTRADO.getCustomMessage() + cep);
        }

        return  enderecoViaCepResponseDto;
    }

    private boolean isValidCep(String cep) {
        return cep != null && cep.matches("\\d{8}");
    }
}
