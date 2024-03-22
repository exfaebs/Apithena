package net.ictcampus.apithena.controller.security;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.ictcampus.apithena.model.models.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static net.ictcampus.apithena.controller.security.SecurityConstants.*;
import static net.ictcampus.apithena.controller.security.SecurityConstants.TOKEN_PREFIX;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    //Wir erben mit dieser Klasse von der Standard Spring Security Klasse.
    // Mithilfe dieser können wir Benutzer authentisieren.
    // Mit der attemptAuthentication Funktion entnehmen wir aus dem Request den Usernamen sowie das Passwort
    // und versuchen uns anzumelden.
    // Falls dies nicht funktioniert, werfen wir eine RuntimeException.
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            User creds = new ObjectMapper()
                    .readValue(req.getInputStream(), User.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsername(),
                            creds.getPassword(),
                            new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    // Die successfulAuthentication Methode wird nach Erfolg der attemptAuthentication Methode ausgeführt.
    // Diese erstellt ein neues JWT.
    // Wir sehen auch, dass wir die Variablen aus den SecurityConstants verwenden, um unser JWT zu erstellen.
    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) {

        String token = JWT.create()
                .withSubject(((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
        try {
            res.setCharacterEncoding("UTF-8");
            res.setContentType("application/json");
            String myJson = "{\"accesstoken\": \"" + token + "\"}";
            res.getWriter().write(myJson);
            res.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
    }


}
