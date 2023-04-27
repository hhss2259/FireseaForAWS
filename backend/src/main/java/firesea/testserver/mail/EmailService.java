package firesea.testserver.mail;

import firesea.testserver.domain.entity.Member;
import firesea.testserver.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final MemberRepository memberRepository;

    private Map createMessage(String to)throws Exception{
        MimeMessage message = javaMailSender.createMimeMessage();
        String key = createKey();

        message.addRecipients(Message.RecipientType.TO, to);//보내는 대상
        message.setSubject("이메일 인증 테스트");//제목

        String msgg="";
        msgg+= "<div style='margin:20px;'>";
        msgg+= "<h1> Firesea에 오신 것을 환영합니다!</h1>";
        msgg+= "<br>";
        msgg+= "<p>아래 코드를 복사해 입력해주세요<p>";
        msgg+= "<br>";
        msgg+= "<p>감사합니다.<p>";
        msgg+= "<br>";
        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg+= "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
        msgg+= "<div style='font-size:130%'>";
        msgg+= "CODE : <strong>";
        msgg+= key+"</strong><div><br/> ";
        msgg+= "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("hhss2259@gmail.com","firesea"));//보내는 사람

        Map map = new HashMap();
        map.put("message", message);
        map.put("key", key);
        return map;
    }

    public static String createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 8; i++) { // 인증코드 8자리
            int index = rnd.nextInt(3); // 0~2 까지 랜덤

            switch (index) {
                case 0:
                    key.append((char) ((rnd.nextInt(26)) + 97));
                    //  a~z  (ex. 1+97=98 => (char)98 = 'b')
                    break;
                case 1:
                    key.append((char) ((rnd.nextInt(26)) + 65));
                    //  A~Z
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    // 0~9
                    break;
            }
        }
        return key.toString();
    }


    public String sendMessage(String to)throws Exception {
        Map map = createMessage(to);
        try {
            javaMailSender.send((MimeMessage) map.get("message"));
        } catch (MailException es) {
            es.printStackTrace();
            throw new IllegalEmailException();
        }
        return (String) map.get("key");
    }

    public void checkDuplicateEmail(String email) {
        Member member = memberRepository.findMemberByEmail(email);

        if (member == null) {
            throw new DuplicateEmailException();
        }
    }

}
