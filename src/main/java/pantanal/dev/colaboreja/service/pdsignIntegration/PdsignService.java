package pantanal.dev.colaboreja.service.pdsignIntegration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import pantanal.dev.colaboreja.DTO.SocialActionContractDTO;
import pantanal.dev.colaboreja.DTO.pdsignIntegration.DocumentPdsignDTO;
import pantanal.dev.colaboreja.DTO.pdsignIntegration.ProcessPdsignDTO;
//import pantanal.dev.colaboreja.DTO.pdsignIntegration.UploadProcessDocumentPdsignDTO;
import pantanal.dev.colaboreja.enumerable.pdsignIntegration.ActionTypeEnum;
import pantanal.dev.colaboreja.enumerable.pdsignIntegration.AuthenticationTypeEnum;
import pantanal.dev.colaboreja.enumerable.pdsignIntegration.CompanyEnum;
import pantanal.dev.colaboreja.enumerable.pdsignIntegration.ResponsibilityEnum;
import pantanal.dev.colaboreja.model.SocialActionContractModel;
import pantanal.dev.colaboreja.model.SocialActionModel;
import pantanal.dev.colaboreja.model.UserModel;
import pantanal.dev.colaboreja.service.SocialActionContractService;
import pantanal.dev.colaboreja.service.SocialActionService;
import pantanal.dev.colaboreja.service.UserService;
//import pantanal.dev.colaboreja.util.PdfGenerate;

import java.io.File;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;

@Service
public class PdsignService {

    @Value("${application.pdsign.auth.url}")
    private String urlAuth;

    @Value("${application.pdsign.url}")
    private String url;

    @Autowired
    private SocialActionService socialActionService;

    @Autowired
    private UserService userService;

    @Autowired
    private SocialActionContractService socialActionContractService;

    public String getTokenPdSign() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED.toString());
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("username", "time9@pantanal.dev");
        body.add("password", "gSM2eR7fek!");
        body.add("client_id", "assinador-app");
        body.add("grant_type", "password");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        return new RestTemplate().postForEntity(this.urlAuth, request, Map.class).getBody().get("access_token").toString();
    }

    public String getStatusDocumentPdSign(String keyProcess) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", "Bearer " + this.getTokenPdSign());

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> responseEntity = new RestTemplate().exchange(
                this.url + "/processes/" + keyProcess,
                HttpMethod.GET,
                requestEntity,
                Map.class
        );

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            Map<String, Object> responseBody = responseEntity.getBody();
            // Verifique se o campo "id" está presente no mapa antes de acessá-lo
            if (responseBody.containsKey("status")) {
                return responseBody.get("status").toString();
            } else {
                // Campo "id" não encontrado na resposta
                return "Campo 'status' não encontrado na resposta.";
            }
        } else {
            // Lide com as respostas não bem-sucedidas aqui, se necessário
            return "Requisição não foi bem-sucedida. Código de status: " + responseEntity.getStatusCodeValue();
        }
    }

    public Resource getDocumentPdSign(String idProcess, String idDocument) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.getTokenPdSign());

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<byte[]> responseEntity = new RestTemplate().exchange(
                this.url + "/processes/" + idProcess + "/documents/" + idDocument + "/download",
                HttpMethod.GET,
                requestEntity,
                byte[].class
        );

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            byte[] responseBody = responseEntity.getBody();
            ByteArrayResource resource = new ByteArrayResource(responseBody);
            return resource;
        } else {
            // Lide com as respostas não bem-sucedidas aqui, se necessário
            return null;
        }
    }

    public Object createProcessPdSign(ProcessPdsignDTO processPdsignDTO, Integer colaboratorId, Long socialActionId) {

        var token = this.getTokenPdSign();
        UserModel colaborator = this.userService.getUserById(colaboratorId).get();
        SocialActionModel socialAction = this.socialActionService.getSocialActionById(socialActionId).get();
        SocialActionContractModel socialActionContract = this.socialActionContractService.findContractByUserAndSocialAction(colaborator.getId(), socialAction.getId());

        this.transformMapValues(processPdsignDTO.getCompany(), CompanyEnum::getIdFromName);
        processPdsignDTO.getMembers().stream().forEach(member -> {
            this.transformMapValues(member.getActionType(), ActionTypeEnum::getIdFromName);
            this.transformMapValues(member.getResponsibility(), ResponsibilityEnum::getIdFromName);
            this.transformMapValues(member.getAuthenticationType(), AuthenticationTypeEnum::getIdFromName);

            member.getRepresentation().getCompanies().stream().forEach(company -> {
                this.transformMapValues(company, CompanyEnum::getIdFromName);
            });
        });

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", "Bearer " + token);

        HttpEntity<ProcessPdsignDTO> requestEntity = new HttpEntity<>(processPdsignDTO, headers);

        ResponseEntity<Map> responseEntity = new RestTemplate().exchange(
                this.url + "/processes/",
                HttpMethod.POST,
                requestEntity,
                Map.class
        );

        if (responseEntity.getStatusCode() == HttpStatus.OK) {

            Map<String, Object> responseBody = responseEntity.getBody();
            socialActionContract = this.socialActionContractService.saveProcessColaborator(responseBody.get("id").toString(), socialActionContract);

            SocialActionContractDTO result = this.createDocumentPdSign(socialActionContract, token);

            return result;
        } else {
            // Lide com as respostas não bem-sucedidas aqui, se necessário
            return Collections.emptyMap();
        }
    }
    public SocialActionContractDTO createDocumentPdSign(SocialActionContractModel socialActionContract, String token) {

        DocumentPdsignDTO documentPdsignDTO = new DocumentPdsignDTO(
                "PDF",
                "false",
                socialActionContract.getColaborator().getFirstname() + "-" + socialActionContract.getColaborator().getLastname() + "_" + socialActionContract.getSocialActionId().getName() + "_" + socialActionContract.getSocialActionId().getInitDateTime().toString() + ".pdf",
                "0",
                "SIGN"
        );

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", "Bearer " + token);

        HttpEntity<DocumentPdsignDTO> requestEntity = new HttpEntity<>(documentPdsignDTO, headers);

        ResponseEntity<Map> responseEntity = new RestTemplate().exchange(
                this.url + "/processes/" + socialActionContract.getKeyProcess() + "/documents",
                HttpMethod.POST,
                requestEntity,
                Map.class
        );

        if (responseEntity.getStatusCode() == HttpStatus.OK) {

            Map<String, Object> responseBody = responseEntity.getBody();
            var result = this.socialActionContractService.saveDocumentColaborator(responseBody.get("id").toString(), socialActionContract);
            return result;
        } else {
            // Lide com as respostas não bem-sucedidas aqui, se necessário
            return (SocialActionContractDTO) Collections.emptyMap();
        }
    }

