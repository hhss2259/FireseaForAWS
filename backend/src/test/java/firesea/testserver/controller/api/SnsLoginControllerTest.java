package firesea.testserver.controller.api;

import firesea.testserver.jwt.JwtFactory;
import firesea.testserver.service.MemberService;
import firesea.testserver.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = SnsLoginController.class)
class SnsLoginControllerTest {

    @Autowired
    MemberService memberService;

    @Autowired
    TokenService tokenService;

    @Autowired
    JwtFactory jwtFactory;









}