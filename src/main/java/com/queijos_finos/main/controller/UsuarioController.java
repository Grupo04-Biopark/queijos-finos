package com.queijos_finos.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.queijos_finos.main.model.Usuarios;
import com.queijos_finos.main.repository.UsuarioRepository;


@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository usuarioRepo;
	
	@PostMapping("/login")
	public void login(@RequestParam("email") String email,
            					@RequestParam("senha") String senha,
            					Model model) {
		
		Usuarios usu = usuarioRepo.findByEmail(email);
		
		System.out.println(senha);
		System.out.println(usu.getSenha());
		
		if(usu.getSenha().equals(senha)) {
			System.out.println("Ta certo rapaiz");
		}else {
			System.out.println("Se fudeu");
		}
		
	}
}
