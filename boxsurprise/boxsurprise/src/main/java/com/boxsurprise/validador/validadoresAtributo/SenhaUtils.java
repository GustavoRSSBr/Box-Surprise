package com.boxsurprise.validador.validadoresAtributo;

import com.boxsurprise.enuns.ErrorCode;
import com.boxsurprise.exceptions.SenhaInvalidaException;

import java.util.regex.Pattern;

public class SenhaUtils {
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$"
    );

    public static void validarSenha(String senha) {
        if (senha == null || !PASSWORD_PATTERN.matcher(senha).matches()) {
            throw new SenhaInvalidaException(ErrorCode.SENHA_INVALIDA.getCustomMessage());
        }
    }
}
