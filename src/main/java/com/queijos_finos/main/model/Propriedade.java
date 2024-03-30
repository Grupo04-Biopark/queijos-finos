package com.queijos_finos.main.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class Propriedade {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idPropriedade;
	
	private String nomePropriedade;
	private String email;
	private int status;
	private String CPF;
	private String CNPJ;
	private String telefone;
	private String celular;
	private String rua;
	private String bairro;
	private String cidade;
	private String UF;
	private String latitude;
	private String longitude;
	private String nomeProdutor;
	
	@ManyToMany
	@JoinTable(name="Fornecedor_has_Propriedade",
		joinColumns = @JoinColumn(name="Propriedade_idPropriedade"),
		inverseJoinColumns = @JoinColumn(name="Fornecedor_idFornecedor"))
	private List<Fornecedor> fornecedores;
	
	@ManyToMany
	@JoinTable(name="Tecnologia_has_Propriedade",
		joinColumns = @JoinColumn(name="Propriedade_idPropriedade"),
		inverseJoinColumns = @JoinColumn(name="Tecnologia_idTecnologia"))
	private List<Tecnologias> tecnologias;
	
	@ManyToMany
	@JoinTable(name="Propriedade_has_Curso",
		joinColumns = @JoinColumn(name="Propriedade_idPropriedade"),
		inverseJoinColumns = @JoinColumn(name="Curso_idCurso"))
	private List<Curso> cursos;
	
	@OneToMany(mappedBy = "propriedade")
	private List<Imagem> imagens;
	
	@OneToMany(mappedBy = "propriedade")
	private List<Contrato> contratos;
	
	@OneToMany(mappedBy = "propriedade")
	private List<Amostra> amostras;
	
	
	
	public int getIdPropriedade() {
		return idPropriedade;
	}
	public void setIdPropriedade(int idPropriedade) {
		this.idPropriedade = idPropriedade;
	}
	public String getNomePropriedade() {
		return nomePropriedade;
	}
	public void setNomePropriedade(String nomePropriedade) {
		this.nomePropriedade = nomePropriedade;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getCPF() {
		return CPF;
	}
	public void setCPF(String cPF) {
		CPF = cPF;
	}
	public String getCNPJ() {
		return CNPJ;
	}
	public void setCNPJ(String cNPJ) {
		CNPJ = cNPJ;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
	public String getRua() {
		return rua;
	}
	public void setRua(String rua) {
		this.rua = rua;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getUF() {
		return UF;
	}
	public void setUF(String uF) {
		UF = uF;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getNomeProdutor() {
		return nomeProdutor;
	}
	public void setNomeProdutor(String nomeProdutor) {
		this.nomeProdutor = nomeProdutor;
	}
	
	
}
