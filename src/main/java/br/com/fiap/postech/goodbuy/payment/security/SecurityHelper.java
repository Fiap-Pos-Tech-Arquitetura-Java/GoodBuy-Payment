package br.com.fiap.postech.goodbuy.payment.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class SecurityHelper {

    public String getToken() {
        return String.valueOf(SecurityContextHolder.getContext().getAuthentication().getCredentials());
    }
}
