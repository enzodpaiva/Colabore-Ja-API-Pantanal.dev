package pantanal.dev.colaboreja.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pantanal.dev.colaboreja.DTO.SocialActionCategoryDTO;
import pantanal.dev.colaboreja.model.SocialActionCategoryModel;
import pantanal.dev.colaboreja.service.SocialActionCategoryService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class SocialActionCategoryControllerTest {

    @Autowired
    private SocialActionCategoryController socialActionCategoryController;

    @MockBean
    private SocialActionCategoryService socialActionCategoryService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(socialActionCategoryController).build();
    }

    @Test
    public void testCreateSocialActionCategory() throws Exception {
        // Crie um objeto SocialActionCategoryDTO simulado
        SocialActionCategoryDTO socialActionCategoryDTO = SocialActionCategoryDTO.builder()
                .name("CategoryName")
                .build();

        // Configure o comportamento do serviço ao chamar o método createSocialActionCategory
        when(socialActionCategoryService.createSocialActionCategory(any(SocialActionCategoryModel.class)))
                .thenAnswer(invocation -> {
                    SocialActionCategoryModel argument = invocation.getArgument(0);
                    argument.setId(1L);
                    return argument;
                });

        // Execute a requisição POST para o endpoint /api/social-action-category
        mockMvc.perform(MockMvcRequestBuilders.post("/api/social-action-category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(socialActionCategoryDTO))) // Converta o objeto para JSON
                .andExpect(MockMvcResultMatchers.status().isCreated()); // Verifique se o status HTTP é 201 (Created)
    }


    // Método utilitário para converter um objeto Java em JSON
    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
