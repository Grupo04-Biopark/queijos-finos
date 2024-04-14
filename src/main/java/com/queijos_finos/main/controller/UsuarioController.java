package com.queijos_finos.main.controller;

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
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository usuarioRepo;
	
	@GetMapping("/")
	public ModelAndView login() {
		ModelAndView modelview = new ModelAndView();
		modelview.setViewName("login");
		return modelview;
	}
	
	
	@PostMapping("/login")
	public String login(@RequestParam("email") String email,
            					@RequestParam("senha") String senha,
            					Model model) {
		
		Usuarios usu = usuarioRepo.findByEmail(email);
		
		System.out.println(senha);
		System.out.println(usu.getSenha());
		
		if(usu.getSenha().equals(senha)) {
			return "redirect:/paginaInicial";
		}else {
			
			model.addAttribute("mensagem", "Credenciais invalidas");
			return "login";
		}
		
		
		
	}
}
