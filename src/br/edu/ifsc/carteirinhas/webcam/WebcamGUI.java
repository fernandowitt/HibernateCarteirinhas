package br.edu.ifsc.carteirinhas.webcam;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamDiscoveryEvent;
import com.github.sarxos.webcam.WebcamDiscoveryListener;
import com.github.sarxos.webcam.WebcamEvent;
import com.github.sarxos.webcam.WebcamListener;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamPicker;
import com.github.sarxos.webcam.WebcamResolution;

/**
 * @author Bartosz Firyn (SarXos), who created the API, and Fernando Corrêa
 *         Witt, IFSC trainee
 */
public class WebcamGUI extends JFrame implements Runnable, WebcamListener, WindowListener, UncaughtExceptionHandler,
		ItemListener, WebcamDiscoveryListener, WebcamUtil {

	private static final long serialVersionUID = 1L;

	//Essa parte aqui diz respeito à construção da interface gráfica
	
	private Webcam webcam = null;
	private WebcamPanel webcampanel = null;
	private WebcamPicker picker = null;
	private JButton screenshotBtn;
	private JButton saveBtn;
	private JButton useBtn;
	private Panel buttonPanel;
	private JLabel showImageLabel;
	private Panel screenshotPanel;
	private JLabel mensagemTexto;
	private JLabel bordaDeAuxilio;
	private JPanel panel;
	private JLabel statusScreenshot;
	public BufferedImage image;

	@Override
	public void run() {

		Webcam.addDiscoveryListener(this);

		setTitle("Java Webcam Capture POC");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setResizable(false);

		addWindowListener(this);

		picker = new WebcamPicker();
		picker.addItemListener(this);

		webcam = picker.getSelectedWebcam();

		if (webcam == null) {
			System.out.println("No webcams found...");
			System.exit(1);
		}

		webcam.setViewSize(WebcamResolution.VGA.getSize());
		webcam.addWebcamListener(WebcamGUI.this);

		screenshotBtn = new JButton("Tirar Foto");
		screenshotBtn.setPreferredSize(new Dimension(150, 30));
		
		useBtn = new JButton("Usar Foto");
		useBtn.setPreferredSize(new Dimension(150, 30));
		useBtn.setEnabled(false);
		
		saveBtn = new JButton("Salvar Foto");
		saveBtn.setPreferredSize(new Dimension(150, 30));
		saveBtn.setEnabled(false);

		buttonPanel = new Panel();
		buttonPanel.add(saveBtn);
		buttonPanel.add(screenshotBtn);
		buttonPanel.add(useBtn);

		webcampanel = new WebcamPanel(webcam, false);
		webcampanel.setFPSDisplayed(true);
		
		Border borderRed = BorderFactory.createDashedBorder(Color.RED, 2, 2);
		Border borderBlack = BorderFactory.createLineBorder(Color.BLACK, 1);
		
		panel = new JPanel();
		panel.setLayout(null);
		panel.setPreferredSize(new Dimension(670,500));
		bordaDeAuxilio = new JLabel();
		bordaDeAuxilio.setPreferredSize(new Dimension(266,369));
		bordaDeAuxilio.setBorder(borderRed);
		webcampanel.setBorder(borderBlack);
		mensagemTexto = new JLabel("Esta é a foto que foi tirada:");
		showImageLabel = new JLabel();
		statusScreenshot = new JLabel("");
		screenshotPanel = new Panel();
		screenshotPanel.setPreferredSize(new Dimension(295, 369));
		screenshotPanel.add(mensagemTexto);
		screenshotPanel.add(showImageLabel);
		screenshotPanel.add(statusScreenshot);
		
		panel.add(bordaDeAuxilio);
		webcampanel.add(panel);
		panel.setOpaque(true);
		panel.setBackground(new Color(0,0,0,0));
		bordaDeAuxilio.setVisible(true);
		
		webcampanel.setBounds(0, 0, 640, 480);
		bordaDeAuxilio.setBounds(204, 50, 266, 369);
		
		add(picker, BorderLayout.NORTH);
		add(webcampanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
		add(screenshotPanel, BorderLayout.EAST);

		// __________________________________
		// A partir daqui, tem a criação dos Listeners dos botões

		screenshotBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tiraScreenshot();

			}
		});

		saveBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				salvaScreenshot();
				//webcam.close(); //??
				//System.exit(0);
				dispose();
			}
		});
		
		useBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getFoto();
				//webcam.close(); //??
				//System.exit(0);
				dispose();
			}
		});
		
		// Continua a construção de interface gráfica
		
		pack();
		setVisible(true);

		Thread t = new Thread() {

			@Override
			public void run() {
				webcampanel.start();

			}
		};
		t.setName("example-starter");
		t.setDaemon(true);
		t.setUncaughtExceptionHandler(this);
		t.start();
		
	}
	
	// Aqui a webcam começa a rodar
	/*@Override
	public void iniciaWebcam() {
		SwingUtilities.invokeLater(new WebcamGUI());
		//TODO trocar esse método main para não iniciar a webcam automaticamente
	}*/

	// A partir daqui são métodos para eventos da janela
	@Override
	public void webcamOpen(WebcamEvent we) {
		System.out.println("webcam open");
	}

	@Override
	public void webcamClosed(WebcamEvent we) {
		System.out.println("webcam closed");
	}

	@Override
	public void webcamDisposed(WebcamEvent we) {
		System.out.println("webcam disposed");
	}

	@Override
	public void webcamImageObtained(WebcamEvent we) {
		// do nothing
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowClosed(WindowEvent e) {
		webcam.close();
	}

	@Override
	public void windowClosing(WindowEvent e) {
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		System.out.println("webcam viewer resumed");
		webcampanel.resume();
	}

	@Override
	public void windowIconified(WindowEvent e) {
		System.out.println("webcam viewer paused");
		webcampanel.pause();
	}

	// Exception em threads
	@Override
	public void uncaughtException(Thread t, Throwable e) {
		System.err.println(String.format("Exception in thread %s", t.getName()));
		e.printStackTrace();
	}

	// Controle de eventos da janela
	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getItem() != webcam) {
			if (webcam != null) {

				webcampanel.stop();

				remove(webcampanel);

				webcam.removeWebcamListener(this);
				webcam.close();

				webcam = (Webcam) e.getItem();
				webcam.setViewSize(WebcamResolution.VGA.getSize());
				webcam.addWebcamListener(this);

				System.out.println("selected " + webcam.getName());

				webcampanel = new WebcamPanel(webcam, false);
				webcampanel.setFPSDisplayed(true);

				add(webcampanel, BorderLayout.CENTER);
				pack();

				Thread t = new Thread() {

					@Override
					public void run() {
						webcampanel.start();
						
					}
				};
				t.setName("example-stoper");
				t.setDaemon(true);
				t.setUncaughtExceptionHandler(this);
				t.start();
			}
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public void webcamFound(WebcamDiscoveryEvent event) {
		if (picker != null) {
			picker.addItem(event.getWebcam());
		}
	}

	@Override
	public void webcamGone(WebcamDiscoveryEvent event) {
		if (picker != null) {
			picker.removeItem(event.getWebcam());
		}
	}

	// Método para captura de imagem
	@Override
	public void tiraScreenshot() {
		Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
		image = webcam.getImage();
		BufferedImage subimage = image.getSubimage(187, 55, 266, 369);
		ImageIcon img = new ImageIcon(subimage);
		showImageLabel.setIcon(img);
		showImageLabel.setBorder(border);
		saveBtn.setEnabled(true);
		useBtn.setEnabled(true);
		statusScreenshot.setText("");
	}
	
	// Método para retornar à classe Aluno o BufferedImage com a subimage da webcam
	public BufferedImage getFoto() {
			ImageIcon icon = (ImageIcon) showImageLabel.getIcon();
			BufferedImage image = (BufferedImage) ((Image) icon.getImage());
			return image;
	}
	
	// Método para salvar de fato a imagem
	@Override
	public void salvaScreenshot(){
		try {
			ImageIcon icon = (ImageIcon) showImageLabel.getIcon();
			BufferedImage image = (BufferedImage) ((Image) icon.getImage());
			ImageIO.write(image, "PNG", new File("test.png"));
			statusScreenshot.setText("Foto salva com sucesso");
		} catch (IOException e) {
			statusScreenshot.setText("Não foi possível realizar operação");
			e.printStackTrace();
		}
	}
}