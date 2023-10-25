package pantanal.dev.colaboreja.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pantanal.dev.colaboreja.DTO.RegisterDTO;
import pantanal.dev.colaboreja.DTO.response.AuthenticationResponse;
import pantanal.dev.colaboreja.enumerable.RoleEnum;
import pantanal.dev.colaboreja.service.AuthenticationService;


import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthenticationControllerTest {

    @InjectMocks
    private AuthenticationController authenticationController;

    @Mock
    private AuthenticationService authenticationService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();
    }

    @Test
    public void testRegisterAdmin() throws Exception {
        // Crie um objeto RegisterDTO simulado
        RegisterDTO registerDTO = RegisterDTO.builder()
                .firstname("John")
                .lastname("Doe")
                .email("john.doe@example.com")
                .password("password123")
                .role(RoleEnum.ADMIN)
                .build();

        // Crie uma instância de AuthenticationResponse simulada
        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                .accessToken("seu_token_de_acesso")
                .refreshToken("seu_refresh_token")
                .build();

        // Configure o comportamento do serviço ao chamar o método register
        Mockito.when(authenticationService.register(registerDTO)).thenReturn(authenticationResponse);

        // Execute a requisição POST para o endpoint /api/auth/register
        mockMvc.perform(post("/api/auth/register")
                        .contentType("application/json")
                        .content(asJsonString(registerDTO))) // Converta o objeto para JSON
                .andExpect(status().isOk()); // Verifique se o status HTTP é 200 (OK)
    }
    @Test
    public void testRegisterUser() throws Exception {
        // Crie um objeto RegisterDTO simulado
        RegisterDTO registerDTO = RegisterDTO.builder()
                .firstname("John")
                .lastname("Doe")
                .email("john.doe@example.com")
                .password("password123")
                .build();

        // Crie uma instância de AuthenticationResponse simulada
        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                .accessToken("seu_token_de_acesso")
                .refreshToken("seu_refresh_token")
                .build();

        // Configure o comportamento do serviço ao chamar o método register
        Mockito.when(authenticationService.register(registerDTO)).thenReturn(authenticationResponse);

        // Execute a requisição POST para o endpoint /api/auth/register
        mockMvc.perform(post("/api/auth/register")
                        .contentType("application/json")
                        .content(asJsonString(registerDTO))) // Converta o objeto para JSON
                .andExpect(status().isOk()); // Verifique se o status HTTP é 200 (OK)
    }

    // Método utilitário para converter um objeto Java em JSON
    private String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
