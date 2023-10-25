package pantanal.dev.colaboreja.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import pantanal.dev.colaboreja.DTO.SocialActionDTO;
import pantanal.dev.colaboreja.model.SocialActionCategoryModel;
import pantanal.dev.colaboreja.model.SocialActionModel;
import pantanal.dev.colaboreja.repository.SocialActionRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class SocialActionServiceTest {

    @InjectMocks
    private SocialActionService socialActionService;

    @Mock
    private SocialActionRepository socialActionRepository;

    @Mock
    private SocialActionCategoryService socialActionCategoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateSocialAction() {
        // Crie um objeto SocialActionDTO simulado
        SocialActionDTO socialActionDTO = new SocialActionDTO();
        socialActionDTO.setSocialActionCategoryId(1L); // ID da categoria simulada
        socialActionDTO.setName("Ação Social Teste");
        socialActionDTO.setDescription("Descrição da Ação Social Teste");

        // Crie um objeto SocialActionCategoryModel simulado
        SocialActionCategoryModel categoryModel = new SocialActionCategoryModel();
        categoryModel.setId(1L);

        // Simule o comportamento do serviço de categoria de ação social para retornar a categoria simulada
        when(socialActionCategoryService.getSocialActionCategoryById(socialActionDTO.getSocialActionCategoryId()))
                .thenReturn(Optional.of(categoryModel));

        // Crie um objeto SocialActionModel simulado que será retornado pelo repositório
        SocialActionModel socialActionModel = new SocialActionModel();
        socialActionModel.setId(1L);
        socialActionModel.setName(socialActionDTO.getName());
        socialActionModel.setDescription(socialActionDTO.getDescription());
        socialActionModel.setSocialActionCategoryId(categoryModel);

        // Simule o comportamento do repositório para retornar o objeto SocialActionModel simulado após o salvamento
        when(socialActionRepository.save(Mockito.any(SocialActionModel.class)))
                .thenReturn(socialActionModel);

        SocialActionModel createdSocialAction = socialActionService.createSocialAction(socialActionDTO);

        // Verifique se o método do serviço de categoria de ação social foi chamado corretamente
        Mockito.verify(socialActionCategoryService).getSocialActionCategoryById(socialActionDTO.getSocialActionCategoryId());

        // Verifique se o método do repositório foi chamado corretamente
        Mockito.verify(socialActionRepository).save(Mockito.any(SocialActionModel.class));

        // Verifique se o resultado corresponde ao objeto de ação social simulado
        assertEquals(socialActionDTO.getName(), createdSocialAction.getName());
        assertEquals(socialActionDTO.getDescription(), createdSocialAction.getDescription());
        assertEquals(socialActionDTO.getSocialActionCategoryId(), createdSocialAction.getSocialActionCategoryId().getId());
    }
    @Test
    public void testGetAllSocialActions() {
        // Crie uma lista de objetos SocialActionModel simulados
        List<SocialActionModel> socialActions = new ArrayList<>();
        socialActions.add(new SocialActionModel());
        socialActions.add(new SocialActionModel());

        // Simule o comportamento do repositório para retornar a lista simulada
        when(socialActionRepository.findAll()).thenReturn(socialActions);

       List<SocialActionModel> result = socialActionService.getAllSocialActions();

        // Verifique se o resultado não é nulo e tem o tamanho esperado
        assertEquals(socialActions.size(), result.size());
    }

    @Test
    public void testGetSocialActionById() {
        // Crie um objeto SocialActionModel simulado
        SocialActionModel socialAction = new SocialActionModel();
        socialAction.setId(1L);

        // Simule o comportamento do repositório para retornar o objeto simulado quando o método findById é chamado
        when(socialActionRepository.findById(1L)).thenReturn(Optional.of(socialAction));

        Optional<SocialActionModel> result = socialActionService.getSocialActionById(1L);

        // Verifique se o resultado não é vazio
        assertEquals(socialAction, result.get());
    }

    @Test
    public void testGetNonExistentSocialActionById() {
        // Simule o comportamento do repositório para retornar um resultado vazio quando o método findById é chamado
        when(socialActionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> socialActionService.getSocialActionById(1L));
    }

    @Test
    public void testFindSocialActionWithText() {
        // Crie uma lista de objetos SocialActionModel simulados
        List<SocialActionModel> socialActions = new ArrayList<>();
        socialActions.add(new SocialActionModel());
        socialActions.add(new SocialActionModel());

        // Simule o comportamento do repositório para retornar a lista simulada quando o método findByNameContainingIgnoreCase é chamado
        when(socialActionRepository.findByNameContainingIgnoreCase("Texto de Exemplo")).thenReturn(socialActions);

        List<SocialActionModel> result = socialActionService.findSocialAction("Texto de Exemplo");

        // Verifique se o resultado não é nulo e tem o tamanho esperado
        assertEquals(socialActions.size(), result.size());
    }

    @Test
    public void testFindSocialActionWithoutText() {
        // Crie uma lista de objetos SocialActionModel simulados
        List<SocialActionModel> socialActions = new ArrayList<>();
        socialActions.add(new SocialActionModel());
        socialActions.add(new SocialActionModel());

        // Simule o comportamento do repositório para retornar a lista simulada quando o método findAll é chamado
        when(socialActionRepository.findAll()).thenReturn(socialActions);

        List<SocialActionModel> result = socialActionService.findSocialAction("");

        // Verifique se o resultado não é nulo e tem o tamanho esperado
        assertEquals(socialActions.size(), result.size());
    }

//    @Test
//    public void testUpdateSocialAction() {
//        // Crie um objeto SocialActionModel simulado
//        SocialActionModel socialAction = new SocialActionModel();
//        socialAction.setId(1L);
//
//        // Crie um objeto SocialActionDTO simulado com dados de atualização
//        SocialActionDTO socialActionDetails = new SocialActionDTO();
//        socialActionDetails.setName("Nome Atualizado");
//        socialActionDetails.setDescription("Descrição Atualizada");
//        socialActionDetails.setInitDateTime(new Date());
//        socialActionDetails.setFinishDateTime(new Date());
//        socialActionDetails.setSocialActionCategoryId(2L);
//
//        // Crie um objeto SocialActionModel simulado para a categoria de ação social
//        SocialActionCategoryModel socialActionCategory = new SocialActionCategoryModel();
//        socialActionCategory.setId(2L);
//
//        // Simule o comportamento do serviço de categoria de ação social para retornar o objeto simulado
//        when(socialActionCategoryService.getSocialActionCategoryById(2L))
//                .thenReturn(Optional.of(socialActionCategory));
//
//        // Simule o comportamento do repositório para retornar o objeto simulado quando o método findById é chamado
//        when(socialActionRepository.findById(1L)).thenReturn(Optional.of(socialAction));
//
//        SocialActionModel updatedSocialAction = socialActionService.updateSocialAction(1L, socialActionDetails);
//
//        // Verifique se o resultado não é nulo
//        assertNotNull(updatedSocialAction);
//
//        // Verifique se os atributos foram atualizados corretamente
//        assertEquals(socialActionDetails.getName(), updatedSocialAction.getName());
//        assertEquals(socialActionDetails.getDescription(), updatedSocialAction.getDescription());
//        assertEquals(socialActionDetails.getInitDateTime(), updatedSocialAction.getInitDateTime());
//        assertEquals(socialActionDetails.getFinishDateTime(), updatedSocialAction.getFinishDateTime());
//        assertEquals(socialActionCategory, updatedSocialAction.getSocialActionCategoryId());
//    }
//

    @Test
    public void testUpdateNonExistentSocialAction() {
        // Crie um objeto SocialActionDTO simulado com dados de atualização
        SocialActionDTO socialActionDetails = new SocialActionDTO();
        socialActionDetails.setName("Nome Atualizado");

        // Simule o comportamento do repositório para retornar um resultado vazio quando o método findById é chamado
        when(socialActionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> socialActionService.updateSocialAction(1L, socialActionDetails));
    }
}
