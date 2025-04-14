package com.chugs.chugs.config;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;

public class NextJSAuthenticationToken extends AbstractAuthenticationToken {

    public NextJSAuthenticationToken() {
        super(AuthorityUtils.createAuthorityList("ROLE_USER"));
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return "Mr NEXTJS ";
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        throw new RuntimeException(" You can't touch this");
    }
}
