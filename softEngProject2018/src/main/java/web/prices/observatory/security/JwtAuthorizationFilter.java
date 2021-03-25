package web.prices.observatory.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import web.prices.observatory.models.AppUser;
import web.prices.observatory.repositories.UserRepository;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static web.prices.observatory.security.SecurityConstants.*;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    @Autowired
    UserRepository userRepository;

    public JwtAuthorizationFilter(AuthenticationManager authManager, ApplicationContext ctx) {
        super(authManager);
        userRepository = ctx.getBean(UserRepository.class);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        String header = req.getHeader(HEADER_STRING);
        if (header==null || !header.startsWith(TOKEN_PREFIX)){
            chain.doFilter(req,res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication;

        try {
            authentication = getAuthentication(req);
        } catch (SignatureVerificationException e){
            System.out.println("Authentication failed for request");
            return;
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {

        String token = request.getHeader(HEADER_STRING);

        if (token!=null){
            AppUser appUser = userRepository.findByJwt(token.replace(TOKEN_PREFIX, ""));
            //parse the token
            //in case secret has changed
            String user = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                    .build().verify(token.replace(TOKEN_PREFIX,"")).getSubject();

            if (user!=null && appUser!=null && appUser.getEnabled()) {
                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            }
        }
        return null;
    }
}
