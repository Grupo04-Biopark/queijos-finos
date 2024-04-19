package com.queijos_finos.main.controller;

import com.queijos_finos.main.model.Contrato;
import com.queijos_finos.main.model.Fornecedor;
import com.queijos_finos.main.model.Propriedade;
import com.queijos_finos.main.repository.FornecedorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class FornecedorController {
    @Autowired
    private FornecedorRepository fornecedorRepository;

    @GetMapping("/fornecedores")
    public String showFornecedores(Model model){
        Fornecedor fornecedor = new Fornecedor();

        Pageable pageable = PageRequest.of(0, 20);

        List<Fornecedor> fornecedores = fornecedorRepository.findAll(pageable).getContent();

        model.addAttribute("fornecedores", fornecedores);

        return "fornecedores";
    }

    @PostMapping("/fornecedores")
    public String createFornecedor(@RequestParam("id") Long id,
                                   @RequestParam("nome") String nome,
                                   @RequestParam("nicho") String nicho,
                                   @RequestParam("email") String email,
                                   @RequestParam("qualidade") Double qualidade,
                                   Model model) throws ParseException{

        if(id != -1) {
            fornecedorRepository.findById(id)
                    .map(fornecedor ->{
                        fornecedor.setNome(nome);
                        fornecedor.setNicho(nicho);
                        fornecedor.setEmail(email);
                        fornecedor.setQualidade(qualidade);
                        return fornecedorRepository.save(fornecedor);
                    })
                    .orElseThrow(() -> new RuntimeException("Fornecedor não encontrado com o ID: " + id));
        }else {
            Fornecedor fornecedor = new Fornecedor();
            fornecedorRepository.save(fornecedor);
        }

        model.addAttribute("mensagem", "Fornecedor salvo com sucesso");
        return "redirect:/fornecedores";
    }

    @PostMapping("/fornecedor/delete/{id}")
    public String deleteFornecedor(@PathVariable("id") Long id,
                                 Model model) {

        fornecedorRepository.deleteById(id);
        return "redirect:/fornecedores";
    }


    @GetMapping("/fornecedores/cadastrar")
    public String createFornecedorView(@RequestParam(required = false) Long idFornecedor,
                                     Model model) {

        if(idFornecedor != null) {
            Optional<Fornecedor> fornecedor = fornecedorRepository.findById(idFornecedor);
            model.addAttribute("fornecedor", fornecedor.get());
        }

        Pageable pageable = PageRequest.of(0, 20);
        return "fornecedoresCadastrar";
    }
}
