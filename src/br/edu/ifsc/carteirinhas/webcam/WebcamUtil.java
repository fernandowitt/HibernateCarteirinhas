package br.edu.ifsc.carteirinhas.webcam;

import java.awt.image.BufferedImage;

public interface WebcamUtil {
	public void salvaScreenshot();
	public void tiraScreenshot();
	public BufferedImage getFoto();
}
