//package pantanal.dev.colaboreja.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.http.MediaType;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import pantanal.dev.colaboreja.DTO.ChangePasswordDTO;
//import pantanal.dev.colaboreja.service.UserService;
//
//import java.security.Principal;
//
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//public class UserControllerTest {
//
//    private MockMvc mockMvc;
//
//    @InjectMocks
//    private UserController userController;
//
//    @Mock
//    private UserService userService;
//
//    private Principal testPrincipal;
//    private ChangePasswordDTO testChangePasswordDTO;
//
//    @BeforeEach
//    public void setUp() {
//        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
//
//        testPrincipal = new UsernamePasswordAuthenticationToken(null, null);
//        testChangePasswordDTO = ChangePasswordDTO.builder()
//                .currentPassword("oldPassword")
//                .newPassword("newPassword")
//                .confirmationPassword("newPassword")
//                .build();
//    }
//
//    @Test
//    public void testChangePassword() throws Exception {
//        // Simule o comportamento do UserService
//        doNothing().when(userService).changePassword(testChangePasswordDTO, testPrincipal);
//
//        // Execute a solicitação PATCH
//        mockMvc.perform(patch("/api/users")
//                        .content(asJsonString(testChangePasswordDTO))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }
//
//    public static String asJsonString(final Object obj) {
//        try {
//            return new ObjectMapper().writeValueAsString(obj);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
