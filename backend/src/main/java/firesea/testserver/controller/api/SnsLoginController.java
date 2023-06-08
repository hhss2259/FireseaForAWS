package firesea.testserver.controller.api;

import firesea.testserver.domain.basic.DefaultRes;
import firesea.testserver.domain.entity.Member;
import firesea.testserver.jwt.JwtFactory;
import firesea.testserver.service.MemberService;
import firesea.testserver.service.TokenService;
//import io.netty.handler.codec.dns.DnsRawRecord;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.Checker;
import org.hibernate.annotations.Check;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.http.*;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

import static firesea.testserver.jwt.JwtConstants.*;
import static firesea.testserver.jwt.JwtConstants.TOKEN_HEADER_PREFIX;

@RestController
@Slf4j
@RequiredArgsConstructor
public class SnsLoginController {

    private final MemberService memberService;
    private final JwtFactory jwtFactory;
    private final TokenService tokenService;

    @PostMapping("/api/oauth2")
    public DefaultRes getAccessToken(@RequestBody SnsProfile snsProfile, HttpServletResponse response) {

        log.info("snsId = {}", snsProfile.getSnsId());
        log.info("email = {}", snsProfile.getEmail());
        log.info("name = {}", snsProfile.getName());

        String username = makeUsernameForSnsLogin(snsProfile);
        int i = memberService.countMemberByUsername(username);
        if(i == 0){
            return DefaultRes.res(20031, "최초 로그인 : 회원 가입을 진행해야 합니다.");

        }
        else{
            String nickname = memberService.getNickname(username);
            String accessToken = jwtFactory.makeAT(username, nickname);//access token 생성
            response.addHeader(AT_HEADER, TOKEN_HEADER_PREFIX + accessToken); //응답 헤더에 access token 추가

            String refreshToken = jwtFactory.makeRT(username, nickname); //refresh Token 생성
            response.addHeader(RT_HEADER, TOKEN_HEADER_PREFIX + refreshToken); //응답 헤더에 refresh token 추가


            log.info("sns login access_token = {} ", accessToken);
            log.info("sns login refresh_token = {} ", refreshToken);


            tokenService.updateRefreshTokens(username, refreshToken);//refresh token member db에 저장
            return DefaultRes.res(20032, "로그인 완료 : 회원 인증이 완료되었습니다. ", nickname);
        }

    }

    @PostMapping("/api/oauth2/register")
    public DefaultRes<String> oauth2_register(@RequestBody SnsRegisteringProfile registeringProfile) {

        String email = registeringProfile.getEmail();
        String snsId = registeringProfile.getSnsId();
        String nickname = registeringProfile.getNickname();

        Member member = new Member(snsId+"_"+email, "1111", email, nickname);

        int result = memberService.save(member);


        if (result == 1) {
            return DefaultRes.res(20033, "최초 SNS 로그인을 통한 회원가입 완료", nickname);
        } else {
            return DefaultRes.res(40033, "회원가입에 실패했습니다", nickname);
        }
    }



    private String makeUsernameForSnsLogin(SnsProfile snsProfile) {
        return snsProfile.getSnsId() + "_" + snsProfile.getEmail();
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class SnsProfile {
        String snsId;
        String email;
        String name;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class SnsRegisteringProfile {
        String snsId;
        String email;
        String nickname;
    }



/*
    @GetMapping("/api/oauth2")"
    public DefaultRes getAccessToken(@RequestParam String access_token) throws InterruptedException {
        log.info("oauth2 access token = {}", access_token);

        WebClient client = WebClient.builder()
                .clientConnector(connector())
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .baseUrl("http://localhost:8080")
                .build();

        Checker block = client.mutate()
                .build()
                .get()
                .uri("/api/checkClient?access_token=" + access_token)
                .retrieve()
                .bodyToMono(Checker.class)
                .block();

        log.info("우짤래미");
        log.info("checker mono = {}" , block);

        return DefaultRes.res(20031, "최초 로그인 : 회원가입되었습니다", block); //이미 회원가입한 상태이면 20032
    }

    @GetMapping("/api/checkClient")
    public Checker checkClient(@RequestParam String access_token) {
        log.info("server access_token = {} ", access_token);
        log.info("우짤래미");
        return new Checker("김채원", 24);
    }

    @NoArgsConstructor
    @AllArgsConstructor
    private static class Checker {

        String name;
        int age;

    }
    private ClientHttpConnector connector() {
        return new
                ReactorClientHttpConnector(HttpClient.create(ConnectionProvider.newConnection()));
    }*/
}
