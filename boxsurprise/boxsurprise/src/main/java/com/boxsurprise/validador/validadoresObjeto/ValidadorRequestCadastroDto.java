package com.boxsurprise.validador.validadoresObjeto;

import com.boxsurprise.dtos.request.RequestCadastroDto;
import com.boxsurprise.validador.validadoresAtributo.*;

public class ValidadorRequestCadastroDto implements IEstrategiaValidacao<RequestCadastroDto>{
    @Override
    public boolean validar(RequestCadastroDto objeto) {

        NomeUtils.validarNome(objeto.getNome());
        TelefoneUtils.validarNumeroTelefone(objeto.getTelefone());
        CpfUtils.validarCpf(objeto.getCpf());
        DataUtils.validarDataNascimento(objeto.getDataNascimento());
        EmailUtils.validarEmail(objeto.getEmail());
        SenhaUtils.validarSenha(objeto.getSenha());

        return true;
    }
}
