package com.queijos_finos.main.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Fornecedor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	private String nome;
	private String email;
	private Double qualidade;
	private String nicho;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	@Override
	public int hashCode() {
		return Objects.hash(Id, email, nicho, nome, qualidade);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Fornecedor other = (Fornecedor) obj;
		return Objects.equals(Id, other.Id) && Objects.equals(email, other.email) && Objects.equals(nicho, other.nicho)
				&& Objects.equals(nome, other.nome) && Objects.equals(qualidade, other.qualidade);
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Double getQualidade() {
		return qualidade;
	}
	public void setQualidade(Double qualidade) {
		this.qualidade = qualidade;
	}
	public String getNicho() {
		return nicho;
	}
	public void setNicho(String nicho) {
		this.nicho = nicho;
	}
}
