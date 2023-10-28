package pantanal.dev.colaboreja.DTO;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthenticationDTOTest {

    @Test
    public void testAuthenticationDTO() {
        // Crie um objeto AuthenticationDTO simulado
        AuthenticationDTO authenticationDTO = AuthenticationDTO.builder()
                .email("test@example.com")
                .password("password123")
                .build();

        // Teste se os valores estão corretos
        assertEquals("test@example.com", authenticationDTO.getEmail());
        assertEquals("password123", authenticationDTO.getPassword());
    }
}

