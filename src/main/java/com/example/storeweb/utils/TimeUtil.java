package com.example.storeweb.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
public class TimeUtil {

    public LocalDateTime getCurrentTime() {
        return LocalDateTime.now();
    }

    public String getCurrentTimeString() {
        return getCurrentTime().toString();
    }
}
