package com.example.storeweb.common.security;

import java.util.List;

public class NoFilterUrlPattern {
    public static final List<String> PERMIT_ALL_URL_PATTERNS = List.of(
            "^/v1/auth/join$",
            "^/v1/auth/login$",
            "^/v1/auth/test$",
            "^/v1/auth/user/account/.*$"
    );

    public static final List<String> PERMIT_ALL_URL_PATTERNS_02 = List.of(
            "^/v1/auth/join$",
            "^/v1/auth/login$",
            "^/v1/auth/test$",
            "^/v1/auth/user/account/.*$"
    );
}
