package com.getir.readingisgood.controller;

import com.getir.readingisgood.dto.request.LoginDTO;
import com.getir.readingisgood.dto.request.SignupDTO;
import com.getir.readingisgood.dto.response.ResponseDTO;
import com.getir.readingisgood.models.RoleType;
import com.getir.readingisgood.models.User;
import com.getir.readingisgood.repository.UserRepository;
import com.getir.readingisgood.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/auth",
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_XML_VALUE},
        consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_XML_VALUE})
public class AuthController {

        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;
        private final AuthService authService;

        @PostMapping("/signin")
        public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginDTO loginDTO) {
                return ResponseEntity.ok(authService.login(loginDTO));
        }


        @PostMapping("/signup")
        public ResponseEntity<?> registerUser(@Valid @RequestBody SignupDTO signupDTO) {
                if (userRepository.existsByUsername(signupDTO.getUsername())) {
                        return ResponseEntity
                                .badRequest()
                                .body(new ResponseDTO().setMessage("Error: Username is already taken!"));
                }

                if (userRepository.existsByEmail(signupDTO.getEmail())) {
                        return ResponseEntity
                                .badRequest()
                                .body(new ResponseDTO().setMessage("Error: Email is already in use!"));
                }

                User user = new User()
                        .setUsername(signupDTO.getUsername())
                        .setEmail(signupDTO.getEmail())
                        .setPassword(passwordEncoder.encode(signupDTO.getPassword()));

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

                return ResponseEntity.ok(new ResponseDTO().setMessage("User registered successfully!"));
        }
}
