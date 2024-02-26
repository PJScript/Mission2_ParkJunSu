package com.example.storeweb.common.service;

import com.example.storeweb.domain.auth.entity.ActivityEntity;
import com.example.storeweb.domain.auth.repo.ActivityRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecurityService {
    @Autowired
    private ActivityRepository activityRepository;

    @Transactional
    public List<ActivityEntity> getActivitiesByUrlPattern(String urlPattern) {
        return activityRepository.findByUrlPattern(urlPattern);
    }
}