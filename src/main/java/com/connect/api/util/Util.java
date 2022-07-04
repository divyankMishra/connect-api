package com.connect.api.util;

import org.springframework.security.core.context.SecurityContextHolder;

public class Util {
    public static String getCurrentUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
