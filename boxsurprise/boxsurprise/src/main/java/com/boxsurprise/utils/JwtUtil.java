package com.boxsurprise.utils;

import com.boxsurprise.dtos.response.JwtResponseDTO;
import com.boxsurprise.enuns.TipoUsuario;
import com.boxsurprise.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);;

    public static String generateToken(Usuario usuario) {
        return Jwts.builder()
                .setSubject(usuario.getEmail())
                .claim("id", usuario.getIdUsuario())
                .claim("email", usuario.getEmail())
                .claim("tipoUsuario", usuario.getTipoUsuario().name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public static JwtResponseDTO decodeToken(String token) {
        Jws<Claims> jws = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token);
        Claims claims = jws.getBody();

        JwtResponseDTO jwtResponseDTO = new JwtResponseDTO();
        jwtResponseDTO.setId(claims.get("id", Integer.class));
        jwtResponseDTO.setEmail(claims.get("email", String.class));
        jwtResponseDTO.setTipoUsuario(TipoUsuario.valueOf(claims.get("tipoUsuario", String.class)));

        return jwtResponseDTO;
    }
}
