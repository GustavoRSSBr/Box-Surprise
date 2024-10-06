package com.boxsurprise.utils;


import com.boxsurprise.dtos.response.ErrorAutenticacaoResponseDTO;

public class ErrorResponseFactory {
    public static ErrorAutenticacaoResponseDTO createResponseError(String message, String path, Integer httpStatus) {
        return new ErrorAutenticacaoResponseDTO(message, path, httpStatus);
    }
}
