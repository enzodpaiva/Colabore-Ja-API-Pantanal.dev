package pantanal.dev.colaboreja.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pantanal.dev.colaboreja.enumerable.SocialActionContractStatusEnum;
import pantanal.dev.colaboreja.model.SocialActionContractModel;
import pantanal.dev.colaboreja.repository.SocialActionContractRepository;
import pantanal.dev.colaboreja.service.SocialActionContractService;
import pantanal.dev.colaboreja.service.pdsignIntegration.PdsignService;

import java.time.Instant;
import java.util.Calendar;
import java.util.List;

@Component
public class UpdateStatusContractTask {

    private static final Logger log = LoggerFactory.getLogger(UpdateStatusContractTask.class);

    private final SocialActionContractService contractService;
    private final PdsignService pdsignService;
    private final SocialActionContractRepository socialActionContractRepository;

    @Autowired
    public UpdateStatusContractTask(SocialActionContractService contractService, PdsignService pdsignService, SocialActionContractRepository socialActionContractRepository) {
        this.contractService = contractService;
        this.pdsignService = pdsignService;
        this.socialActionContractRepository = socialActionContractRepository;
    }

    @Scheduled(cron = "0 0/30 8-19 * * MON-FRI") // Executa a cada 30 minutos, de segunda a sexta, das 8h às 19h
    public void performTask() {
        log.info("Performing VerifyStatusContractTask @ {}", Instant.now());

        Calendar now = Calendar.getInstance();
        int hour = now.get(Calendar.HOUR_OF_DAY);

        // Verifique se está dentro do intervalo das 8h às 19h
        if (hour >= 8 && hour <= 19) {

            List<SocialActionContractModel> runningContracts = this.contractService.getRunningKeyProcesses();

            for (SocialActionContractModel contract : runningContracts) {
                String status = this.pdsignService.getStatusDocumentPdSign(contract.getKeyProcess());

                if (!status.equals(contract.getStatusContract().toString())) {
                    SocialActionContractStatusEnum statusEnum = SocialActionContractStatusEnum.fromString(status);
                    if (statusEnum != null) {
                        contract.setStatusContract(statusEnum);
                        this.socialActionContractRepository.save(contract);
                    }
                }
            }
        }
    }

    public void manualVerifyStatusContractTaskExecution() {
        log.info("Performing VerifyStatusContractTask @ {}", Instant.now());

        Calendar now = Calendar.getInstance();
        int hour = now.get(Calendar.HOUR_OF_DAY);

        // Verifique se está dentro do intervalo das 8h às 19h
        if (hour >= 8 && hour <= 19) {

            List<SocialActionContractModel> runningContracts = this.contractService.getRunningKeyProcesses();

            for (SocialActionContractModel contract : runningContracts) {
                String status = this.pdsignService.getStatusDocumentPdSign(contract.getKeyProcess());

                if (!status.equals(contract.getStatusContract().toString())) {
                    SocialActionContractStatusEnum statusEnum = SocialActionContractStatusEnum.fromString(status);
                    if (statusEnum != null) {
                        contract.setStatusContract(statusEnum);
                        this.socialActionContractRepository.save(contract);
                    }
                }
            }
        }
    }
}
