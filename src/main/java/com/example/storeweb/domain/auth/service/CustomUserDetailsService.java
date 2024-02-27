package com.example.storeweb.domain.auth.service;

import com.example.storeweb.domain.auth.entity.ActivityEntity;
import com.example.storeweb.domain.auth.entity.RoleEntity;
import com.example.storeweb.domain.auth.entity.TenantEntity;
import com.example.storeweb.domain.auth.repo.ActivityRepository;
import com.example.storeweb.domain.auth.repo.RoleRepository;
import com.example.storeweb.domain.auth.repo.TenantRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Slf4j
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private TenantRepository tenantRepository;
    private RoleRepository roleRepository;


    public UserDetails loadUserByTenantUuid(String uuid) throws UsernameNotFoundException {
        log.info("UUID-1 : " + uuid);
        TenantEntity tenant = tenantRepository.findByUuid(uuid)
                .orElseThrow(() -> new UsernameNotFoundException("Tenant not found with id: " + uuid));


        log.info("UUID-2 : " + uuid);
        List<String> roles = roleRepository.findAll().stream().map(RoleEntity::getValue).toList();
        List<GrantedAuthority> authorities = new ArrayList<>();


        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return new User(
                tenant.getUuid(), Optional.ofNullable(tenant.getEmail()).orElse(""), authorities);

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
