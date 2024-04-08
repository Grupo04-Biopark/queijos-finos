package com.queijos_finos.main.controller;





import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.hibernate.grammars.hql.HqlParser.IsNullPredicateContext;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.queijos_finos.main.model.Contrato;
import com.queijos_finos.main.model.Propriedade;
import com.queijos_finos.main.repository.ContratoRepository;
import com.queijos_finos.main.repository.PropriedadeRepository;



@Controller
public class Contratos {

	@Autowired
	private ContratoRepository contratoRepo;
	@Autowired
	private PropriedadeRepository propriedadeRepo;
	
	@GetMapping("/contratos/cadastrar")
	public String createContratoView(@RequestParam(required = false) Long idContrato,
									 Model model) {
		
		if(idContrato != null) {
			 Optional<Contrato> contrato = contratoRepo.findById(idContrato);
			 model.addAttribute("contrato", contrato);
		}
		
		Pageable pageable = PageRequest.of(0, 10);
		
		List<Propriedade> propriedades = propriedadeRepo.findAll(pageable).getContent();
		
		model.addAttribute("propriedades", propriedades);
		return "contratosCadastrar";
	}
	
	@GetMapping("/contratos")
	public String showContratos(Model model) {
		Contrato contrato = new Contrato();
		Pageable pageable = PageRequest.of(0, 10);
		
		List<Contrato> contratos = contratoRepo.findAll(pageable).getContent();
		
		
		model.addAttribute("contratos", contratos);
		
		return "contratos";
	}
	
	@PostMapping("/contratos")
	public String createContrato(@RequestParam("nome") String nome,
								 @RequestParam("dataEmissao") String dataEmissao,
								 @RequestParam("dataVencimento") String dataVencimento,
								 @RequestParam("idPropriedade") Long idPropriedade,
								 Model model) throws ParseException {
		
		Propriedade propriedade = new Propriedade();
		propriedade.setIdPropriedade(idPropriedade);
		
		System.out.println(dataEmissao);
		
		SimpleDateFormat formato = new SimpleDateFormat("y-M-d"); 
		Date dataEmissaoConv = formato.parse(dataEmissao);
		Date dataVencimentoConv = formato.parse(dataVencimento);
		
		System.out.println(dataEmissaoConv);
		
		Contrato contrato = new Contrato(nome, dataEmissaoConv, dataVencimentoConv, propriedade);
		contratoRepo.save(contrato);
		
		model.addAttribute("mensagem", "Contrato salvo com sucesso");
		return "redirect:/contratos";
	}
}
