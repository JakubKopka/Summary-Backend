package pl.kopka.summary.service;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import pl.kopka.summary.constant.SecurityConst;
import pl.kopka.summary.domain.model.User;
import pl.kopka.summary.security.JwtTokenProvider;

@Service
public class AuthenticateService {

    private AuthenticationManager authenticationManager;

    private JwtTokenProvider jwtTokenProvider;

    public AuthenticateService(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public void authenticateUser(String username, String password){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

    }

    public HttpHeaders getJwtHeader(User user) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(SecurityConst.JWT_TOKEN_HEADER, jwtTokenProvider.generateJwtToken(user));
        return httpHeaders;
    }
}
