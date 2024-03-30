package com.queijos_finos.main.model;

import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Contrato {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	private String nome;
	private Date dataEmissao;
	private Date dataVercimento;
	@Override
	public int hashCode() {
		return Objects.hash(Id, dataEmissao, dataVercimento, nome);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contrato other = (Contrato) obj;
		return Objects.equals(Id, other.Id) && Objects.equals(dataEmissao, other.dataEmissao)
				&& Objects.equals(dataVercimento, other.dataVercimento) && Objects.equals(nome, other.nome);
	}
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Date getDataEmissao() {
		return dataEmissao;
	}
	public void setDataEmissao(Date dataEmissao) {
		this.dataEmissao = dataEmissao;
	}
	public Date getDataVercimento() {
		return dataVercimento;
	}
	public void setDataVercimento(Date dataVercimento) {
		this.dataVercimento = dataVercimento;
	}
}
