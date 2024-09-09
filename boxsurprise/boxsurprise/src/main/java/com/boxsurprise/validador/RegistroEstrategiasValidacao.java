package com.boxsurprise.validador;

import com.boxsurprise.enuns.ErrorCode;
import com.boxsurprise.validador.validadoresObjeto.IEstrategiaValidacao;

import java.util.HashMap;
import java.util.Map;

public class RegistroEstrategiasValidacao {
    private final Map<Class<?>, IEstrategiaValidacao<?>> estrategias = new HashMap<>();

    public <T> void registrar(Class<T> classe, IEstrategiaValidacao<T> estrategia) {
        if (estrategias.containsKey(classe)) {
            throw new IllegalArgumentException(ErrorCode.ESTRATEGIA_DUPLICADA.getCustomMessage() + classe.getName());
        }
        estrategias.put(classe, estrategia);
    }

    @SuppressWarnings("unchecked")
    public <T> IEstrategiaValidacao<T> obterEstrategia(Class<?> classe) {
        return (IEstrategiaValidacao<T>) estrategias.get(classe);
    }
}