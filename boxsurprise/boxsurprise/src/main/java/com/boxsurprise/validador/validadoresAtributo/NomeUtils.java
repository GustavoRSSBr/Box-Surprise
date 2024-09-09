package com.boxsurprise.validador.validadoresAtributo;

import com.boxsurprise.enuns.ErrorCode;
import com.boxsurprise.exceptions.NomeInvalidoException;

public class NomeUtils {

    public static void validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty() || !nome.matches("^[A-Za-zÀ-ÿ ]+$")) {
            throw new NomeInvalidoException(ErrorCode.NOME_INVALIDO.getCustomMessage() + nome);
        }
    }
}
