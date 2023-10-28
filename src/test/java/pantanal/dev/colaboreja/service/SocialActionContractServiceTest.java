package pantanal.dev.colaboreja.service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import pantanal.dev.colaboreja.DTO.SocialActionContractDTO;
import pantanal.dev.colaboreja.enumerable.SocialActionContractStatusEnum;
import pantanal.dev.colaboreja.model.SocialActionContractModel;
import pantanal.dev.colaboreja.model.SocialActionModel;
import pantanal.dev.colaboreja.model.UserModel;
import pantanal.dev.colaboreja.repository.SocialActionContractRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class SocialActionContractServiceTest {

    @InjectMocks
    private SocialActionContractService socialActionContractService;

    @Mock
    private SocialActionContractRepository socialActionContractRepository;

    @Mock
    private SocialActionService socialActionService;
    @Mock
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateSocialActionContract() {
        // Crie um objeto SocialActionContractDTO simulado
        SocialActionContractDTO contractDTO = new SocialActionContractDTO();
        contractDTO.setSocialActionId(1L);
        contractDTO.setKeyProcess("ContractKey");
        contractDTO.setKeyDocument("DocumentKey");
        contractDTO.setCodeDocumentPdsign("143242");
        contractDTO.setStatusContract(SocialActionContractStatusEnum.CREATED);

        // Crie um objeto SocialActionModel simulado
        SocialActionModel socialAction = new SocialActionModel();
        socialAction.setId(1L);

        UserModel colaborator = new UserModel();
        colaborator.setId(1);

        // Simule o comportamento do serviço de ação social para retornar um objeto SocialActionModel simulado
        Mockito.when(socialActionService.getSocialActionById(contractDTO.getSocialActionId()))
                .thenReturn(Optional.of(socialAction));

        // Simule o comportamento do serviço de ação social para retornar um objeto SocialActionModel simulado
        Mockito.when(userService.getUserById(contractDTO.getColaborator()))
                .thenReturn(Optional.of(colaborator));

        // Crie um objeto SocialActionContractModel simulado
        SocialActionContractModel contractModel = new SocialActionContractModel();
        contractModel.setId(1L);
        contractModel.setKeyProcess("ContractKey");
        contractModel.setKeyDocument("DocumentKey");
        contractModel.setCodeDocumentPdsign("143242");
        contractModel.setStatusContract(SocialActionContractStatusEnum.CREATED);

        // Simule o comportamento do repositório ao salvar o objeto
        Mockito.when(socialActionContractRepository.save(Mockito.any(SocialActionContractModel.class)))
                .thenReturn(contractModel);

      SocialActionContractModel createdContract = socialActionContractService.createSocialActionContract(contractDTO);

        // Verifique se o método do serviço de ação social foi chamado corretamente
        Mockito.verify(socialActionService).getSocialActionById(contractDTO.getSocialActionId());

        // Verifique se o método do repositório foi chamado corretamente
        Mockito.verify(socialActionContractRepository).save(Mockito.any(SocialActionContractModel.class));

        // Verifique se o resultado corresponde ao objeto de contrato simulado
        Mockito.verify(socialActionService).getSocialActionById(contractDTO.getSocialActionId());
    }

    @Test
    public void testGetAllSocialActionContracts() {
        // Crie uma lista simulada de contratos de ação social
        List<SocialActionContractModel> contractList = new ArrayList<>();
        contractList.add(new SocialActionContractModel());
        contractList.add(new SocialActionContractModel());

        // Simule o comportamento do repositório para retornar a lista simulada
        Mockito.when(socialActionContractRepository.findAll())
                .thenReturn(contractList);

        List<SocialActionContractModel> result = socialActionContractService.getAllSocialActionContracts();

        // Verifique se o método do repositório foi chamado corretamente
        Mockito.verify(socialActionContractRepository).findAll();

        // Verifique se o resultado não é nulo e tem o tamanho esperado
        assertNotNull(result);
        assertEquals(2, result.size());
    }
    @Test
    public void testGetExistingSocialActionContractById() {
       Long contractId = 1L;

        // Crie um contrato de ação social simulado com o ID especificado
        SocialActionContractModel contract = new SocialActionContractModel();
        contract.setId(contractId);

        // Simule o comportamento do repositório para retornar o contrato simulado com base no ID
        Mockito.when(socialActionContractRepository.findById(contractId))
                .thenReturn(Optional.of(contract));

        Optional<SocialActionContractModel> result = socialActionContractService.getSocialActionContractById(contractId);

        // Verifique se o método do repositório foi chamado corretamente
        Mockito.verify(socialActionContractRepository).findById(contractId);

        // Verifique se o resultado não é vazio e se corresponde ao contrato simulado
        assertTrue(result.isPresent());
        assertEquals(contractId, result.get().getId());
    }

    @Test
    public void testGetNonExistentSocialActionContractById() {
        // ID de um contrato que não existe (por exemplo, ID 99)
        Long nonExistentContractId = 99L;

        // Simule o comportamento do repositório para não retornar um contrato simulado
        Mockito.when(socialActionContractRepository.findById(nonExistentContractId))
                .thenReturn(Optional.empty());

        // Execute o método e espere que uma exceção NoSuchElementException seja lançada
        assertThrows(NoSuchElementException.class, () -> socialActionContractService.getSocialActionContractById(nonExistentContractId));

        // Verifique se o método do repositório foi chamado corretamente
        Mockito.verify(socialActionContractRepository).findById(nonExistentContractId);
    }
    @Test
    public void testFindSocialActionContractWithKey() {
        // Chave de contrato para pesquisa
        String keyContract = "ContractKey";

        // Crie uma lista simulada de contratos de ação social com a chave especificada
        List<SocialActionContractModel> contractList = new ArrayList<>();
        contractList.add(new SocialActionContractModel());
        contractList.add(new SocialActionContractModel());

        // Simule o comportamento do repositório para retornar a lista simulada com base na chave do contrato
        Mockito.when(socialActionContractRepository.findByKeyProcessContainingIgnoreCase(keyContract))
                .thenReturn(contractList);

        List<SocialActionContractModel> result = socialActionContractService.findSocialActionContract(keyContract);

        // Verifique se o método do repositório foi chamado corretamente
        Mockito.verify(socialActionContractRepository).findByKeyProcessContainingIgnoreCase(keyContract);

        // Verifique se o resultado não é nulo e tem o tamanho esperado
        assertEquals(contractList.size(), result.size());
    }

    @Test
    public void testFindSocialActionContractWithoutKey() {
        // Chave de contrato nula ou vazia (cenário em que deseja buscar todos os contratos)
        String keyContract = null;

        // Crie uma lista simulada de contratos de ação social
        List<SocialActionContractModel> contractList = new ArrayList<>();
        contractList.add(new SocialActionContractModel());
        contractList.add(new SocialActionContractModel());

        // Simule o comportamento do repositório para retornar a lista simulada de todos os contratos
        Mockito.when(socialActionContractRepository.findAll())
                .thenReturn(contractList);

        List<SocialActionContractModel> result = socialActionContractService.findSocialActionContract(keyContract);

        // Verifique se o método do repositório foi chamado corretamente
        Mockito.verify(socialActionContractRepository).findAll();

        // Verifique se o resultado não é nulo e tem o tamanho esperado
        assertEquals(contractList.size(), result.size());
    }
    @Test
    public void testUpdateSocialActionContract() {
        Long contractId = 1L;

        // Crie um contrato de ação social simulado com o ID especificado
        SocialActionContractModel contract = new SocialActionContractModel();
        contract.setId(contractId);

        // Crie um objeto DTO simulado para atualização
        SocialActionContractDTO updateDTO = new SocialActionContractDTO();
        updateDTO.setSocialActionId(2L);  // Novo ID de ação social
        updateDTO.setKeyProcess("UpdatedContractKey");
        updateDTO.setStatusContract(SocialActionContractStatusEnum.CREATED);

        // Crie um objeto SocialActionModel simulado com o novo ID
        SocialActionModel socialAction = new SocialActionModel();
        socialAction.setId(updateDTO.getSocialActionId());

        // Simule o comportamento do serviço de ação social para retornar o objeto SocialActionModel simulado
        Mockito.when(socialActionService.getSocialActionById(updateDTO.getSocialActionId()))
                .thenReturn(Optional.of(socialAction));

        // Simule o comportamento do repositório para retornar o contrato simulado com base no ID
        Mockito.when(socialActionContractRepository.findById(contractId))
                .thenReturn(Optional.of(contract));

        // Simule o comportamento do repositório para retornar o contrato atualizado
        Mockito.when(socialActionContractRepository.save(Mockito.any(SocialActionContractModel.class)))
                .thenReturn(contract);

        SocialActionContractModel updatedContract = socialActionContractService.updateSocialActionContract(contractId, updateDTO);

        // Verifique se o método do repositório foi chamado corretamente
        Mockito.verify(socialActionContractRepository).findById(contractId);
        Mockito.verify(socialActionService).getSocialActionById(updateDTO.getSocialActionId());
        Mockito.verify(socialActionContractRepository).save(Mockito.any(SocialActionContractModel.class));

        // Verifique se o contrato foi atualizado corretamente
        assertEquals(updateDTO.getKeyProcess(), updatedContract.getKeyProcess());
        assertEquals(updateDTO.getStatusContract(), updatedContract.getStatusContract().label);
        assertEquals(updateDTO.getSocialActionId(), updatedContract.getSocialActionId().getId());
    }


    @Test
    public void testDeleteAllSocialActionContract() {
        socialActionContractService.deleteAllSocialActionContract();

        // Verifique se o método do repositório foi chamado corretamente para excluir todos os contratos
        Mockito.verify(socialActionContractRepository).deleteAll();
    }
    @Test
    public void testDeleteSocialActionContract() {
       Long contractId = 1L;

       socialActionContractService.deleteSocialActionContract(contractId);

        // Verifique se o método do repositório foi chamado corretamente para excluir o contrato com o ID especificado
        Mockito.verify(socialActionContractRepository).deleteById(contractId);
    }


}


