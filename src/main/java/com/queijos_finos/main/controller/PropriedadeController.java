package com.queijos_finos.main.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.queijos_finos.main.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.queijos_finos.main.repository.ContratoRepository;
import com.queijos_finos.main.repository.CursosRepository;
import com.queijos_finos.main.repository.FornecedorRepository;
import com.queijos_finos.main.repository.PropriedadeRepository;
import com.queijos_finos.main.repository.TecnologiaRepository;

@Controller
public class PropriedadeController {

	@Autowired
	private PropriedadeRepository propriedadeRepo;
	@Autowired
	private CursosRepository cursoRepo;
	@Autowired
	private TecnologiaRepository tecnologiaRepo;
	@Autowired
	private FornecedorRepository fornecedorRepo;


	@GetMapping("/propriedade")
	public String showPropriedade(Model model) {
		List<Propriedade> propriedade;
		Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
		propriedade = propriedadeRepo.findAll(pageable).getContent();
		model.addAttribute("propriedade", propriedade);
		return "propriedade";
	}

	@GetMapping("/propriedade/visualizar")
	public String detailsPropriedade(@RequestParam(required = false) Long idPropriedade, Model model) {
		Optional<Propriedade> propriedadeOptional = propriedadeRepo.findById(idPropriedade);
		Propriedade propriedade = propriedadeOptional.get();
		model.addAttribute("propriedade", propriedade);
		return "visualizarPropriedade"; // Nome do arquivo Thymeleaf para a visualização da propriedade
	}
	
	@GetMapping("/propriedade/cadastrar")
	public String createContratoView(@RequestParam(required = false) Long idPropriedade,
									 Model model) {
		
		if(idPropriedade != null) {
			 Optional<Propriedade> propriedade = propriedadeRepo.findById(idPropriedade);
			
			 model.addAttribute("propriedade", propriedade.get());
		}
		
		
		
		List<Curso> cursos = cursoRepo.findAll();
		List<Tecnologias> tecnologias = tecnologiaRepo.findAll();
		List<Fornecedor> fornecedores = fornecedorRepo.findAll();
		
		model.addAttribute("cursos", cursos);
		model.addAttribute("tecnologias", tecnologias);
		model.addAttribute("fornecedores", fornecedores);
		
		return "propriedadeCadastrar";
	}
	
//	@GetMapping("/contratos")
//    public String showContratos(
//                                   Model model){
//        Pageable pageable = PageRequest.of(30, 25);
//        
//        Page<Contrato> contratos = contratoRepo.findAll(pageable);
//
//        model.addAttribute("contratos", contratos);
//		
//		return "contratos"; 
//    }
	
	@PostMapping("/propriedade")
	public String createContrato(@RequestBody Propriedade propriedadeReq,
									Model model) throws ParseException {
		
		for (Tecnologias tecnologia : propriedadeReq.getTecnologias()) {
            List<Tecnologias> tecnologiaExistente = tecnologiaRepo.findByNome(tecnologia.getNome());
            if (tecnologiaExistente.isEmpty()) {
            	tecnologia.setId(null);
                tecnologiaRepo.save(tecnologia);
                tecnologia.setId(tecnologiaRepo.findFirstByOrderByIdDesc().getId());
            }
        }
		
		System.out.print(propriedadeReq);
		
		if(propriedadeReq.getIdPropriedade() != -1) {
			propriedadeRepo.findById(propriedadeReq.getIdPropriedade())
			.map(propriedade ->{
				propriedade.setNomePropriedade(propriedadeReq.getNomePropriedade());
                propriedade.setEmail(propriedadeReq.getEmail());
                propriedade.setStatus(propriedadeReq.getStatus());
                propriedade.setCPF(propriedadeReq.getCPF());
                propriedade.setCNPJ(propriedadeReq.getCNPJ());
                propriedade.setTelefone(propriedadeReq.getTelefone());
                propriedade.setCelular(propriedadeReq.getCelular());
                propriedade.setRua(propriedadeReq.getRua());
                propriedade.setBairro(propriedadeReq.getBairro());
                propriedade.setCidade(propriedadeReq.getCidade());
                propriedade.setUF(propriedadeReq.getUF());
                propriedade.setLatitude(propriedadeReq.getLatitude());
                propriedade.setLongitude(propriedadeReq.getLongitude());
                propriedade.setNomeProdutor(propriedadeReq.getNomeProdutor());
                propriedade.setCursos(propriedadeReq.getCursos());
                propriedade.setTecnologias(propriedadeReq.getTecnologias());
                propriedade.setFornecedores(propriedadeReq.getFornecedores());
				return propriedadeRepo.save(propriedade);
			})
			.orElseThrow(() -> new RuntimeException("Propriedade não encontrada com o ID: " + propriedadeReq.getIdPropriedade()));
		}else {
			propriedadeReq.setIdPropriedade(null);
			propriedadeRepo.save(propriedadeReq);
		}
	
		model.addAttribute("mensagem", "Propriedade salva com sucesso");
		return "redirect:/contratos";
	}
	
	
	@PostMapping("/propriedade/delete/{id}")
	public String deleteContrato(@PathVariable("id") Long id) {
		
		propriedadeRepo.deleteById(id);
		return "propriedadeCadastrar";
	}
}
