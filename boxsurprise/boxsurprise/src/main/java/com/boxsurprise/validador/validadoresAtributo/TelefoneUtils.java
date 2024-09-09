package com.boxsurprise.validador.validadoresAtributo;

import com.boxsurprise.enuns.ErrorCode;
import com.boxsurprise.exceptions.TelefoneInvalidoException;
import java.util.regex.Pattern;

public class TelefoneUtils {

    // Regex para validar números de telefone com caracteres especiais permitidos
    private static final String PHONE_NUMBER_PATTERN = "^[0-9()\\-\\s]+$";
    private static final Pattern pattern = Pattern.compile(PHONE_NUMBER_PATTERN);

    public static void validarNumeroTelefone(String numeroTelefone) {
        if (numeroTelefone == null || numeroTelefone.trim().isEmpty() || !pattern.matcher(numeroTelefone).matches()) {
            throw new TelefoneInvalidoException(ErrorCode.TELEFONE_INVALIDO.getCustomMessage() + numeroTelefone);
        }
    }
}
