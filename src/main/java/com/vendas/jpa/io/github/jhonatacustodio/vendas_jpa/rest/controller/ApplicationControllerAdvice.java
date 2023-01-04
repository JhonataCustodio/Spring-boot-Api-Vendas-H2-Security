package com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.rest.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.exception.BusinessRuleException;
import com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.exception.PedidoNaoEncontradoException;
import com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.rest.errorHandling.ApiErrors;


@RestControllerAdvice
public class ApplicationControllerAdvice {
    
    @ExceptionHandler(BusinessRuleException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleRegraNegocioException(BusinessRuleException exception){
        String mensagemError = exception.getMessage();
        return new ApiErrors(mensagemError);
    }

    @ExceptionHandler(PedidoNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handlePedidoNotFoundException(PedidoNaoEncontradoException exception){
        String mensagemError = exception.getMessage();
        return new ApiErrors(mensagemError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleMethodValidationException(MethodArgumentNotValidException exception){
        List<String> errors = exception.getBindingResult().getAllErrors().stream().map(
            erro -> erro.getDefaultMessage()
        ).collect(Collectors.toList());
        return new ApiErrors(errors);
    }

}
