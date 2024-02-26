package com.example.storeweb.domain.auth.service;

import com.example.storeweb.domain.auth.entity.TenantEntity;
import com.example.storeweb.domain.auth.repo.TenantRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;


@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final TenantRepository tenantRepository;



    @Override
    public UserDetails loadUserByUsername(String uuid) throws UsernameNotFoundException {
        TenantEntity tenant = tenantRepository.findTenantEntityByUuid(uuid);

//            new UsernameNotFoundException("User not found with uuid: " + uuid);



        return new org.springframework.security.core.userdetails.User(
                tenant.getUuid(),
                "", // 비밀번호는 사용하지 않으므로 빈 문자열로 설정
                Collections.singletonList(new SimpleGrantedAuthority(tenant.getRole().getValue()))
        );
    }
}
