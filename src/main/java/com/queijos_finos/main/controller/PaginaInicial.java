package com.queijos_finos.main.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.queijos_finos.main.model.Usuarios;
import com.queijos_finos.main.repository.UsuarioRepository;

@Controller
public class PaginaInicial {
	@Autowired
	private UsuarioRepository usuarioRepo;
	
	@GetMapping("/paginaInicial")
	public String login(Model model) {
	
		
		Usuarios usu = usuarioRepo.findByEmail("ritzelarthur");
		
		model.addAttribute("usu", usu);
		return "paginaInicial";
		
	}
	
	
	
}
