package com.boxsurprise.validador;

import com.boxsurprise.enuns.ErrorCode;
import com.boxsurprise.validador.validadoresObjeto.IEstrategiaValidacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Validador {


    private final RegistroEstrategiasValidacao registro;

    @Autowired
    public Validador(RegistroEstrategiasValidacao registro) {
        this.registro = registro;
    }

    public <T> boolean validar(T objeto) {
        IEstrategiaValidacao<T> estrategia = registro.obterEstrategia(objeto.getClass());
        if (estrategia != null) {
            return estrategia.validar(objeto);
        }
        throw new IllegalArgumentException(ErrorCode.ESTRATEGIA_NAO_ENCONTRADA.getCustomMessage() + objeto.getClass().getName());
    }
}