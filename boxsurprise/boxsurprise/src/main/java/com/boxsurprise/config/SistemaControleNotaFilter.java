package com.boxsurprise.config;

import com.boxsurprise.dtos.response.ErrorAutenticacaoResponseDTO;
import com.boxsurprise.dtos.response.JwtResponseDTO;
import com.boxsurprise.enuns.ErrorCode;
import com.boxsurprise.enuns.TipoUsuario;
import com.boxsurprise.utils.ErrorResponseFactory;
import com.boxsurprise.utils.JwtUtil;
import com.boxsurprise.utils.RotasUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class SistemaControleNotaFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger LOGGER = LoggerFactory.getLogger(SistemaControleNotaFilter.class);
    @Autowired
    private ResourceLoader resourceLoader;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        LOGGER.info("Início do método doFilterInternal");


        if (!checkPathExistence(request)) {
            response.sendError(HttpStatus.NOT_FOUND.value());
            return;
        }

        if (checkRoleExistence(request)) {
            if (!checkHeadersExistence(request)) {
                sendBadRequestResponse(response, request);
                return;
            } else {
                try {
                    String token = request.getHeader("Authorization").replace("Bearer ", "");
                    JwtResponseDTO jwtResponseDTO = JwtUtil.decodeToken(token);
                    TipoUsuario tipoUsuario = jwtResponseDTO.getTipoUsuario();

                    if (!isRoleAuthorized(request, tipoUsuario)) {
                        sendForbiddenResponse(response, request);
                        return;
                    }

                } catch (Exception e) {
                    LOGGER.error("Erro ao decodificar o token JWT: {}", e.getMessage());
                    sendInvalidTokenResponse(response, request);
                    return;
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean checkPathExistence(HttpServletRequest request) {
        String servletPath = request.getServletPath();
        if (servletPath.contains("/swagger") || servletPath.contains("/api")) {
            return true;
        }
        return RotasUtil.getRotas(resourceLoader).containsKey(servletPath);
    }

    private boolean checkHeadersExistence(HttpServletRequest request) {
        return request.getHeader("Authorization") != null && !request.getHeader("Authorization").isBlank();
    }

    private boolean checkRoleExistence(HttpServletRequest request) {
        List<TipoUsuario> roles = RotasUtil.getRotas(resourceLoader).get(request.getServletPath());

        if (roles == null || roles.isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean isRoleAuthorized(HttpServletRequest request, TipoUsuario tipoUsuario) {
        try {
            return RotasUtil.getRotas(resourceLoader).get(request.getServletPath()).contains(tipoUsuario);
        } catch (Exception e) {
            return false;
        }
    }

    private void sendBadRequestResponse(HttpServletResponse response, HttpServletRequest request) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ErrorAutenticacaoResponseDTO errorResponse
                = ErrorResponseFactory
                .createResponseError(ErrorCode.DESC_BAD_REQUEST_HEADERS.getCustomMessage(), request.getServletPath(), HttpStatus.BAD_REQUEST.value());
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }

    private void sendForbiddenResponse(HttpServletResponse response, HttpServletRequest request) throws IOException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ErrorAutenticacaoResponseDTO errorResponse = ErrorResponseFactory
                .createResponseError(
                        ErrorCode.DESC_ROLE_SEM_PERMISSAO.getCustomMessage(),
                        request.getServletPath(),
                        HttpStatus.FORBIDDEN.value()
                );
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }

    private void sendInvalidTokenResponse(HttpServletResponse response, HttpServletRequest request) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ErrorAutenticacaoResponseDTO errorResponse
                = ErrorResponseFactory.createResponseError(
                ErrorCode.DESC_TOKEN_INVALIDO.getCustomMessage(), request.getServletPath(), HttpStatus.BAD_REQUEST.value());
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
