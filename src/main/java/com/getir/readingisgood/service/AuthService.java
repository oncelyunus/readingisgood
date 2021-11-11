package com.getir.readingisgood.service;

import com.getir.readingisgood.contants.ErrorCodes;
import com.getir.readingisgood.dto.request.LoginDTO;
import com.getir.readingisgood.dto.request.SignupDTO;
import com.getir.readingisgood.dto.response.JwtResponseDTO;
import com.getir.readingisgood.dto.token.UserAuthToken;
import com.getir.readingisgood.helper.GetirException;
import com.getir.readingisgood.models.Address;
import com.getir.readingisgood.models.RoleType;
import com.getir.readingisgood.models.User;
import com.getir.readingisgood.repository.UserRepository;
import com.getir.readingisgood.security.jwt.JwtUtil;
import com.getir.readingisgood.security.service.GetirUserDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class AuthService {

        private final AuthenticationManagerBuilder authenticationManagerBuilder;
        private final PasswordEncoder passwordEncoder;
        private final UserRepository userRepository;
        private final JwtUtil jwtUtil;
        private final RedisTemplate<String, Object> redisTemplate;

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

        public void registerNewUser(SignupDTO signupDTO) throws GetirException {
                if(userRepository.existsByUsername(signupDTO.getUsername())) {
                        throw new GetirException(ErrorCodes.USER_ALREADY_EXISTS_CODE,
                                ErrorCodes.USER_ALREADY_EXISTS_MESSAGE+signupDTO.getUsername());
                }

                if (userRepository.existsByEmail(signupDTO.getEmail())) {
                        throw new GetirException(ErrorCodes.USER_ALREADY_EXISTS_CODE,
                                ErrorCodes.USER_ALREADY_EXISTS_MESSAGE+signupDTO.getEmail());
                }

                Address address = null;
                if(Objects.nonNull(signupDTO.getAddress())) {
                        address = new Address()
                                .setStreet(signupDTO.getAddress().getStreet())
                                .setCity(signupDTO.getAddress().getCity())
                                .setPhone(signupDTO.getAddress().getPhone());
                }
                User user = new User()
                        .setUsername(signupDTO.getUsername())
                        .setEmail(signupDTO.getEmail())
                        .setPassword(passwordEncoder.encode(signupDTO.getPassword()))
                        .setAddress(address);

                Set<String> strRoles = signupDTO.getRoles();
                Set<String> roles = new HashSet<>();

                if (CollectionUtils.isEmpty(strRoles)) {
                        roles.add(RoleType.USER.getName());
                } else {
                        strRoles.forEach(role -> {
                                if (role.equals("admin")) {
                                        roles.add(RoleType.ADMIN.getName());
                                } else {
                                        roles.add(RoleType.USER.getName());
                                }
                        });
                }

                user.setRoles(roles);
                userRepository.save(user);
        }
}
