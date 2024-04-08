package com.queijos_finos.main.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.queijos_finos.main.model.Contrato;
import com.queijos_finos.main.model.Propriedade;




public interface PropriedadeRepository extends JpaRepository<Propriedade, Long>{
	Page<Propriedade> findAll(Pageable pageable);
}
