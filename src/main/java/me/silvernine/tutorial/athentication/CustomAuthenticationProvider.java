package me.silvernine.tutorial.athentication;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final CustomUserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String fToken = (String) authentication.getCredentials();
        FirebaseToken firebaseToken = null;
        log.debug("CustomAuthenticationProvider");

        try {
            firebaseToken = FirebaseAuth.getInstance().verifyIdToken(fToken);
        } catch (FirebaseAuthException e) {
            log.error("Firebase 토큰 유효성 체크 실패");
            throw new BadCredentialsException("Firebase 토큰 유효성 체크 실패. username=" + username, e);
        }

        UserDetails user = userDetailsService.loadUserByUsername(username);
        if (user == null) {
            throw new BadCredentialsException("username is not found. username=" + username);
        }

        return new CustomAuthenticationToken(firebaseToken.getEmail(), fToken, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return CustomAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
