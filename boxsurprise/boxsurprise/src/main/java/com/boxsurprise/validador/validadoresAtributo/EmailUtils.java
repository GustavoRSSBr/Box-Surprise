package com.boxsurprise.validador.validadoresAtributo;

import com.boxsurprise.enuns.ErrorCode;
import com.boxsurprise.exceptions.EmailInvalidoException;

import java.util.regex.Pattern;

public class EmailUtils {
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
    );

    public static void validarEmail(String email) {
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new EmailInvalidoException(ErrorCode.EMAIL_INVALIDO.getCustomMessage() + email);
        }
    }

}
