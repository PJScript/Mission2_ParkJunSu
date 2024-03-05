package com.example.storeweb.common.security;

import com.example.storeweb.domain.auth.entity.ActivityEntity;
import com.example.storeweb.domain.auth.entity.RoleEntity;
import com.example.storeweb.domain.auth.entity.TenantEntity;
import com.example.storeweb.domain.auth.repo.ActivityRepository;
import com.example.storeweb.domain.auth.repo.RoleRepository;
import com.example.storeweb.domain.auth.repo.TenantRepository;
import jakarta.transaction.Transactional;
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

    @Transactional
    public UserDetails loadUserByTenantUuid(String uuid) throws UsernameNotFoundException {
        TenantEntity tenant = tenantRepository.findTenantEntityByUuid(uuid)
                .orElseThrow(() -> new UsernameNotFoundException("Tenant not found with uuid: " + uuid));

        RoleEntity role = tenant.getRole();
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role.getValue()));

        return new User(tenant.getUuid(), tenant.getPassword(), authorities);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TenantEntity tenant = tenantRepository.findByAccount(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with account: " + username));


        RoleEntity role = tenant.getRole();
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role.getValue()));

        return new User(tenant.getAccount(), tenant.getPassword(), authorities);
    }


}
