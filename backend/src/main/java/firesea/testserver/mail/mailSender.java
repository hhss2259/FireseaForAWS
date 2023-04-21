package firesea.testserver.mail;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public interface mailSender {

    void sendAuthMail();
}
