package com.getir.readingisgood.service;

import com.getir.readingisgood.dto.request.LoginDTO;
import com.getir.readingisgood.dto.response.JwtResponseDTO;
import com.getir.readingisgood.dto.token.UserAuthToken;
import com.getir.readingisgood.security.jwt.JwtUtil;
import com.getir.readingisgood.security.service.GetirUserDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class AuthService {

        private final AuthenticationManagerBuilder authenticationManagerBuilder;
        private final JwtUtil jwtUtil;

        public JwtResponseDTO login(LoginDTO loginDTO) {
                final UserAuthToken userAuthToken = UserAuthToken.of(loginDTO.getUsername(), loginDTO.getPassword());
                final Authentication authentication = authenticationManagerBuilder.getObject().authenticate(userAuthToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                String jwt = jwtUtil.generateJwtToken(authentication);

                GetirUserDetail userDetails = (GetirUserDetail) authentication.getPrincipal();
                List<String> roles = userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList());

                return new JwtResponseDTO()
                        .setToken(jwt)
                        .setRoles(roles)
                        .setId(userDetails.getId())
                        .setUsername(userDetails.getUsername())
                        .setEmail(userDetails.getEmail());

        }
}
