package br.edu.ifsc.carteirinhas.entidades;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

import br.edu.ifsc.carteirinhas.webcam.Webcam;
import br.edu.ifsc.carteirinhas.webcam.WebcamController;


@Entity
public class Aluno{
	
	private String cpf;
	private String nome;
	private Date dataNascimento;
	private byte[] foto;
	
	public Aluno() {}

	public Aluno(String cpf, String nome, String data, String enderecoFoto) throws ParseException, IOException {
		super();
		this.cpf = cpf;
		this.nome = nome;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		this.dataNascimento = (Date)formatter.parse(data);
		
		File inputFile = new File(enderecoFoto);
		FileInputStream inputStream = new FileInputStream(inputFile);
		byte[] fileBytes = new byte[(int) inputFile.length()];
		inputStream.read(fileBytes);
		inputStream.close();
		
		this.foto = fileBytes;
	}
	
	public Aluno(String cpf, String nome, String data) throws ParseException, IOException {
		super();
		this.cpf = cpf;
		this.nome = nome;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		this.dataNascimento = (Date)formatter.parse(data);
	}

	@Id
	@Column(length=11)
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}


	@Basic
	@Temporal(TemporalType.DATE)
	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String data) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		this.dataNascimento = (Date)formatter.parse(data);
	}
	
	@Lob
	@Column(columnDefinition="longblob")
	public byte[] getFoto() {
		return foto;
	}
	
	public void setFoto(String enderecoFoto) throws IOException {
		File inputFile = new File(enderecoFoto);
		FileInputStream inputStream = new FileInputStream(inputFile);
		byte[] fileBytes = new byte[(int) inputFile.length()];
		inputStream.read(fileBytes);
		inputStream.close();
		
		this.foto = fileBytes;
	}

	public void setFoto() throws IOException{
		Thread thread1 = new Thread(Webcam.wcam);
		thread1.run();
		
		Thread thread2 = new Thread(WebcamController.wcamController);
		thread2.run();
		synchronized(Webcam.wcam) {
			try {
				thread2.wait();
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		
	}
	
}
