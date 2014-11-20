/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package vistas;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import control_general.ServPropiedades;
import javax.swing.LayoutStyle.ComponentPlacement;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class VistaPropiedades extends JPanel implements PanelIniciable, EventoConfigurable {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static final long serialVersionUID = 1L;

	private JFrame frame_etl;

	private JLabel lblTiempoMaximo;
	private JLabel lblTiempoMinimo;

	private JPanel panelConexionBD;
	private JPanel panelRuidoAlarma;

	private CompSeleccionarDireccion btnCambiar;

	private JButton btnConfirmarCambios;
	private JButton btnPorDefecto;

	private JSpinner spinnerAceptacion;
	private JSpinner spinnerTiempoMinimo;
	private JSpinner spinnerTiempoMaximo;

	private JTextField txt_direccion_fuente;
	private JTextField txtDireccion;
	private JTextField txtPuerto;
	private JTextField txtContrasenia;
	private JTextField txtUsuario;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public VistaPropiedades(JFrame frame_etl) {

		this.frame_etl = frame_etl;

		iniciarComponentes();
		configEventos();
	}

	@Override
	public void iniciarComponentes() {

		setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Valores actuales",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

		JPanel panelPropiedades = new JPanel();
		panelPropiedades.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panelPropiedades.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

		GridBagLayout gbl_panelPropiedades = new GridBagLayout();
		gbl_panelPropiedades.columnWidths = new int[] { 124, 64, 196, 0, 85, 0 };
		gbl_panelPropiedades.rowHeights = new int[] { 20, 20, 20, 0, 103, 0, 0 };
		gbl_panelPropiedades.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panelPropiedades.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		panelPropiedades.setLayout(gbl_panelPropiedades);

		JLabel lblDireccionFuente = new JLabel("direccion fuente");

		GridBagConstraints gbc_lblDireccionFuente = new GridBagConstraints();
		gbc_lblDireccionFuente.anchor = GridBagConstraints.WEST;
		gbc_lblDireccionFuente.insets = new Insets(0, 0, 5, 5);
		gbc_lblDireccionFuente.gridx = 0;
		gbc_lblDireccionFuente.gridy = 0;
		panelPropiedades.add(lblDireccionFuente, gbc_lblDireccionFuente);

		txt_direccion_fuente = new JTextField(ServPropiedades.getInstancia().getProperty(
				"Datos.DIRECCION_LECTURA_DATOS"));
		txt_direccion_fuente.setEditable(false);

		GridBagConstraints gbc_txt_direccion_fuente = new GridBagConstraints();
		gbc_txt_direccion_fuente.fill = GridBagConstraints.HORIZONTAL;
		gbc_txt_direccion_fuente.gridwidth = 4;
		gbc_txt_direccion_fuente.insets = new Insets(0, 0, 5, 0);
		gbc_txt_direccion_fuente.gridx = 1;
		gbc_txt_direccion_fuente.gridy = 0;
		panelPropiedades.add(txt_direccion_fuente, gbc_txt_direccion_fuente);
		txt_direccion_fuente.setColumns(10);

		btnCambiar = new CompSeleccionarDireccion(txt_direccion_fuente);

		GridBagConstraints gbc_btnCambiar = new GridBagConstraints();
		gbc_btnCambiar.anchor = GridBagConstraints.WEST;
		gbc_btnCambiar.insets = new Insets(0, 0, 5, 0);
		gbc_btnCambiar.gridx = 4;
		gbc_btnCambiar.gridy = 1;
		panelPropiedades.add(btnCambiar, gbc_btnCambiar);

		JLabel lblAceptacion = new JLabel("% aceptacion indicador");
		GridBagConstraints gbc_lblAceptacion = new GridBagConstraints();
		gbc_lblAceptacion.anchor = GridBagConstraints.WEST;
		gbc_lblAceptacion.insets = new Insets(0, 0, 5, 5);
		gbc_lblAceptacion.gridx = 0;
		gbc_lblAceptacion.gridy = 2;
		panelPropiedades.add(lblAceptacion, gbc_lblAceptacion);

		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
				groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(panelPropiedades, GroupLayout.PREFERRED_SIZE, 529, Short.MAX_VALUE).addGap(8)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(panelPropiedades, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE).addContainerGap()));

		spinnerAceptacion = new JSpinner();
		GridBagConstraints gbc_spinnerAceptacion = new GridBagConstraints();
		gbc_spinnerAceptacion.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinnerAceptacion.insets = new Insets(0, 0, 5, 5);
		gbc_spinnerAceptacion.gridx = 1;
		gbc_spinnerAceptacion.gridy = 2;
		panelPropiedades.add(spinnerAceptacion, gbc_spinnerAceptacion);

		int valor_inicial = Integer.valueOf(ServPropiedades.getInstancia().getProperty(
				"Graficos.PORCENTAGE_ACEPTACION_RESPECTO_MEDIA"));
		spinnerAceptacion.setModel(new SpinnerNumberModel(valor_inicial, 1, 100, 1));

		panelRuidoAlarma = new JPanel();
		panelRuidoAlarma.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
				"rango valido de una alarma", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_panelRuidoAlarma = new GridBagConstraints();
		gbc_panelRuidoAlarma.anchor = GridBagConstraints.WEST;
		gbc_panelRuidoAlarma.gridwidth = 3;
		gbc_panelRuidoAlarma.insets = new Insets(0, 0, 5, 5);
		gbc_panelRuidoAlarma.gridx = 0;
		gbc_panelRuidoAlarma.gridy = 3;
		panelPropiedades.add(panelRuidoAlarma, gbc_panelRuidoAlarma);

		lblTiempoMaximo = new JLabel("tiempo minimo");
		lblTiempoMaximo.setHorizontalAlignment(SwingConstants.RIGHT);

		lblTiempoMinimo = new JLabel("tiempo maximo");
		lblTiempoMinimo.setHorizontalAlignment(SwingConstants.RIGHT);

		spinnerTiempoMinimo = new JSpinner();
		spinnerTiempoMaximo = new JSpinner();

		int valor_maximo = Integer.valueOf(ServPropiedades.getInstancia().getProperty("Ruido.MAXIMA_DURACION_ALARMA"));
		valor_inicial = Integer.valueOf(ServPropiedades.getInstancia().getProperty("Ruido.MINIMA_DURACION_ALARMA"));
		spinnerTiempoMinimo.setModel(new SpinnerNumberModel(valor_maximo, 1, valor_maximo, 1));
		// spinnerTiempoMinimo.setModel(new SpinnerNumberModel(valor_inicial, 1, valor_maximo, 1));
		spinnerTiempoMaximo.setModel(new SpinnerNumberModel(1, 51, 1, 1));

		GroupLayout gl_panelRuidoAlarma = new GroupLayout(panelRuidoAlarma);
		gl_panelRuidoAlarma.setHorizontalGroup(gl_panelRuidoAlarma.createParallelGroup(Alignment.LEADING).addGroup(
				gl_panelRuidoAlarma
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								gl_panelRuidoAlarma
										.createParallelGroup(Alignment.LEADING)
										.addComponent(lblTiempoMaximo, GroupLayout.PREFERRED_SIZE, 83,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(lblTiempoMinimo, GroupLayout.PREFERRED_SIZE, 89,
												GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(
								gl_panelRuidoAlarma
										.createParallelGroup(Alignment.LEADING)
										.addComponent(spinnerTiempoMinimo, GroupLayout.PREFERRED_SIZE, 55,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(spinnerTiempoMaximo, GroupLayout.DEFAULT_SIZE, 55,
												Short.MAX_VALUE)).addContainerGap()));
		gl_panelRuidoAlarma.setVerticalGroup(gl_panelRuidoAlarma.createParallelGroup(Alignment.LEADING).addGroup(
				gl_panelRuidoAlarma
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								gl_panelRuidoAlarma
										.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblTiempoMaximo)
										.addComponent(spinnerTiempoMinimo, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(
								gl_panelRuidoAlarma
										.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblTiempoMinimo)
										.addComponent(spinnerTiempoMaximo, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		gl_panelRuidoAlarma.linkSize(SwingConstants.VERTICAL, new Component[] { spinnerTiempoMinimo,
				spinnerTiempoMaximo });
		gl_panelRuidoAlarma.linkSize(SwingConstants.VERTICAL, new Component[] { lblTiempoMaximo, lblTiempoMinimo });
		gl_panelRuidoAlarma.linkSize(SwingConstants.HORIZONTAL, new Component[] { spinnerTiempoMinimo,
				spinnerTiempoMaximo });
		gl_panelRuidoAlarma.linkSize(SwingConstants.HORIZONTAL, new Component[] { lblTiempoMaximo, lblTiempoMinimo });
		panelRuidoAlarma.setLayout(gl_panelRuidoAlarma);

		panelConexionBD = new JPanel();
		panelConexionBD.setBorder(new TitledBorder(null, "conexion a base de datos", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panelConexionBD = new GridBagConstraints();
		gbc_panelConexionBD.anchor = GridBagConstraints.WEST;
		gbc_panelConexionBD.gridwidth = 3;
		gbc_panelConexionBD.insets = new Insets(0, 0, 5, 5);
		gbc_panelConexionBD.gridx = 0;
		gbc_panelConexionBD.gridy = 4;
		panelPropiedades.add(panelConexionBD, gbc_panelConexionBD);

		JLabel lblDireccion = new JLabel("direccion");
		lblDireccion.setHorizontalAlignment(SwingConstants.RIGHT);

		txtDireccion = new JTextField();
		txtDireccion.setText((String) null);
		txtDireccion.setColumns(10);

		txtPuerto = new JTextField();
		txtPuerto.setText((String) null);
		txtPuerto.setColumns(10);

		JLabel lblPuerto = new JLabel("puerto");
		lblPuerto.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel lblContrasenia = new JLabel("contrase\u00F1a");
		lblContrasenia.setHorizontalAlignment(SwingConstants.RIGHT);

		txtContrasenia = new JTextField();
		txtContrasenia.setText((String) null);
		txtContrasenia.setColumns(10);

		txtUsuario = new JTextField();
		txtUsuario.setText((String) null);
		txtUsuario.setColumns(10);

		JLabel lblUsuario = new JLabel("usuario");
		lblUsuario.setHorizontalAlignment(SwingConstants.RIGHT);
		GroupLayout gl_panelConexionBD = new GroupLayout(panelConexionBD);
		gl_panelConexionBD.setHorizontalGroup(gl_panelConexionBD.createParallelGroup(Alignment.LEADING).addGroup(
				gl_panelConexionBD
						.createSequentialGroup()
						.addGroup(
								gl_panelConexionBD
										.createParallelGroup(Alignment.LEADING)
										.addGroup(
												gl_panelConexionBD
														.createSequentialGroup()
														.addGap(10)
														.addComponent(lblDireccion, GroupLayout.PREFERRED_SIZE, 52,
																GroupLayout.PREFERRED_SIZE)
														.addGap(4)
														.addComponent(txtDireccion, GroupLayout.PREFERRED_SIZE, 88,
																GroupLayout.PREFERRED_SIZE))
										.addGroup(
												gl_panelConexionBD
														.createSequentialGroup()
														.addContainerGap()
														.addComponent(lblUsuario, GroupLayout.PREFERRED_SIZE, 35,
																GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(txtUsuario, GroupLayout.PREFERRED_SIZE, 88,
																GroupLayout.PREFERRED_SIZE)))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(
								gl_panelConexionBD
										.createParallelGroup(Alignment.LEADING, false)
										.addGroup(
												gl_panelConexionBD
														.createSequentialGroup()
														.addComponent(lblContrasenia, GroupLayout.PREFERRED_SIZE, 66,
																GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(txtContrasenia, GroupLayout.PREFERRED_SIZE, 88,
																GroupLayout.PREFERRED_SIZE))
										.addGroup(
												gl_panelConexionBD
														.createSequentialGroup()
														.addComponent(lblPuerto, GroupLayout.PREFERRED_SIZE, 32,
																GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.RELATED,
																GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(txtPuerto, GroupLayout.PREFERRED_SIZE, 88,
																GroupLayout.PREFERRED_SIZE)))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		gl_panelConexionBD
				.setVerticalGroup(gl_panelConexionBD
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panelConexionBD
										.createSequentialGroup()
										.addGroup(
												gl_panelConexionBD
														.createParallelGroup(Alignment.LEADING)
														.addGroup(
																gl_panelConexionBD.createSequentialGroup().addGap(14)
																		.addComponent(lblDireccion).addGap(11)
																		.addComponent(lblUsuario))
														.addGroup(
																gl_panelConexionBD
																		.createSequentialGroup()
																		.addContainerGap()
																		.addGroup(
																				gl_panelConexionBD
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addGroup(
																								gl_panelConexionBD
																										.createParallelGroup(
																												Alignment.BASELINE)
																										.addComponent(
																												lblPuerto)
																										.addComponent(
																												txtPuerto,
																												GroupLayout.PREFERRED_SIZE,
																												GroupLayout.DEFAULT_SIZE,
																												GroupLayout.PREFERRED_SIZE))
																						.addComponent(
																								txtDireccion,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE))
																		.addGroup(
																				gl_panelConexionBD
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addGroup(
																								gl_panelConexionBD
																										.createSequentialGroup()
																										.addGap(5)
																										.addComponent(
																												txtUsuario,
																												GroupLayout.PREFERRED_SIZE,
																												GroupLayout.DEFAULT_SIZE,
																												GroupLayout.PREFERRED_SIZE))
																						.addGroup(
																								gl_panelConexionBD
																										.createSequentialGroup()
																										.addGap(8)
																										.addComponent(
																												lblContrasenia))
																						.addGroup(
																								gl_panelConexionBD
																										.createSequentialGroup()
																										.addGap(5)
																										.addComponent(
																												txtContrasenia,
																												GroupLayout.PREFERRED_SIZE,
																												GroupLayout.DEFAULT_SIZE,
																												GroupLayout.PREFERRED_SIZE)))))
										.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		gl_panelConexionBD.linkSize(SwingConstants.HORIZONTAL, new Component[] { txtDireccion, txtUsuario });
		gl_panelConexionBD.linkSize(SwingConstants.HORIZONTAL, new Component[] { lblDireccion, lblUsuario });
		gl_panelConexionBD.linkSize(SwingConstants.HORIZONTAL, new Component[] { lblPuerto, lblContrasenia });
		gl_panelConexionBD.setHonorsVisibility(false);
		panelConexionBD.setLayout(gl_panelConexionBD);

		btnPorDefecto = new JButton("por defecto");
		GridBagConstraints gbc_btnPorDefecto = new GridBagConstraints();
		gbc_btnPorDefecto.anchor = GridBagConstraints.SOUTH;
		gbc_btnPorDefecto.insets = new Insets(0, 0, 0, 5);
		gbc_btnPorDefecto.gridx = 3;
		gbc_btnPorDefecto.gridy = 5;

		panelPropiedades.add(btnPorDefecto, gbc_btnPorDefecto);

		btnConfirmarCambios = new JButton("confirmar");
		btnConfirmarCambios.setHorizontalAlignment(SwingConstants.RIGHT);
		gbc_btnPorDefecto = new GridBagConstraints();
		gbc_btnPorDefecto.anchor = GridBagConstraints.SOUTH;
		gbc_btnPorDefecto.gridx = 4;
		gbc_btnPorDefecto.gridy = 5;

		panelPropiedades.add(btnConfirmarCambios, gbc_btnPorDefecto);
		setLayout(groupLayout);
	}

	@Override
	public void configEventos() {

		btnConfirmarCambios.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				/*
				 * graficos
				 */
				ServPropiedades.getInstancia().setProperty("Graficos.PORCENTAGE_ACEPTACION_RESPECTO_MEDIA",
						String.valueOf(spinnerAceptacion.getModel().getValue()));

				/*
				 * datos
				 */
				ServPropiedades.getInstancia().setProperty("Datos.DIRECCION_LECTURA_DATOS",
						txt_direccion_fuente.getText());

				/*
				 * ruido
				 */
				ServPropiedades.getInstancia().setProperty("Ruido.MINIMA_DURACION_ALARMA",
						String.valueOf(spinnerTiempoMaximo.getModel().getValue()));
				ServPropiedades.getInstancia().setProperty("Ruido.MAXIMA_DURACION_ALARMA",
						String.valueOf(spinnerTiempoMinimo.getModel().getValue()));

				/*
				 * conexion
				 */
				ServPropiedades.getInstancia().setProperty("Conexion.IP", txtDireccion.getText());
				ServPropiedades.getInstancia().setProperty("Conexion.PUERTO", txtPuerto.getText());
				ServPropiedades.getInstancia().setProperty("Conexion.USUARIO", txtUsuario.getText());
				ServPropiedades.getInstancia().setProperty("Conexion.CONTRASENIA", txtContrasenia.getText());

				ServPropiedades.guardarCambios();

				frame_etl.dispose();
			}
		});
	}
}