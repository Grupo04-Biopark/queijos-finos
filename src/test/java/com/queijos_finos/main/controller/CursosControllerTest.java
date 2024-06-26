package com.queijos_finos.main.controller;

import com.queijos_finos.main.model.Curso;
import com.queijos_finos.main.repository.CursosRepository;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CursosControllerTest {

    @Test
    void cadastrarCurso() {
        // Mock do repositório apenas para simular o comportamento de salvamento
        CursosRepository cursosRepository = new CursosRepository() {
            @Override
            public void deleteCursoPropriedadeRelacionamento(Long cursoId) {
                // Não implementado para este teste
            }

            @Override
            public <S extends Curso> S save(S entity) {

                // Simula o salvamento de um curso
                entity.setId(1L); // Atribui um ID simulado
                return entity;
            }
        };

        // Criando uma instância do controller com o mock do repositório
        CursosController cursosController = new CursosController(cursosRepository);

        // Criando um objeto Curso para cadastrar
        Curso curso = new Curso();
        curso.setNome("Curso Teste");
        curso.setProfessor("Professor Teste");
        curso.setConteudo("Conteúdo Teste");
        curso.setDuracao("10 horas");

        // Chamando o método cadastrarCurso do controller
        ResponseEntity<String> response = cursosController.cadastrarCurso(curso);

        // Verificando se o retorno foi bem sucedido
        assertEquals(HttpStatus.FOUND, response.getStatusCode()); // Deve retornar um redirecionamento (302)
        assertNotNull(response.getHeaders().getLocation()); // Verifica se o header Location não é nulo
        assertEquals("/cursos", response.getHeaders().getLocation().getPath()); // Verifica se redireciona para /cursos
    }
}
