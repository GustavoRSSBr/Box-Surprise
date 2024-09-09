package com.boxsurprise.exceptions;

public class SenhaInvalidaException extends NegocioException {
    public SenhaInvalidaException(String mensagem) {
        super(mensagem);
    }
}
