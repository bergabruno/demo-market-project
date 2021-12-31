//package br.com.mercado.security;
//
//import br.com.mercado.dto.AdminNewDTO;
//import br.com.mercado.model.entity.Admin;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.AllArgsConstructor;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.ArrayList;
//
//@AllArgsConstructor
//public class JWTAutheticationFilter extends UsernamePasswordAuthenticationFilter {
//
//    private AuthenticationManager authenticationManager;
//    private JWTUtil jwtUtil;
//
//
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//
//        try {
//            AdminNewDTO adminNewDTO = new ObjectMapper().readValue(request.getInputStream(), AdminNewDTO.class);
//            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(adminNewDTO.getLogin(), adminNewDTO.getSenha(), new ArrayList<>());
//            Authentication authentication = authenticationManager.authenticate(authToken);
//            return authentication;
//        }catch (IOException e){
//            throw new RuntimeException("erro");
//        }
//
//
//    }
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
//        String login = ((AdminSS) authResult.getPrincipal()).getUsername();
//        String token = jwtUtil.generateToken(login);
//        response.addHeader("Authorization", "Bearer " + token);
//
//    }
//
//}
