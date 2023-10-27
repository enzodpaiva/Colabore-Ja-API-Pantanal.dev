package pantanal.dev.colaboreja.controller;

import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import pantanal.dev.colaboreja.util.ErroResponse;


import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

public class ExceptionControllerTest {

    private final ExceptionController exceptionController = new ExceptionController();

    @Test
    public void testHandleMethodArgumentNotValidException() {
        // Crie uma instância de MethodArgumentNotValidException simulada
        MethodArgumentNotValidException exception = new MethodArgumentNotValidException((MethodParameter) null, mock(BindingResult.class));

        // Crie um objeto BindingResult simulado
        BindingResult bindingResult = exception.getBindingResult();

        // Crie alguns erros simulados para o BindingResult
        FieldError fieldError1 = new FieldError("objectName", "fieldName1", "Error message 1");
        FieldError fieldError2 = new FieldError("objectName", "fieldName2", "Error message 2");

        // Adicione os erros simulados ao BindingResult
        bindingResult.addError(fieldError1);
        bindingResult.addError(fieldError2);

        // Execute o controlador simulado
        ResponseEntity<List<ErroResponse>> responseEntity = exceptionController.handleMethodArgumentNotValidException(exception, null);

        // Verifique se o status da resposta é HttpStatus.BAD_REQUEST
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void testHandleItemNotFoundException() {
        // Crie uma instância de NoSuchElementException simulada
        NoSuchElementException exception = new NoSuchElementException("Element not found");

        // Execute o controlador simulado
        ResponseEntity<ErroResponse> responseEntity = exceptionController.handleItemNotFoundException(exception, null);

        // Verifique se o status da resposta é HttpStatus.NOT_FOUND
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

        ErroResponse erroResponse = responseEntity.getBody();
        assert erroResponse != null;
        assertEquals(NoSuchElementException.class.getName() + ", " + "Element not found", erroResponse.message());

    }

//    @Test
//    public void testHandleInternalServerErrorException() {
//        // Crie uma instância de Exception simulada
//        Exception exception = new Exception("Internal server error");
//
//        // Execute o controlador simulado
//        ResponseEntity<ErroResponse> responseEntity = exceptionController.handleInternalServerErrorException(exception, null);
//
//        // Verifique se o status da resposta é HttpStatus.INTERNAL_SERVER_ERROR
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
//
//        // Verifique se o corpo da resposta contém a mensagem de erro
//        ErroResponse erroResponse = responseEntity.getBody();
//        assert erroResponse != null;
//        assertEquals("Internal server error", erroResponse.message());
//
//        // Verifique se o timeStamp foi definido para uma data válida
//        assertNotNull(erroResponse.timeStamp());
//    }

    @Test
    public void testHandleDuplicateKeyException() {
        // Crie uma instância de DuplicateKeyException simulada
        DuplicateKeyException exception = new DuplicateKeyException("Duplicate key error");

        // Execute o controlador simulado
        ResponseEntity<ErroResponse> responseEntity = exceptionController.handleDuplicateKeyException(exception, null);

        // Verifique se o status da resposta é HttpStatus.CONFLICT
        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());

        // Verifique se o corpo da resposta contém a mensagem de erro
        ErroResponse erroResponse = responseEntity.getBody();
        assert erroResponse != null;
        assertEquals("Duplicate key error", erroResponse.message());

        // Verifique se o timeStamp foi definido para uma data válida
        assertNotNull(erroResponse.timeStamp());
    }

    @Test
    public void testHandleSignatureException() {
        // Crie uma instância de SignatureException simulada
        io.jsonwebtoken.security.SignatureException exception = new io.jsonwebtoken.security.SignatureException("Signature error");

        // Execute o controlador simulado
        ResponseEntity<ErroResponse> responseEntity = exceptionController.handleSignatureException(exception, null);

        // Verifique se o status da resposta é HttpStatus.UNAUTHORIZED
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());

        // Verifique se o corpo da resposta contém a mensagem de erro
        ErroResponse erroResponse = responseEntity.getBody();
        assert erroResponse != null;
        assertEquals("Signature error", erroResponse.message());

        // Verifique se o timeStamp foi definido para uma data válida
        assertNotNull(erroResponse.timeStamp());
    }
}