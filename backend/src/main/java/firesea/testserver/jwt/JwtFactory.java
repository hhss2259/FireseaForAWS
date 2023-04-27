package firesea.testserver.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import firesea.testserver.service.MemberService;
import firesea.testserver.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

import static firesea.testserver.jwt.JwtConstants.*;

@Component
@RequiredArgsConstructor
public class JwtFactory {

    private final TokenService tokenService;

    public String makeAT(String username, String nickname) {
        return JWT.create()
                .withSubject("access token")
                .withExpiresAt(new Date(System.currentTimeMillis() + AT_EXP_TIME))
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withClaim("username", username)
                .withClaim("nickname",nickname)
                .sign(Algorithm.HMAC256(JWT_SECRET_AT));

    }

    public String makeRT(String username, String nickname) {
        return JWT.create()
                .withSubject("access token")
                .withExpiresAt(new Date(System.currentTimeMillis() + RT_EXP_TIME))
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withClaim("username", username)
                .withClaim("nickname",nickname)
                .sign(Algorithm.HMAC256(JWT_SECRET_RT));

    }

    public HttpServletResponse issueTokens(String username, String nickname, HttpServletResponse response) {
        String accessToken = makeAT(username, nickname);//access token 생성
        response.addHeader(AT_HEADER, TOKEN_HEADER_PREFIX + accessToken); //응답 헤더에 access token 추가

        String refreshToken = makeRT(username, nickname); //refresh Token 생성
        response.addHeader(RT_HEADER, TOKEN_HEADER_PREFIX + refreshToken); //응답 헤더에 refresh token 추가

        tokenService.updateRefreshTokens(username, refreshToken);//refresh token member db에 저장

        return response;
    }
}
