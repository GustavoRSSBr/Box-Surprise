package com.boxsurprise.validador.validadoresObjeto;

public interface IEstrategiaValidacao<T> {
    boolean validar(T objeto);
}
