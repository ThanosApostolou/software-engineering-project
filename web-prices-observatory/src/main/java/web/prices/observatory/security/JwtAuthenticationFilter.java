package web.prices.observatory.security;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import web.prices.observatory.models.AppUser;
import web.prices.observatory.repositories.UserRepository;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static web.prices.observatory.security.SecurityConstants.*;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, ApplicationContext ctx) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/observatory/api/login");
        userRepository = ctx.getBean(UserRepository.class);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response){
        if (request.getContentType().contains("application/json")) {

            try {
                AppUser credentials = new ObjectMapper().readValue(request.getInputStream(), AppUser.class);
                return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credentials.getUsername(),
                        credentials.getPassword(), new ArrayList<>()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getParameterMap().get("username")[0],
                    request.getParameterMap().get("password")[0], new ArrayList<>()));
        }
    }

    @Override
    protected void  successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                             FilterChain chain, Authentication auth) throws IOException, ServletException {

        String username = ((User) auth.getPrincipal()).getUsername();
        AppUser appUser = userRepository.findByUsername(username);

        String token = JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));

        appUser.setJwt(token);
        userRepository.save(appUser);
        response.setContentType("application/json");       
        response.setCharacterEncoding("UTF-8");
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
        response.setStatus(200);
        response.getWriter().print("{\"token\":\"" + token + "\"}");
        response.getWriter().close();

    }
}
