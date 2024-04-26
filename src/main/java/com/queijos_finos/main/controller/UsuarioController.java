package com.queijos_finos.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	public String showUsuarios(Model model) {
		Usuarios usuarios = new Usuarios();
		Pageable pageable = PageRequest.of(0, 10);
		List<Usuarios> usuario = usuarioRepo.findAll(pageable).getContent();
		model.addAttribute("usuarios", usuario);
		return "usuarios";
	}

	@PostMapping("/usuarios")
	public String createUsuario(@RequestParam("id") Long id,
								@RequestParam("nome") String nome,
								@RequestParam("email") String email,
								@RequestParam("senha") String senha,
								Model model) {

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

	@PostMapping("/usuario/delete/{id}")
	public String deleteUsuario(@PathVariable("id") Long id,
								Model model) {

		usuarioRepo.deleteById(id);
		return "redirect:/usuarios";
	}

	@GetMapping("/usuarios/cadastrar")
	public String createUsuarioView(@RequestParam(required = false) Long idUsuario,
									Model model) {

		if (idUsuario != null) {
			Optional<Usuarios> usuarios = usuarioRepo.findById(idUsuario);
			model.addAttribute("usuario", usuarios.orElse(null));
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
