package pantanal.dev.colaboreja.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import pantanal.dev.colaboreja.controller.SocialActionController;
import pantanal.dev.colaboreja.DTO.SocialActionDTO;
import pantanal.dev.colaboreja.model.SocialActionCategoryModel;
import pantanal.dev.colaboreja.model.SocialActionModel;
import pantanal.dev.colaboreja.service.SocialActionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.MockitoAnnotations.*;
@SpringBootTest(properties = "spring.security.enabled=false")
@WithMockUser(username = "testuser", roles = {"ADMIN"})
@AutoConfigureMockMvc
public class SocialActionControllerTest {

    @InjectMocks
    private SocialActionController socialActionController;

    @Mock
    private SocialActionService socialActionService;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testCreateSocialAction() {

        SocialActionDTO socialActionDTO = SocialActionDTO.builder()
                .name("Teste")
                .description("Descricao")
                .initDateTime(new Date())
                .finishDateTime(new Date())
                .build();

        SocialActionModel socialActionModel = SocialActionModel.builder()
                .id(1l)
                .name(socialActionDTO.getName())
                .description(socialActionDTO.getDescription())
                .initDateTime(socialActionDTO.getInitDateTime())
                .finishDateTime(socialActionDTO.getFinishDateTime())
                .socialActionCategoryId(new SocialActionCategoryModel())
                .build();

        socialActionModel.setSocialActionCategoryId(new SocialActionCategoryModel());

        // Simule o comportamento do serviço ao chamar createSocialAction
        Mockito.when(socialActionService.createSocialAction(socialActionDTO)).thenReturn(socialActionModel);

        // Chame o método no controlador
        ResponseEntity<SocialActionDTO> response = socialActionController.createSocialAction(socialActionDTO);

        // Verifique se a resposta tem o status HTTP esperado
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

    }
//    @Test
//    public void testCreateSocialActionNomeNull() {
//
//        SocialActionDTO socialActionDTO = SocialActionDTO.builder()
//                .name(null)
//                .description("Descricao")
//                .initDateTime(new Date())
//                .finishDateTime(new Date())
//                .build();
//
//
//
//        // Chame o método no controlador
//        ResponseEntity<SocialActionDTO> response = socialActionController.createSocialAction(socialActionDTO);
//
//        // Verifique se a resposta tem o status HTTP esperado
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//
//    }
//    @Test
//        public void testDeleteSocialAction() throws Exception {
//
//        Long validSocialActionId = 1L;
//
//        mockMvc.perform(delete("/api/social-action/" + validSocialActionId)
//                    .contentType(MediaType.APPLICATION_JSON))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//            .andExpect(jsonPath("$.success").value(true))
//            .andExpect(jsonPath("$.message").value("Ação social deletada com sucesso"));
//}



}
