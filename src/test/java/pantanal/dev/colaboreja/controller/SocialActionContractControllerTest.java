    package pantanal.dev.colaboreja.controller;

    import com.fasterxml.jackson.databind.ObjectMapper;
    import org.junit.jupiter.api.BeforeEach;
    import org.junit.jupiter.api.Test;
    import org.mockito.InjectMocks;
    import org.mockito.Mock;
    import org.mockito.Mockito;
    import org.springframework.test.web.servlet.MockMvc;
    import org.springframework.test.web.servlet.setup.MockMvcBuilders;
    import pantanal.dev.colaboreja.DTO.SocialActionContractDTO;
    import pantanal.dev.colaboreja.model.SocialActionContractModel;
    import pantanal.dev.colaboreja.model.SocialActionModel;
    import pantanal.dev.colaboreja.service.SocialActionContractService;
    import pantanal.dev.colaboreja.enumerable.SocialActionContractStatusEnum;

    import static org.mockito.MockitoAnnotations.initMocks;
    import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
    import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

    public class SocialActionContractControllerTest {

        @InjectMocks
        private SocialActionContractController socialActionContractController;

        @Mock
        private SocialActionContractService socialActionContractService;

        private MockMvc mockMvc;

        @BeforeEach
        public void setUp() {
            initMocks(this);
            mockMvc = MockMvcBuilders.standaloneSetup(socialActionContractController).build();
        }

        @Test
        public void testCreateSocialActionContract() throws Exception {
            // Crie um objeto SocialActionModel simulado
            SocialActionModel socialActionModel = new SocialActionModel();
            socialActionModel.setId(1L); // Defina o ID do modelo
            socialActionModel.setName("Ação Social 1");
            // Defina outros campos do modelo

            // Crie um objeto SocialActionContractDTO simulado
            SocialActionContractDTO socialActionContractDTO = SocialActionContractDTO.builder()
                    .keyContract("Contrato123")
                    .statusContract("Ativo")
                    .socialActionId(socialActionModel.getId()) // Associe o SocialActionModel pelo ID
                    .build();

            // Crie uma instância de SocialActionContractModel simulada
            SocialActionContractModel socialActionContractModel = new SocialActionContractModel();
            socialActionContractModel.setId(1L);
            socialActionContractModel.setKeyContract(socialActionContractDTO.getKeyContract());
            socialActionContractModel.setStatusContract(SocialActionContractStatusEnum._3D);
            // Associe o SocialActionModel
            socialActionContractModel.setSocialActionId(socialActionModel);

            // Configure o comportamento do serviço ao chamar o método createSocialActionContract
            Mockito.when(socialActionContractService.createSocialActionContract(socialActionContractDTO)).thenReturn(socialActionContractModel);

            // Execute a requisição POST para o endpoint /api/social-action-contract
            mockMvc.perform(post("/api/social-action-contract")
                            .contentType("application/json")
                            .content(asJsonString(socialActionContractDTO))) // Converta o objeto para JSON
                    .andExpect(status().isCreated()); // Verifique se o status HTTP é 201 (Created)
        }


        // Método utilitário para converter um objeto Java em JSON (se necessário)
        private String asJsonString(final Object obj) {
            try {
                final ObjectMapper mapper = new ObjectMapper();
                return mapper.writeValueAsString(obj);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }
