package coumo.server.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import coumo.server.web.dto.CustomOwnerDetails;
import coumo.server.web.dto.OwnerRequestDTO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

public class WebLoginFilter extends UsernamePasswordAuthenticationFilter{

    private final AuthenticationManager authenticationManager;

    //JWTUtil 주입
    private final JWTUtil jwtUtil;

    public WebLoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil){
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            //클라이언트 요청에서 loginId, password 추출
            ObjectMapper objectMapper = new ObjectMapper();
            OwnerRequestDTO.LoginDTO loginRequest = objectMapper.readValue(request.getInputStream(), OwnerRequestDTO.LoginDTO.class);

            // 스프링 시큐리티에서 loginId와 password를 검증하기 위해서는 token에 담아야 함
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    loginRequest.getLoginId(),
                    loginRequest.getPassword()
            );

            // token에 담은 검증을 위한 AuthenticationManager로 전달
            return authenticationManager.authenticate(authToken);
        } catch (IOException e) {
            throw new RuntimeException("Error reading login request", e);
        }
    }
    // 로그인 성공시 실행하는 메소드 (여기서 JWT를 발급하면 됨)
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
        // 로그인 성공 로직 및 JWT 토큰 발급을 여기에 구현
        // authentication.getName()을 통해 로그인한 사용자의 loginId을 가져올 수 있음.
        // 발급된 토큰 response에 추가작업을 수행
        CustomOwnerDetails customOwnerDetails = (CustomOwnerDetails) authentication.getPrincipal();
        String loginId = customOwnerDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String token = jwtUtil.createJwt(loginId, 60*60*10L);

        response.addHeader("Authorization", "Bearer " + token);
    }

    // 로그인 실패시 실행하는 메소드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        // 로그인 실패 로직을 여기에 구현
        // 실패 원인 등을 확인 후 적절한 응답 생성
        response.setStatus(401);
    }
}