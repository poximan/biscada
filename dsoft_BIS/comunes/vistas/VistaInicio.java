/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package vistas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import control_general.GestorBI;
import control_general.GestorETL;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class VistaInicio extends JPanel implements PanelIniciable, EventoConfigurable {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static final long serialVersionUID = 1L;

	private JPanel panel;

	private JButton btnGestionarArchivos;
	private JButton btnAnalisisDeDatos;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public VistaInicio() {

		iniciarComponentes();
		configEventos();
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	public void iniciarComponentes() {

		panel = new JPanel();
		add(panel);

		btnGestionarArchivos = new JButton("gestionar archivos");
		panel.add(btnGestionarArchivos);

		btnAnalisisDeDatos = new JButton("analisis de datos");
		panel.add(btnAnalisisDeDatos);
	}

	@Override
	public void configEventos() {

		btnGestionarArchivos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Runnable hilo = new Runnable() {
					@Override
					public void run() {
						GestorETL.getSingleton().mostrarVentana();
					}
				};
				new Thread(hilo);
			}
		});

		btnAnalisisDeDatos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Runnable hilo = new Runnable() {
					@Override
					public void run() {
						GestorBI.getSingleton().mostrarVentana();
					}
				};
				new Thread(hilo);
			}
		});
	}
}