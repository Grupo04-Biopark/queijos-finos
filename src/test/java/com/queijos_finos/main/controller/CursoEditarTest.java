package com.queijos_finos.main.controller;

import com.queijos_finos.main.model.Curso;
import com.queijos_finos.main.repository.CursosRepository;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static com.jayway.jsonpath.internal.path.PathCompiler.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CursoEditarTest {

    @Test
    void editarCursoNaoExistente() {
        CursosRepository cursosRepository = new CursosRepository() {
            @Override
            public Optional<Curso> findById(Long id) {
                // Simula a busca de um curso inexistente pelo ID
                return Optional.empty();
            }
        };

        CursosController cursosController = new CursosController(cursosRepository);

        Curso cursoEditado = new Curso();
        cursoEditado.setNome("Curso Editado");
        cursoEditado.setProfessor("Professor Editado");
        cursoEditado.setConteudo("Conteúdo Editado");
        cursoEditado.setDuracao("12 meses");

        // Chamando o método editCursos do controller
        try {
            cursosController.editCursos(1L, cursoEditado);
            fail("Expected an exception to be thrown");
        } catch (RuntimeException e) {
            assertEquals("Curso não encontrado com o ID: 1", e.getMessage());
        }
    }
