package com.queijos_finos.main.controller;





import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.hibernate.grammars.hql.HqlParser.IsNullPredicateContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
			 System.out.println(contrato.get().getDataEmissao());
			 model.addAttribute("contrato", contrato.get());
		}
		
		Pageable pageable = PageRequest.of(0, 20);
		
		List<Propriedade> propriedades = propriedadeRepo.findAll(pageable).getContent();
		
		model.addAttribute("propriedades", propriedades);
		return "contratosCadastrar";
	}
	
	@GetMapping("/contratos")
    public String showContratos(@RequestParam(defaultValue = "0") int pagina,
                                   Model model){
        Pageable pageable = PageRequest.of(pagina, 10);
        
        Page<Contrato> contratos = contratoRepo.findAll(pageable);

        model.addAttribute("contratos", contratos);
		
		return "contratos";
    }
	
	@PostMapping("/contratos")
	public String createContrato(@RequestParam("id") Long id,
								 @RequestParam("nome") String nome,
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
		if(id != -1) {
			contratoRepo.findById(id)
			.map(contrato ->{
				contrato.setNome(nome);
				contrato.setDataEmissao(dataEmissaoConv);
				contrato.setDataVercimento(dataVencimentoConv);
				contrato.setPropriedade(propriedade);
				return contratoRepo.save(contrato);
			})
			.orElseThrow(() -> new RuntimeException("Contrato não encontrado com o ID: " + id));
		}else {
			Contrato contrato = new Contrato(nome, dataEmissaoConv, dataVencimentoConv, propriedade);
			contratoRepo.save(contrato);
		}
	
		model.addAttribute("mensagem", "Contrato salvo com sucesso");
		return "redirect:/contratos";
	}
	
	
	@PostMapping("/contratos/delete/{id}")
	public String deleteContrato(@PathVariable("id") Long id) {
		System.out.println("teste");
		contratoRepo.deleteById(id);
		return "contratosCadastrar";
	}
	
	
	@GetMapping("/contratos/nome")
	public String showContratosFiltroNome(@RequestParam("nome") String nome,
	                                      Model model) {
	    Contrato contrato = new Contrato();
	    
	    System.out.println(nome);
	    Pageable pageable = PageRequest.of(0, 20);
	    
	    Page<Contrato> contratos = contratoRepo.findBynome(pageable, nome);
	    
	    model.addAttribute("contratos", contratos);
	    
	    // Retorna a página HTML com os contratos filtrados
	    return "contratos";
	}
	
	@GetMapping("/contratos/propriedade")
	public String showContratosFiltroNomePropriedade(@RequestParam("nome") String nome,
	                                      Model model) {
	    Contrato contrato = new Contrato();
	    
	    System.out.println(nome);
	    Pageable pageable = PageRequest.of(0, 20);
	    
	    Page<Contrato> contratos = contratoRepo.findByNomProp(pageable, nome);
	    
	    model.addAttribute("contratos", contratos);
	    
	    // Retorna a página HTML com os contratos filtrados
	    return "contratos";
	}

}
