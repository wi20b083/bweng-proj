package com.example.bwengproj.security;

import com.example.bwengproj.model.status.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
    @Serial
    private static final long serialVersionUID = -2550185165626007488L;
    @Value("${jwt.secret}")
    private String secret;

    private static String getClientIp(HttpServletRequest request) {
        String remoteAddr = "";
        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }
        return remoteAddr;
    }

    public static String getUserAgent(HttpServletRequest request) {
        String ua = "";
        if (request != null) {
            ua = request.getHeader("User-Agent");
        }
        return ua;
    }

    //retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public List<Role> getRolesFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return Collections.singletonList(claims.get("roles", Role.class));
    }

    public String getIpAddressFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return claims.get("ip", String.class);
    }

    public String getUserAgentFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return claims.get("user_agent", String.class);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //for retrieving any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //generate token for user
    public String generateToken(UserDetails userDetails, HttpServletRequest request) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("roles", userDetails.getAuthorities())
                .claim("ip", getClientIp(request))
                .claim("user_agent", getUserAgent(request))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    //validate token
    public Boolean validateToken(String token, UserDetails userDetails, HttpServletRequest request) {
        final String username = getUsernameFromToken(token);
        final String ipAddress = getIpAddressFromToken(token);
        final String userAgent = getUserAgentFromToken(token);
        return (userAgent.equals(getUserAgent(request)) && ipAddress.equals(getClientIp(request)) && username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
