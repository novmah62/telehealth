//package com.drewsec.commons.security.util;
//
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.oauth2.jwt.Jwt;
//
//public class AuthenticatedUserUtil {
//
//    private static Jwt getJwt() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.getPrincipal() instanceof Jwt jwt) {
//            return jwt;
//        }
//        throw new IllegalStateException("JWT token not found");
//    }
//
//    public static String getCurrentUserId() {
//        return getJwt().getSubject();
//    }
//
//    public static String getCurrentUserEmail() {
//        return getJwt().getClaimAsString("email");
//    }
//
//    public static String getCurrentUsername() {
//        return getJwt().getClaimAsString("preferred_username");
//    }
//
//    public static String getCurrentUserRole() {
//        return getJwt().getClaimAsString("role");
//    }
//
//}
