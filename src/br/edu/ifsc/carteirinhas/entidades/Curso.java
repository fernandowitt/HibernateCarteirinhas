package br.edu.ifsc.carteirinhas.entidades;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Curso {
	private int idCurso;
	private String nome;
	private Date dataInicio;
	private int periodo;
	private Date dataFim;
	
	public Curso() {}
	
	public Curso(int idCurso, String nome, String inicio, int periodo, String fim) throws ParseException {
		super();
		this.idCurso = idCurso;
		this.nome = nome;
		SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");
		this.dataInicio = (Date)formatter1.parse(inicio);
		this.periodo = periodo;
		SimpleDateFormat formatter2 = new SimpleDateFormat("dd-MM-yyyy");
		this.dataFim = (Date)formatter2.parse(fim);
	}

	@Id
	public int getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(int idCurso) {
		this.idCurso = idCurso;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(String inicio) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		this.dataInicio = (Date)formatter.parse(inicio);
	}

	public int getPeriodo() {
		return periodo;
	}

	public void setPeriodo(int periodo) {
		this.periodo = periodo;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(String fim) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		this.dataFim = (Date)formatter.parse(fim);
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataFim == null) ? 0 : dataFim.hashCode());
		result = prime * result + ((dataInicio == null) ? 0 : dataInicio.hashCode());
		result = prime * result + idCurso;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + periodo;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Curso other = (Curso) obj;
		if (dataFim == null) {
			if (other.dataFim != null)
				return false;
		} else if (!dataFim.equals(other.dataFim))
			return false;
		if (dataInicio == null) {
			if (other.dataInicio != null)
				return false;
		} else if (!dataInicio.equals(other.dataInicio))
			return false;
		if (idCurso != other.idCurso)
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (periodo != other.periodo)
			return false;
		return true;
	}
	
	
	
}
