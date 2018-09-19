package br.edu.ifsc.carteirinhas.database;

import java.util.List;

import javax.persistence.EntityManager;

import br.edu.ifsc.carteirinhas.entidades.*;

public interface DBUtil {
	
	public EntityManager getDB();
	
	public void addAluno(Aluno aln);
	public void addAlunos(List<Aluno> alnLista);
	public Aluno getAluno(String cpf);
	public List<Aluno> getAlunos();
	public void removeAluno(Aluno aln);
	
	public void addCurso(Curso cur);
	public void addCursos(List<Curso> curLista);
	public Curso getCurso(int idCurso);
	public List<Curso> getCursos();
	public void removeCurso(Curso cur);
	
	public void addCarteirinha(Carteirinha cart);
	public void addCarteirinhas(List<Carteirinha> cartLista);
	public Carteirinha getCarteirinha(String matricula);
	public List<Carteirinha> getCarteirinhas();
	public void removeCarteirinha(Carteirinha cart);
	
	public void updateDB();
	public void close();
}
