package com.eventcafecloud.oauth.token;

import com.eventcafecloud.oauth.exception.TokenValidFailedException;
import com.eventcafecloud.user.domain.User;
import com.eventcafecloud.user.domain.type.RoleType;
import com.eventcafecloud.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
public class AuthTokenProvider {

    private final Key key;
    private static final String AUTHORITIES_KEY = "role";
    private static final String NICKNAME_KEY = "nickName";
    @Autowired
    private UserRepository userRepository;

    public AuthTokenProvider(String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public AuthToken createAuthToken(String userEmail, Date expiry) {
        return new AuthToken(userEmail, expiry, key);
    }

    public AuthToken createAuthToken(String userEmail, String nickName, String role, Date expiry) {
        return new AuthToken(userEmail, nickName, role, expiry, key);
    }

    public AuthToken convertAuthToken(String token) {
        return new AuthToken(token, key);
    }

    public Authentication getAuthentication(AuthToken authToken) {

        if (authToken.validate()) {
            Claims claims = authToken.getTokenClaims();
            Collection<? extends GrantedAuthority> authorities =
                    Arrays.stream(new String[]{claims.get(AUTHORITIES_KEY).toString()})
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());
            String userNickname = claims.get(NICKNAME_KEY, String.class);
            RoleType roleType = RoleType.of(claims.get(AUTHORITIES_KEY).toString());

            User principal = userRepository.findByUserEmail(claims.getSubject()).orElseThrow();
            log.debug("claims subject := [{}]", claims.getSubject());

            return new UsernamePasswordAuthenticationToken(principal, authToken, authorities);
        } else {
            throw new TokenValidFailedException();
        }
    }

    public String getUserEmailByToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }
}

