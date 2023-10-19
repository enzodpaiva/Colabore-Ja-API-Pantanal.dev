package pantanal.dev.colaboreja.controller;

import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pantanal.dev.colaboreja.util.ErroResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.util.*;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErroResponse>> handleMethodArgumentNotValidException(MethodArgumentNotValidException rnfe, HttpServletRequest request){
        List<ErroResponse> listaErrors= new ArrayList<>();
        List<FieldError> fieldErrors = rnfe.getBindingResult().getFieldErrors();

        fieldErrors.forEach(
                e -> {
                    ErroResponse detalhes = new ErroResponse(e.getField() + ", " + e.getDefaultMessage(), new Date().getTime());
                    listaErrors.add(detalhes);
                });
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(listaErrors);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErroResponse> handleItemNotFoundException(NoSuchElementException rnfe, HttpServletRequest request){

        ErroResponse detalhes = new ErroResponse(rnfe.getClass().getName() + ", " + rnfe.getMessage(), new Date().getTime());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(detalhes);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResponse> handleInternalServerErrorException(Exception rnfe, HttpServletRequest request){

        ErroResponse detalhes = new ErroResponse(rnfe.getMessage(), new Date().getTime());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(detalhes);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ErroResponse> handleDuplicateKeyException(DuplicateKeyException rnfe, HttpServletRequest request){

        ErroResponse detalhes = new ErroResponse(rnfe.getMessage(), new Date().getTime());

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(detalhes);
    }

    @ExceptionHandler(io.jsonwebtoken.security.SignatureException.class)
    public ResponseEntity<ErroResponse> handleSignatureException(SignatureException rnfe, HttpServletRequest request){

        ErroResponse detalhes = new ErroResponse(rnfe.getMessage(), new Date().getTime());

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(detalhes);
    }



}
