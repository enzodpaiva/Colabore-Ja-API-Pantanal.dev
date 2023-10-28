package pantanal.dev.colaboreja.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.boot.test.context.SpringBootTest;
import pantanal.dev.colaboreja.model.SocialActionCategoryModel;
import pantanal.dev.colaboreja.repository.SocialActionCategoryRepository;

import org.springframework.dao.DuplicateKeyException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class SocialActionCategoryServiceTest {

    @InjectMocks
    private SocialActionCategoryService socialActionCategoryService;

    @Mock
    private SocialActionCategoryRepository socialActionCategoryRepository;

    @BeforeEach
    public void setup() {
        // Configurar o comportamento dos mocks aqui, se necessário
    }

    @Test
    public void testCreateSocialActionCategory() {
        // Crie um objeto SocialActionCategoryModel para simular a entrada
        SocialActionCategoryModel inputCategory = new SocialActionCategoryModel();
        inputCategory.setName("Teste");

        // Simule o comportamento do repositório para retornar uma contagem vazia
        when(socialActionCategoryRepository.findByNameContainingIgnoreCase(inputCategory.getName()))
                .thenReturn(new ArrayList<>());

        // Simule o comportamento do repositório ao salvar o objeto
        when(socialActionCategoryRepository.save(any(SocialActionCategoryModel.class)))
                .thenReturn(inputCategory);

        SocialActionCategoryModel createdCategory = socialActionCategoryService.createSocialActionCategory(inputCategory);

        // Verificar se o método do repositório foi chamado corretamente
        verify(socialActionCategoryRepository).findByNameContainingIgnoreCase(inputCategory.getName());
        verify(socialActionCategoryRepository).save(inputCategory);

        // Verificar se o resultado corresponde ao objeto de entrada
        assertNotNull(createdCategory);
        assertEquals("Teste", createdCategory.getName());
    }


    @Test
    public void testGetAllSocialActionCategories() {
        // Criar uma lista de objetos SocialActionCategoryModel simulados
        List<SocialActionCategoryModel> socialActionCategories = new ArrayList<>();
        socialActionCategories.add(new SocialActionCategoryModel());
        socialActionCategories.add(new SocialActionCategoryModel());

        // Configurar o comportamento dos mocks
        when(socialActionCategoryRepository.findAll()).thenReturn(socialActionCategories);

        // Executar o método para obter todas as categorias de ação social
        List<SocialActionCategoryModel> result = socialActionCategoryService.getAllSocialActionCategorys();

        // Verificar se o resultado não é nulo e tem o tamanho esperado
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testCreateDuplicateSocialActionCategory() {
        // Crie um objeto SocialActionCategoryModel simulado com um nome que já existe
        SocialActionCategoryModel inputCategory = new SocialActionCategoryModel();
        inputCategory.setName("CategoryName");

        // Simule o comportamento do repositório para retornar uma contagem não vazia
        when(socialActionCategoryRepository.findByNameContainingIgnoreCase(inputCategory.getName()))
                .thenReturn(Collections.singletonList(inputCategory));

        // Configure o mock do repositório para lançar a exceção
        when(socialActionCategoryRepository.save(any(SocialActionCategoryModel.class)))
                .thenThrow(DuplicateKeyException.class);

        assertThrows(DuplicateKeyException.class, () -> socialActionCategoryService.createSocialActionCategory(inputCategory));

        // Verifique se o método do repositório foi chamado corretamente
        verify(socialActionCategoryRepository).findByNameContainingIgnoreCase(inputCategory.getName());

        // Verifique se o método save foi chamado
        verify(socialActionCategoryRepository).save(inputCategory);
    }


    @Test
    public void testGetSocialActionCategoryById() {
        // Criar um objeto SocialActionCategoryModel simulado
        SocialActionCategoryModel socialActionCategory = new SocialActionCategoryModel();
        socialActionCategory.setId(1L);

        // Configurar o comportamento dos mocks
        when(socialActionCategoryRepository.findById(1L)).thenReturn(Optional.of(socialActionCategory));

        // Executar o método para obter uma categoria de ação social pelo ID
        Optional<SocialActionCategoryModel> result = socialActionCategoryService.getSocialActionCategoryById(1L);

        // Verificar se o resultado não é vazio
        assertTrue(result.isPresent());
        // Verificar se o ID do resultado corresponde ao ID esperado
        assertEquals(1L, result.get().getId());
    }

    @Test
    public void testGetNonExistentSocialActionCategoryById() {
        // Configurar o comportamento dos mocks
        when(socialActionCategoryRepository.findById(1L)).thenReturn(Optional.empty());

        // Executar o método para obter uma categoria de ação social pelo ID
        assertThrows(NoSuchElementException.class, () -> socialActionCategoryService.getSocialActionCategoryById(1L));
    }

//    @Test
//    public void testUpdateSocialActionCategory() {
//        // Crie um objeto SocialActionCategoryModel simulado
//        SocialActionCategoryModel existingCategory = new SocialActionCategoryModel();
//        existingCategory.setId(1L);
//        existingCategory.setName("CategoryName");
//
//        // Crie um objeto SocialActionCategoryModel simulado com os detalhes da atualização
//        SocialActionCategoryModel updatedCategory = new SocialActionCategoryModel();
//        updatedCategory.setId(1L);
//        updatedCategory.setName("UpdatedCategoryName");
//
//        // Simule o comportamento do repositório para encontrar a categoria existente
//        when(socialActionCategoryRepository.findById(existingCategory.getId()))
//                .thenReturn(Optional.of(existingCategory));
//
//        // Simule o comportamento do repositório ao salvar a categoria atualizada
//        when(socialActionCategoryRepository.save(any(SocialActionCategoryModel.class)))
//                .thenReturn(updatedCategory);
//
//        // Execute o método de atualização
//        SocialActionCategoryModel result = socialActionCategoryService.updateSocialActionCategory(existingCategory.getId(), updatedCategory);
//
//        // Verifique se o método do repositório foi chamado corretamente
//        verify(socialActionCategoryRepository).findById(existingCategory.getId());
//        verify(socialActionCategoryRepository).save(existingCategory);
//
//        // Verifique se o resultado corresponde à categoria atualizada
//        assertNotNull(result);
//        assertEquals("UpdatedCategoryName", result.getName());
//    }

    @Test
    public void testUpdateNonExistentSocialActionCategory() {
        // Simule o comportamento do repositório para não encontrar a categoria
        when(socialActionCategoryRepository.findById(1L))
                .thenReturn(Optional.empty());

        // Execute o método de atualização e espere uma exceção NoSuchElementException
        assertThrows(NoSuchElementException.class, () -> socialActionCategoryService.updateSocialActionCategory(1L, new SocialActionCategoryModel()));

        // Verifique se o método do repositório foi chamado corretamente
        verify(socialActionCategoryRepository).findById(1L);

        // Certifique-se de que o método save não foi chamado
        verify(socialActionCategoryRepository, never()).save(any(SocialActionCategoryModel.class));
    }

}