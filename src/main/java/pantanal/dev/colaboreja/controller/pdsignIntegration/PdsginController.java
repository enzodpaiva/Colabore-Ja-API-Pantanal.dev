package pantanal.dev.colaboreja.controller.pdsignIntegration;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pantanal.dev.colaboreja.DTO.pdsignIntegration.ProcessPdsignDTO;
import pantanal.dev.colaboreja.service.pdsignIntegration.PdsignService;
import pantanal.dev.colaboreja.util.ApiResponse;

@RestController
@RequestMapping("/api/integration/pdsign")
public class PdsginController {
    @Autowired
    private PdsignService service;

    @GetMapping("/get-status-document")
    public ResponseEntity<?> getStatusDocumentPdsign() {
        try {
            var token = this.service.getStatusDocumentPdSign("960a48d1-2372-458c-9b52-5e9ec724fce4");

            ApiResponse response = new ApiResponse(true, "Token resgatado", token);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            throw ex;
        }
    }

    @GetMapping("/get-document")
    public ResponseEntity<?> getDocumentPdsign() {
        try {
            var document = this.service.getDocumentPdSign("960a48d1-2372-458c-9b52-5e9ec724fce4", "0a8def1a-9c08-4429-bf03-9729031d34ea");

            ApiResponse response = new ApiResponse(true, "Documento obtido com sucesso", document);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            throw ex;
        }
    }

    @PostMapping("/create-process")
    public ResponseEntity<?> createProcessPdsign(@Valid @RequestBody ProcessPdsignDTO processPdsignDTO) {
        try {
            var document = this.service.createProcessPdSign(processPdsignDTO);

            ApiResponse response = new ApiResponse(true, "Documento obtido com sucesso", document);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            throw ex;
        }
    }
}
