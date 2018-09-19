package br.edu.ifsc.carteirinhas.database;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import br.edu.ifsc.carteirinhas.entidades.*;

public class HibernateSQLiteUtil implements DBUtil {
	private static EntityManager db;
	
	@Override
	public void addAluno(Aluno aln) {
		add(aln);
	}
	
	@Override
	public void addAlunos(List<Aluno> alnLista) {
		for(Aluno aln : alnLista)
			add(aln);
	}

	@Override
	public Aluno getAluno(String cpf) {
		return getDB().find(Aluno.class, cpf);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Aluno> getAlunos() {
		return getDB().createQuery("from Aluno").getResultList();
	}
	
	@Override
	public void removeAluno(Aluno aln) {
		remove(aln);
	}
	
	/*---------------------------------------------------------*/
	
	@Override
	public void addCurso(Curso cur) {
		add(cur);
	}
	
	@Override
	public void addCursos(List<Curso> curLista) {
		for(Curso cur : curLista)
			add(cur);
	}

	@Override
	public Curso getCurso(int idCurso) {
		return getDB().find(Curso.class, idCurso);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Curso> getCursos() {
		return getDB().createQuery("from Curso").getResultList();
	}

	@Override
	public void removeCurso(Curso cur) {
		remove(cur);
	}
	
	/*---------------------------------------------------------*/
	
	@Override
	public void addCarteirinha(Carteirinha cart) {
		add(cart);
	}
	
	@Override
	public void addCarteirinhas(List<Carteirinha> cartLista) {
		for(Carteirinha cart : cartLista)
			add(cart);
	}

	@Override
	public Carteirinha getCarteirinha(String matricula) {
		return getDB().find(Carteirinha.class, matricula);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Carteirinha> getCarteirinhas() {
		return getDB().createQuery("from Carteirinha").getResultList();
	}

	@Override
	public void removeCarteirinha(Carteirinha cart) {
		remove(cart);
}
	
	/*---------------------------------------------------------*/
	
	public EntityManager getDB() {
		if(db == null)
			db = Persistence.createEntityManagerFactory("HibernateCarteirinhasPU").createEntityManager();
		return db;
	}
	
	private void add(Object object) {
		EntityTransaction transaction = getDB().getTransaction();
		transaction.begin();
		getDB().persist(object);
		transaction.commit();
	}
	private void remove(Object object) {
		EntityTransaction transac = getDB().getTransaction();
		transac.begin();
		getDB().remove(object);
		transac.commit();
	}
	
	@Override
	public void updateDB() {
		EntityTransaction transac = getDB().getTransaction();
		transac.begin();
		transac.commit();
	}
	
	public void close() {
		if(db == null)
			return;
		if(!db.isOpen())
			return;
		
		db.close();
	}
}
