package br.edu.ifsc.carteirinhas.entidades;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Carteirinha {
	
	private String matricula;
	private Date dataExpedicao;
	private Date dataVencimento;
	private int numExpedicao;
	private Aluno aluno;
	private Curso curso;
	
	public Carteirinha() {}

	public Carteirinha(String matricula, String expedicao, String vencimento, int numExpedicao, Aluno aluno,
			Curso curso) throws ParseException {
		super();
		this.matricula = matricula;
		SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");
		this.dataExpedicao = (Date)formatter1.parse(expedicao);
		SimpleDateFormat formatter2 = new SimpleDateFormat("dd-MM-yyyy");
		this.dataVencimento = (Date)formatter2.parse(vencimento);
		this.numExpedicao = numExpedicao;
		this.aluno = aluno;
		this.curso = curso;
	}
 
	@Id
	@Column(length=10)
	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public Date getDataExpedicao() {
		return dataExpedicao;
	}

	public void setDataExpedicao(String expedicao) throws ParseException {
		SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");
		this.dataExpedicao = (Date)formatter1.parse(expedicao);
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(String vencimento) throws ParseException {
		SimpleDateFormat formatter2 = new SimpleDateFormat("dd-MM-yyyy");
		this.dataVencimento = (Date)formatter2.parse(vencimento);
	}

	public int getNumExpedicao() {
		return numExpedicao;
	}

	public void setNumExpedicao(int numExpedicao) {
		this.numExpedicao = numExpedicao;
	}

	@ManyToOne
	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	@ManyToOne
	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aluno == null) ? 0 : aluno.hashCode());
		result = prime * result + ((curso == null) ? 0 : curso.hashCode());
		result = prime * result + ((dataExpedicao == null) ? 0 : dataExpedicao.hashCode());
		result = prime * result + ((dataVencimento == null) ? 0 : dataVencimento.hashCode());
		result = prime * result + ((matricula == null) ? 0 : matricula.hashCode());
		result = prime * result + numExpedicao;
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
		Carteirinha other = (Carteirinha) obj;
		if (aluno == null) {
			if (other.aluno != null)
				return false;
		} else if (!aluno.equals(other.aluno))
			return false;
		if (curso == null) {
			if (other.curso != null)
				return false;
		} else if (!curso.equals(other.curso))
			return false;
		if (dataExpedicao == null) {
			if (other.dataExpedicao != null)
				return false;
		} else if (!dataExpedicao.equals(other.dataExpedicao))
			return false;
		if (dataVencimento == null) {
			if (other.dataVencimento != null)
				return false;
		} else if (!dataVencimento.equals(other.dataVencimento))
			return false;
		if (matricula == null) {
			if (other.matricula != null)
				return false;
		} else if (!matricula.equals(other.matricula))
			return false;
		if (numExpedicao != other.numExpedicao)
			return false;
		return true;
	}
	
	
}
