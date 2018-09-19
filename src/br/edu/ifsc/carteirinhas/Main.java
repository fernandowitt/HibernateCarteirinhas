package br.edu.ifsc.carteirinhas;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import br.edu.ifsc.carteirinhas.database.*;
import br.edu.ifsc.carteirinhas.entidades.*;

public class Main {

	public static void main(String[] args) throws ParseException {	
		//Persistence.createEntityManagerFactory("HibernateCarteirinhasPU");
		DB.connection.addAluno(new Aluno("10101010101", "Fernando Corrêa Witt", "22-10-1999"));
		DB.connection.addAluno(new Aluno("10134010101", "Fernando Corrêa Witt", "22-10-1999"));
		DB.connection.addCurso(new Curso(224466, "Análise e Desenvolvimento de Sistemas", "02-02-2017", 4,"22-12-2019"));
		DB.connection.addCurso(new Curso(246810, "Análise e Desenvolvimento de Sistemas", "02-02-2018", 2,"22-12-2020"));
		DB.connection.addCarteirinha(new Carteirinha("1710030002", "02-02-2017", "22-12-2019", 1, DB.connection.getAluno("10101010101"), DB.connection.getCurso(224466)));
		
		//test//
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		String a = formatter.format(DB.connection.getAluno("10101010101").getDataNascimento());
		System.out.println(a);
		DB.connection.close();
	}
}
