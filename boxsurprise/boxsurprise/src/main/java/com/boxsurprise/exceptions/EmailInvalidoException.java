package com.boxsurprise.exceptions;

public class EmailInvalidoException extends NegocioException {
    public EmailInvalidoException(String mensagem) {
        super(mensagem);
    }
}
