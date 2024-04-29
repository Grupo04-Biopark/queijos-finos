package com.queijos_finos.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import com.queijos_finos.main.model.Usuarios;
import com.queijos_finos.main.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;


@Controller
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository usuarioRepo;


	@GetMapping("/usuarios")
	public String showUsuarios(@RequestParam(name = "query", required = false) String query, Model model) {
		List<Usuarios> usuarios;
		if (query != null && !query.isEmpty()) {
			// Realizar a busca de acordo com a query
			usuarios = usuarioRepo.findByNomeContainingIgnoreCase(query);
		} else {
			// Obter todos os usuários paginados
			Pageable pageable = PageRequest.of(0, 20);
			usuarios = usuarioRepo.findAll(pageable).getContent();
		}
		model.addAttribute("usuarios", usuarios);
		return "usuarios"; // Retorna o nome da página Thymeleaf
	}


	@PostMapping("/usuarios")
	public String createUsuario(@RequestParam("id") Long id,
								@RequestParam("nome") String nome,
								@RequestParam("email") String email,
								@RequestParam("senha") String senha,
								Model model)  {

		if (id != -1) {
			usuarioRepo.findById(id)
					.map(usuarios -> {
						usuarios.setNome(nome);
						usuarios.setEmail(email);
						usuarios.setSenha(senha);
						return usuarioRepo.save(usuarios);
					})
					.orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + id));
		} else {
			Usuarios usuarios = new Usuarios();
			usuarios.setNome(nome);
			usuarios.setEmail(email);
			usuarios.setSenha(senha);
			usuarioRepo.save(usuarios);
		}

		model.addAttribute("mensagem", "Usuário salvo com sucesso");
		return "redirect:/usuarios";
	}

	@PostMapping("/usuarios/delete/{id}")
	public String deleteUsuario(@PathVariable("id") Long id,
								Model model) {

		usuarioRepo.deleteById(id);
		return "redirect:/usuarios";
	}



	@GetMapping("/usuarios/cadastrar")
	public String createUsuarioView(@RequestParam(required = false) Long idUsuarios, Model model) {
		if (idUsuarios != null) {
			Optional<Usuarios> usuarioOptional = usuarioRepo.findById(idUsuarios);
            usuarioOptional.ifPresent(usuarios -> model.addAttribute("usuario", usuarios));
		}

		return "usuariosCadastrar";
	}



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
		
		
		if(usu.getSenha().equals(senha)) {
			model.addAttribute("usu", usu);
			System.out.println(usu.getNome());
			return "paginaInicial";
		}else {
			
			model.addAttribute("mensagem", "Credenciais invalidas");
			return "login";
		}
		
	}
	
	
	
}
