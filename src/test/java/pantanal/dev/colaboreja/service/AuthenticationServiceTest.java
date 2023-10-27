package pantanal.dev.colaboreja.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import pantanal.dev.colaboreja.DTO.AuthenticationDTO;
import pantanal.dev.colaboreja.DTO.RegisterDTO;
import pantanal.dev.colaboreja.DTO.response.AuthenticationResponse;
import pantanal.dev.colaboreja.enumerable.RoleEnum;
import pantanal.dev.colaboreja.enumerable.TokenTypeEnum;
import pantanal.dev.colaboreja.model.UserModel;
import pantanal.dev.colaboreja.repository.TokenRepository;
import pantanal.dev.colaboreja.repository.UserRepository;
import pantanal.dev.colaboreja.service.AuthenticationService;
import pantanal.dev.colaboreja.service.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AuthenticationServiceTest {

    @InjectMocks
    private AuthenticationService authenticationService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @BeforeEach
    public void setup() {
        // Configurar o comportamento dos mocks aqui, se necessário
    }

    @Test
    public void testRegister() {
        // Criar um objeto RegisterDTO simulado
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setFirstname("John");
        registerDTO.setLastname("Doe");
        registerDTO.setEmail("johndoe@example.com");
        registerDTO.setPassword("password123");
        registerDTO.setRole(RoleEnum.valueOf("ADMIN"));

        // Criar um objeto UserModel simulado
        UserModel userModel = new UserModel();
        userModel.setFirstname(registerDTO.getFirstname());
        userModel.setLastname(registerDTO.getLastname());
        userModel.setEmail(registerDTO.getEmail());
        userModel.setPassword("encodedPassword");
        userModel.setRole(registerDTO.getRole());

        // Configurar o comportamento dos mocks
        when(passwordEncoder.encode(registerDTO.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(UserModel.class))).thenReturn(userModel);
        when(jwtService.generateToken(userModel)).thenReturn("jwtToken");
        when(jwtService.generateRefreshToken(userModel)).thenReturn("refreshToken");

        // Executar o método de registro
        AuthenticationResponse response = authenticationService.register(registerDTO);

        // Verificar se a resposta contém os tokens esperados
        assertEquals("jwtToken", response.getAccessToken());
        assertEquals("refreshToken", response.getRefreshToken());
    }

    @Test
    public void testAuthenticate() {
        // Criar um objeto AuthenticationDTO simulado
        AuthenticationDTO authenticationDTO = new AuthenticationDTO();
        authenticationDTO.setEmail("johndoe@example.com");
        authenticationDTO.setPassword("password123");

        // Criar um objeto UserModel simulado
        UserModel userModel = new UserModel();
        userModel.setFirstname("John");
        userModel.setLastname("Doe");
        userModel.setEmail("johndoe@example.com");
        userModel.setPassword("encodedPassword");

        // Configurar o comportamento dos mocks
        when(userRepository.findByEmail(authenticationDTO.getEmail())).thenReturn(java.util.Optional.of(userModel));
        when(jwtService.generateToken(userModel)).thenReturn("jwtToken");
        when(jwtService.generateRefreshToken(userModel)).thenReturn("refreshToken");

        // Executar o método de autenticação
        AuthenticationResponse response = authenticationService.authenticate(authenticationDTO);

        // Verificar se a resposta contém os tokens esperados
        assertEquals("jwtToken", response.getAccessToken());
        assertEquals("refreshToken", response.getRefreshToken());
    }

}

