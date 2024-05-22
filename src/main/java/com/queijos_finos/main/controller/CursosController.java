package com.queijos_finos.main.controller;


import com.queijos_finos.main.model.Curso;
import com.queijos_finos.main.repository.CursosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class CursosController {

    @Autowired
    private CursosRepository cursosRepository;

    @GetMapping("/cursos")
    public String showCursos(@RequestParam(value = "search", required = false)String search, Model model) {
        Pageable pageable = PageRequest.of(0, 20);
        List<Curso> cursos = cursosRepository.findAll(pageable).getContent();
        model.addAttribute("cursos", cursos);
        return "cursos";
    }

    @GetMapping("/cursos/cadastrar")
    public String createCursosView(Model model) {
        return "cursosCadastrar";
    }

    @PostMapping("/cursos/cadastrar")
    public String createCursos(@ModelAttribute Curso curso, Model model) {
        cursosRepository.save(curso);
        model.addAttribute("mensagem", "Curso cadastrado com sucesso");
        return "redirect:/cursos";
    }

    @GetMapping("/cursos/editar/{id}")
    public String editCursosView(@PathVariable Long id, Model model) {
        Optional<Curso> curso = cursosRepository.findById(id);
        curso.ifPresent(c -> model.addAttribute("curso", c));
        return "cursosEditar";
    }

    @PostMapping("/cursos/editar/{id}")
    public String editCursos(@PathVariable Long id, @ModelAttribute Curso curso, Model model) {
        curso.setId(id);
        cursosRepository.save(curso);
        model.addAttribute("mensagem", "Curso atualizado com sucesso");
        return "redirect:/cursos";
    }

    @PostMapping("/cursos/excluir/{id}")
    public String deleteCursos(@PathVariable Long id, Model model) {
        cursosRepository.deleteById(id);
        model.addAttribute("mensagem", "Curso excluído com sucesso");
        return "redirect:/cursos";
    }
}

