package com.getir.readingisgood.security.jwt;

import com.getir.readingisgood.contants.AppContants;
import com.getir.readingisgood.security.service.GetirUserDetail;
import com.getir.readingisgood.security.service.GetirUserDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
@Log4j2
@RequiredArgsConstructor
public class TokenFilter extends OncePerRequestFilter {

        private final JwtUtil jwtUtil;
        private final GetirUserDetailService getirUserDetailService;

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                FilterChain filterChain) throws ServletException, IOException {
                try {
                        String jwt = parseJwt(request);
                        if (StringUtils.hasText(jwt) &&
                                jwtUtil.validateJwtToken(jwt)) {
                                String username = jwtUtil.parseUsername(jwt);

                                GetirUserDetail userDetails = (GetirUserDetail) getirUserDetailService.loadUserByUsername(username);
                                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                                        userDetails.getAuthorities());
                                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                                SecurityContextHolder.getContext().setAuthentication(authentication);
                        }
                } catch (Exception e) {
                        log.error("Cannot set user authentication: {}", e.getMessage());
                }

                filterChain.doFilter(request, response);
        }

        private String parseJwt(HttpServletRequest request) {
                String bearerHeader = request.getHeader(AppContants.AUTHORIZATION);

                if (StringUtils.hasText(bearerHeader) && bearerHeader.startsWith(AppContants.BEARER + " ")) {
                        return bearerHeader.substring(AppContants.BEARER.length()+1, bearerHeader.length());
                }

                return null;
        }
}