//    public SocialActionContractDTO sendProcessDocumentPdSign(SocialActionContractModel socialActionContract, String token) {
//
//        PdfGenerate pdf = new PdfGenerate();
//        UploadProcessDocumentPdsignDTO uploadProcessDocumentPdsignDTO = new UploadProcessDocumentPdsignDTO(
//                pdf
//        );
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Type", MediaType.MULTIPART_FORM_DATA_VALUE);
//        headers.add("Authorization", "Bearer " + token);
//
//        HttpEntity<UploadProcessDocumentPdsignDTO> requestEntity = new HttpEntity<>(uploadProcessDocumentPdsignDTO, headers);
//
//        ResponseEntity<Map> responseEntity = new RestTemplate().exchange(
//                this.url + "/processes/" + socialActionContract.getKeyProcess() + "/documents/" + socialActionContract.getKeyDocument() + "/upload",
//                HttpMethod.POST,
//                requestEntity,
//                Map.class
//        );
//
//        if (responseEntity.getStatusCode() == HttpStatus.OK) {
//
//            Map<String, Object> responseBody = responseEntity.getBody();
//            var result = this.socialActionContractService.saveDocumentColaborator(responseBody.get("id").toString(), socialActionContract);
//            return result;
//        } else {
//            // Lide com as respostas não bem-sucedidas aqui, se necessário
//            return (SocialActionContractDTO) Collections.emptyMap();
//        }
//    }

       public Object updateProcessStatusPdSign(String idProcess, Integer colaboratorId, Long socialActionId, ProcessPdsignDTO processPdsignDTO) {

        var token = this.getTokenPdSign();
        UserModel colaborator = this.userService.getUserById(colaboratorId).get();
        SocialActionModel socialAction = this.socialActionService.getSocialActionById(socialActionId).get();
        SocialActionContractModel socialActionContract = this.socialActionContractService.findContractByUserAndSocialAction(colaborator.getId(), socialAction.getId());

        this.transformMapValues(processPdsignDTO.getCompany(), CompanyEnum::getIdFromName);
        processPdsignDTO.getMembers().stream().forEach(member -> {
            this.transformMapValues(member.getActionType(), ActionTypeEnum::getIdFromName);
            this.transformMapValues(member.getResponsibility(), ResponsibilityEnum::getIdFromName);
            this.transformMapValues(member.getAuthenticationType(), AuthenticationTypeEnum::getIdFromName);

            member.getRepresentation().getCompanies().stream().forEach(company -> {
                this.transformMapValues(company, CompanyEnum::getIdFromName);
            });
        });

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", "Bearer " + token);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("status", "RUNNING");

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> responseEntity = new RestTemplate().exchange(
                this.url + "/processes/" + idProcess,
                HttpMethod.PATCH,
                requestEntity,
                Map.class
        );

        if (responseEntity.getStatusCode() == HttpStatus.OK) {

            return this.socialActionContractService.updateStatusProcessColaborator(socialActionContract);
        } else {
            // Lide com as respostas não bem-sucedidas aqui, se necessário
            return Collections.emptyMap();
        }
    }

    private static <K, V> void transformMapValues(Map<K, V> map, Function<V, V> transformer) {
        for (K key : map.keySet()) {
            V value = map.get(key);
            V transformedValue = transformer.apply(value);
            map.put(key, transformedValue);
        }
    }

}
