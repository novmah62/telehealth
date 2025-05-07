//package com.drewsec.commons.security;
//
//import com.drewsec.commons.security.annotation.AuthenticatedUserEmail;
//import com.drewsec.commons.security.annotation.AuthenticatedUserId;
//import com.drewsec.commons.security.annotation.AuthenticatedUserName;
//import org.springframework.core.MethodParameter;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.oauth2.jwt.Jwt;
//import org.springframework.web.bind.support.WebDataBinderFactory;
//import org.springframework.web.method.support.HandlerMethodArgumentResolver;
//import org.springframework.web.method.support.ModelAndViewContainer;
//import org.springframework.web.context.request.NativeWebRequest;
//
//public class AuthenticatedUserArgumentResolver implements HandlerMethodArgumentResolver {
//
//    @Override
//    public boolean supportsParameter(MethodParameter parameter) {
//        return parameter.hasParameterAnnotation(AuthenticatedUserId.class)
//                || parameter.hasParameterAnnotation(AuthenticatedUserName.class)
//                || parameter.hasParameterAnnotation(AuthenticatedUserEmail.class);
//    }
//
//    @Override
//    public Object resolveArgument(MethodParameter parameter,
//                                  ModelAndViewContainer mavContainer,
//                                  NativeWebRequest webRequest,
//                                  WebDataBinderFactory binderFactory) throws Exception {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.getPrincipal() instanceof Jwt jwt) {
//            if (parameter.hasParameterAnnotation(AuthenticatedUserId.class)) {
//                return jwt.getSubject(); // user id
//            } else if (parameter.hasParameterAnnotation(AuthenticatedUserName.class)) {
//                return jwt.getClaimAsString("preferred_username"); // username keycloak
//            } else if (parameter.hasParameterAnnotation(AuthenticatedUserEmail.class)) {
//                return jwt.getClaimAsString("email");
//            }
//        }
//        return null;
//    }
//}
