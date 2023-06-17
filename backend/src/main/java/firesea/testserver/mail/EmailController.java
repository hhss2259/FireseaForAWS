package firesea.testserver.mail;

import firesea.testserver.domain.basic.DefaultRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

@RestController
@Slf4j
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @GetMapping("/api/checkEmail")
    public DefaultRes<String> checkEmail(@RequestParam String email) throws Exception {

        emailService.checkDuplicateEmail(email);
        String key = emailService.sendMessage(email);

        return DefaultRes.res(20030 , "이메일을 발송했습니다.", key);
    }

    @ExceptionHandler(IllegalEmailException.class)
    public DefaultRes illegalEmailException() {
        return DefaultRes.res(40031, "이메일을 발송하지 못했습니다.");
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public DefaultRes duplicateEmailException() {
        return DefaultRes.res(40032, "이미 회원가입한 이메일입니다.");
    }
}
