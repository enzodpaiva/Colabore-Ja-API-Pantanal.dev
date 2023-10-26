package pantanal.dev.colaboreja.controller.pdsignIntegration;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pantanal.dev.colaboreja.DTO.pdsignIntegration.ProcessPdsignDTO;
import pantanal.dev.colaboreja.service.pdsignIntegration.PdsignService;
import pantanal.dev.colaboreja.task.UpdateStatusContractTask;
import pantanal.dev.colaboreja.util.ApiResponse;

@RestController
@RequestMapping("/api/integration/pdsign")
public class PdsginController {
    @Autowired
    private PdsignService service;

    @Autowired
    private UpdateStatusContractTask updateStatusContractTask; // Inject the task bean

    @GetMapping("/get-status-document/{idProcess}")
    public ResponseEntity<?> getStatusDocumentPdsign(
            @PathVariable(value = "id", required = true) String idProcess
    ) {
        try {
            var token = this.service.getStatusDocumentPdSign(idProcess);

            ApiResponse response = new ApiResponse(true, "Token resgatado", token);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            throw ex;
        }
    }

    @GetMapping("/get-document/{idProcess}/{idDocument}")
    public ResponseEntity<?> getDocumentPdsign(
            @PathVariable String idProcess, String idDocument
    ) {
        try {
            var document = this.service.getDocumentPdSign(idProcess, idDocument);

            ApiResponse response = new ApiResponse(true, "Documento obtido com sucesso", document);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            throw ex;
        }
    }

    @PostMapping("/create-process/{colaboratorId}/{socialActionId}")
    public ResponseEntity<?> createProcessPdsign(
            @PathVariable Integer colaboratorId, @PathVariable Long socialActionId,
            @Valid @RequestBody ProcessPdsignDTO processPdsignDTO) {
        try {
            var document = this.service.createProcessPdSign(processPdsignDTO, colaboratorId,  socialActionId);

            ApiResponse response = new ApiResponse(true, "Process criado com sucesso", document);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            throw ex;
        }
    }

//    @PostMapping("/test-schedule")
//    public void testSchedule(
//    ) {
//        try {
//            updateStatusContractTask.manualVerifyStatusContractTaskExecution();
//        } catch (Exception ex) {
//            throw ex;
//        }
//    }
}
