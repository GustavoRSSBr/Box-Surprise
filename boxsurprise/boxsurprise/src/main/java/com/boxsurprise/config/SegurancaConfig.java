package com.boxsurprise.config;

import com.boxsurprise.model.Usuario;
import com.boxsurprise.utils.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SegurancaConfig  {
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String criptografarSenha(String senha) {
        return passwordEncoder.encode(senha);
    }

    public boolean compararSenhaHash(String senha, String senhaHash) {
        return passwordEncoder.matches(senha, senhaHash);
    }

    public int buscarIdToken(String token) {
        return JwtUtil.decodeToken(token.replace("Bearer ", "")).getId();
    }

    public String buscarEmailToken(String token) {
        return JwtUtil.decodeToken(token.replace("Bearer ", "")).getEmail();
    }

    public String gerarToken(Usuario usuario) {
        return JwtUtil.generateToken(usuario);
    }
}
