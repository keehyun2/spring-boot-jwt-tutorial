package me.silvernine.tutorial.athentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

public class FirebaseAuthenticationToken extends AbstractAuthenticationToken {
    private Object principal;
    private String credentials;

    public FirebaseAuthenticationToken(Object principal, String credentials) {
        super(Collections.emptyList());
        this.principal = principal;
        this.credentials = credentials;
    }

    public FirebaseAuthenticationToken(Object principal, String credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }
}
