package pantanal.dev.colaboreja.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import pantanal.dev.colaboreja.DTO.ChangePasswordDTO;
import pantanal.dev.colaboreja.model.UserModel;
import pantanal.dev.colaboreja.repository.UserRepository;
import pantanal.dev.colaboreja.service.UserService;

import java.security.Principal;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        initMocks(this);
    }

//    @Test
//    public void testChangePasswordWithMatchingPasswords() {
//        // Crie um objeto ChangePasswordDTO com senhas correspondentes
//        ChangePasswordDTO changePasswordDTO = ChangePasswordDTO.builder()
//                .currentPassword("oldPassword")
//                .newPassword("newPassword")
//                .confirmationPassword("newPassword")
//                .build();
//
//        // Crie um UserModel simulado com a senha inicial correta
//        UserModel user = new UserModel();
//        user.setPassword("oldPassword");
//
//        // Crie um Principal simulado
//        Principal principal = new UsernamePasswordAuthenticationToken(user, null);
//
//        // Simule o comportamento do PasswordEncoder para senhas correspondentes
//        Mockito.when(passwordEncoder.matches(changePasswordDTO.getCurrentPassword(), user.getPassword())).thenReturn(true);
//
//        // Chame o método changePassword
//        userService.changePassword(changePasswordDTO, principal);
//
//        // Verifique se a senha do usuário foi atualizada corretamente
//        Mockito.verify(userRepository, Mockito.times(1)).save(user);
//        assertEquals("newPassword", user.getPassword());
//    }



    @Test
    public void testChangePasswordWithIncorrectCurrentPassword() {
        // Crie um objeto ChangePasswordDTO com senha atual incorreta
        ChangePasswordDTO changePasswordDTO = ChangePasswordDTO.builder()
                .currentPassword("oldPassword")
                .newPassword("newPassword")
                .confirmationPassword("newPassword")
                .build();

        // Crie um UserModel simulado com senha incorreta
        UserModel user = new UserModel();
        user.setPassword("wrongPassword");

        // Crie um Principal simulado
        Principal principal = new UsernamePasswordAuthenticationToken(user, null);

        // Simule o comportamento do PasswordEncoder para senhas incorretas
        Mockito.when(passwordEncoder.matches(changePasswordDTO.getCurrentPassword(), user.getPassword())).thenReturn(false);

        // Verifique se uma exceção é lançada quando a senha atual está incorreta
        assertThrows(IllegalStateException.class, () -> userService.changePassword(changePasswordDTO, principal));

        // Certifique-se de que o usuário não tenha sido salvo
        Mockito.verify(userRepository, Mockito.never()).save(user);
    }
}
