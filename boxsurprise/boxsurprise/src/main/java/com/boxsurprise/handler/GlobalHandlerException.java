package com.boxsurprise.handler;

import com.boxsurprise.dtos.response.ErrorResponseDto;
import com.boxsurprise.enuns.ErrorCode;
import com.boxsurprise.exceptions.NegocioException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalHandlerException {

    private static final Logger logger = LoggerFactory.getLogger(GlobalHandlerException.class);

    @ExceptionHandler(NegocioException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponseDto> handleNegocioException(NegocioException e) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorResponseDto> handleAllExceptions(Exception e) {
        logger.error("GenericException: {}", e.getMessage(), e);
        ErrorResponseDto errorResponse = new ErrorResponseDto(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponseDto> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        logger.error("JsonParseException: {}", e.getMessage(), e);
        ErrorResponseDto errorResponse = new ErrorResponseDto(ErrorCode.DADO_INVALIDO.getCustomMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataAccessException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponseDto> handleDataAccessException(DataAccessException e) {
        logger.error("DataAccessException: {}", e.getMessage(), e);
        String errorMessage = e.getMostSpecificCause().getMessage();
        ErrorCode errorCode = ErrorCode.fromMessage(errorMessage);

        if (errorCode == ErrorCode.OUTRO_ERRO) {
            ErrorResponseDto errorResponse = new ErrorResponseDto(errorMessage);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            ErrorResponseDto errorResponse = new ErrorResponseDto(errorCode.getCustomMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }
}
