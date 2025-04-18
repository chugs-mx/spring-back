// TODO: implement if necessary the AuthenticationProvider for Nextjs JWT token
// only if we need to make the request from the client and not from the server.
// I will keep this file only for reference purposes
package com.chugs.chugs.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class NextJSAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
