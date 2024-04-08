package com.queijos_finos.main.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.queijos_finos.main.model.Contrato;


public interface ContratoRepository extends JpaRepository<Contrato, Long>{
	Page<Contrato> findAll(Pageable pageable);
	
}
