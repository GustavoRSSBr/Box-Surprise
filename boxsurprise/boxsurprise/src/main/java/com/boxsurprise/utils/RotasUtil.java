package com.boxsurprise.utils;

import com.boxsurprise.enuns.TipoUsuario;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RotasUtil {

    private static final Logger logger = LoggerFactory.getLogger(RotasUtil.class);

    public static Map<String, List<TipoUsuario>> getRotas(ResourceLoader resourceLoader) {
        Map<String, List<TipoUsuario>> pathsAndRoles = new HashMap<>();
        Resource resource = resourceLoader.getResource("classpath:rotas.json");

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<PathsAndRoles> rolePathList = objectMapper.readValue(resource.getInputStream(), new TypeReference<List<PathsAndRoles>>() {
            });
            for (PathsAndRoles rolePath : rolePathList) {
                pathsAndRoles.put(rolePath.getPath(), rolePath.getRoles());
            }
        } catch (IOException e) {
            logger.error("Erro ao ler o arquivo de rotas: ", e);
            throw new RuntimeException(e);
        }
        return pathsAndRoles;
    }
}