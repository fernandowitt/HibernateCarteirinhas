package br.edu.ifsc.carteirinhas.webcam;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class WebcamController implements Runnable{
	public static final WebcamController wcamController = new WebcamController();
	
	public void run() {
		try {
			getFoto();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public byte[] getFoto() throws IOException {
		Thread insertFoto = new Thread();
		BufferedImage fotoimage = Webcam.wcam.getFoto();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(fotoimage, "png", baos);
		baos.flush();
		byte[] fotobyte = baos.toByteArray();
		return fotobyte;
	}
}
