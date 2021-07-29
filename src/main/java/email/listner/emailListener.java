package email.listner;

import com.example.demo.common.EmailRequest;
import email.service.EmailService;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class emailListener {

    private final EmailService emailService;

    public emailListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(topics = "activation-email")
    public void listenForActivationEmail(EmailRequest emailRequest) {
        log.info("Kafka received: " + emailRequest.getTo());
        log.info(emailRequest.getContent());
        emailService.send(emailRequest.getTo(),emailRequest.getSubject(),emailRequest.getContent());
    }

}
