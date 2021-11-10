package com.getir.readingisgood.security.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.getir.readingisgood.models.User;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
public class GetirUserDetail implements UserDetails {

        private String id;
        private String username;
        private String email;
        @JsonIgnore
        private String password;
        private Collection<? extends GrantedAuthority> authorities;

        public static GetirUserDetail build(User user) {
                List<GrantedAuthority> authorities = user.getRoles().stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

                return new GetirUserDetail(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getPassword(),
                        authorities);
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
                return authorities;
        }

        public String getId() {
                return id;
        }

        public String getEmail() {
                return email;
        }

        @Override
        public String getPassword() {
                return password;
        }

        @Override
        public String getUsername() {
                return username;
        }

        @Override
        public boolean isAccountNonExpired() {
                return true;
        }

        @Override
        public boolean isAccountNonLocked() {
                return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
                return true;
        }

        @Override
        public boolean isEnabled() {
                return true;
        }

        @Override
        public boolean equals(Object o) {
                if (this == o)
                        return true;
                if (o == null || getClass() != o.getClass())
                        return false;
                GetirUserDetail user = (GetirUserDetail) o;
                return Objects.equals(id, user.id);
        }
}
