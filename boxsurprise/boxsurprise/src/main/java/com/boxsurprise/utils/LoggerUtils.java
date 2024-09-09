package com.boxsurprise.utils;

import org.slf4j.Logger;

public class LoggerUtils {

    /**
     * Loga o início de uma requisição.
     *
     * @param logger  O logger a ser utilizado.
     * @param methodName  O nome do método onde o log está sendo feito.
     * @param request  O objeto de request a ser logado.
     */
    public static void logRequestStart(Logger logger, String methodName, Object request) {
        if (logger.isInfoEnabled()) {
            logger.info("Iniciando a operação '{}'. Request: {}", methodName, request);
        }
    }

    /**
     * Loga o tempo decorrido de uma operação.
     *
     * @param logger  O logger a ser utilizado.
     * @param methodName  O nome do método onde o log está sendo feito.
     * @param startTime  O tempo inicial em milissegundos.
     */
    public static void logElapsedTime(Logger logger, String methodName, long startTime) {
        long elapsedTime = System.currentTimeMillis() - startTime;
        if (logger.isInfoEnabled()) {
            logger.info("Finalizando a operação '{}'. Tempo decorrido: {} ms", methodName, elapsedTime);
        }
    }
}
