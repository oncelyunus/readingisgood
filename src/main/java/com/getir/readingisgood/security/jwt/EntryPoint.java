package com.getir.readingisgood.security.jwt;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
@Log4j2
public class EntryPoint implements AuthenticationEntryPoint {

        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response,
                AuthenticationException authException) throws IOException, ServletException {
                log.error("Unauthorized error: {}", authException.getMessage());
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
        }
}
