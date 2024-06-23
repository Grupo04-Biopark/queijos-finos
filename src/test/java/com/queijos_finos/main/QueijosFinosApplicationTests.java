package com.queijos_finos.main;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.queijos_finos.main.model.Usuarios;
import com.queijos_finos.main.UsuarioService;

@SpringBootTest
class QueijosFinosApplicationTests {

    @Autowired
    private UsuarioService usuarioService;

    @Test
    void cadastrarUsuarioComHash() {
        //cenario
        long id = -1;
        String nome = "Arthur";
        String email = "ritzelarthur@gmail.com";
        String senha = "admin";
        BCryptPasswordEncoder hashGenerator = new BCryptPasswordEncoder();

        //acao
        Usuarios usuario = usuarioService.cadastrarAlterarUsuarioHash(id, nome, email, senha);

        //validacao
        assertTrue(hashGenerator.matches(senha, usuario.getSenha()));
    }

    @Test
    void alterarUsuarioComHash() {
        //cenario
        long id = 202;
        String nome = "Arthur";
        String email = "ritzelarthur@gmail.com";
        String senha = "";
        BCryptPasswordEncoder hashGenerator = new BCryptPasswordEncoder();

        //acao
        Usuarios usuario = usuarioService.cadastrarAlterarUsuarioHash(id, nome, email, senha);

        //validacao
        assertEquals(email, usuario.getEmail());
        assertEquals(nome, usuario.getNome());
    }
    
    @Test
    void alterarSenhaUsuarioTest() {
        //cenario
        long id = 202;
        String novaSenha = "admin";
        BCryptPasswordEncoder hashGenerator = new BCryptPasswordEncoder();

        //acao
        Usuarios usuario = usuarioService.alterarSenhaUsuario(id, novaSenha);

        //validacao
        assertTrue(hashGenerator.matches(novaSenha, usuario.getSenha()));
    }
}
