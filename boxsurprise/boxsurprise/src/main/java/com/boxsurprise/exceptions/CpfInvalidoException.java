package com.boxsurprise.exceptions;

public class CpfInvalidoException extends NegocioException {
    public CpfInvalidoException(String mensagem) {
        super(mensagem);
    }
}
