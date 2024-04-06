package com.queijos_finos.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.queijos_finos.main.model.Usuarios;


public interface UsuarioRepository extends JpaRepository<Usuarios, Long>{
	@Query("select u from Usuarios u where u.email=:email")
	Usuarios findByEmail(String email); 
}
