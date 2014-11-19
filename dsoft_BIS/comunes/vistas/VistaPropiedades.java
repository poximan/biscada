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

	private CompSeleccionarDireccion btnCambiar;
	private JTextField txt_direccion_fuente;
	private JButton btnConfirmarCambios;

	private JSpinner spinnerAceptacion;
	private JSpinner spinnerMinimoRuido;
	private JLabel lblTiempoMaximo;
	private JSpinner spinnerMaximoRuido;
	private JLabel lblConexionABase;
	private JLabel lblDireccion;
	private JLabel lblUsuario;
	private JLabel lblContrasea;
	private JTextField txtDireccion;
	private JTextField txtUsuario;
	private JTextField txtContrasenia;
	private JLabel lblPuerto;
	private JTextField txtPuerto;

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
		gbl_panelPropiedades.columnWidths = new int[] { 0, 129, 110, 110, 110, 0, 0 };
		gbl_panelPropiedades.rowHeights = new int[] { 20, 20, 20, 0, 0, 20, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panelPropiedades.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_panelPropiedades.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, Double.MIN_VALUE };
		panelPropiedades.setLayout(gbl_panelPropiedades);

		JLabel lblDireccionFuente = new JLabel("direccion fuente");

		GridBagConstraints gbc_lblDireccionFuente = new GridBagConstraints();
		gbc_lblDireccionFuente.anchor = GridBagConstraints.EAST;
		gbc_lblDireccionFuente.insets = new Insets(0, 0, 5, 5);
		gbc_lblDireccionFuente.gridx = 1;
		gbc_lblDireccionFuente.gridy = 0;
		panelPropiedades.add(lblDireccionFuente, gbc_lblDireccionFuente);

		txt_direccion_fuente = new JTextField(ServPropiedades.getInstancia().getProperty(
				"Datos.DIRECCION_LECTURA_DATOS"));
		txt_direccion_fuente.setEditable(false);

		GridBagConstraints gbc_txt_direccion_fuente = new GridBagConstraints();
		gbc_txt_direccion_fuente.fill = GridBagConstraints.HORIZONTAL;
		gbc_txt_direccion_fuente.gridwidth = 3;
		gbc_txt_direccion_fuente.insets = new Insets(0, 0, 5, 5);
		gbc_txt_direccion_fuente.gridx = 2;
		gbc_txt_direccion_fuente.gridy = 0;
		panelPropiedades.add(txt_direccion_fuente, gbc_txt_direccion_fuente);
		txt_direccion_fuente.setColumns(10);

		btnCambiar = new CompSeleccionarDireccion(txt_direccion_fuente);

		GridBagConstraints gbc_btnCambiar = new GridBagConstraints();
		gbc_btnCambiar.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCambiar.insets = new Insets(0, 0, 5, 5);
		gbc_btnCambiar.gridx = 4;
		gbc_btnCambiar.gridy = 1;
		panelPropiedades.add(btnCambiar, gbc_btnCambiar);

		JLabel lblAceptacion = new JLabel("% aceptacion indicador");
		GridBagConstraints gbc_lblAceptacion = new GridBagConstraints();
		gbc_lblAceptacion.anchor = GridBagConstraints.EAST;
		gbc_lblAceptacion.insets = new Insets(0, 0, 5, 5);
		gbc_lblAceptacion.gridx = 1;
		gbc_lblAceptacion.gridy = 2;
		panelPropiedades.add(lblAceptacion, gbc_lblAceptacion);

		spinnerAceptacion = new JSpinner();
		GridBagConstraints gbc_spinnerAceptacion = new GridBagConstraints();
		gbc_spinnerAceptacion.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinnerAceptacion.insets = new Insets(0, 0, 5, 5);
		gbc_spinnerAceptacion.gridx = 2;
		gbc_spinnerAceptacion.gridy = 2;
		panelPropiedades.add(spinnerAceptacion, gbc_spinnerAceptacion);

		int valor_inicial = Integer.valueOf(ServPropiedades.getInstancia().getProperty(
				"Graficos.PORCENTAGE_ACEPTACION_RESPECTO_MEDIA"));
		spinnerAceptacion.setModel(new SpinnerNumberModel(valor_inicial, 1, 100, 1));

		JLabel lblDuracionParaConsiderar = new JLabel("duracion para considerar valida una alarma");
		GridBagConstraints gbc_lblDuracionParaConsiderar = new GridBagConstraints();
		gbc_lblDuracionParaConsiderar.gridwidth = 2;
		gbc_lblDuracionParaConsiderar.insets = new Insets(0, 0, 5, 5);
		gbc_lblDuracionParaConsiderar.gridx = 1;
		gbc_lblDuracionParaConsiderar.gridy = 4;
		panelPropiedades.add(lblDuracionParaConsiderar, gbc_lblDuracionParaConsiderar);

		spinnerMinimoRuido = new JSpinner();
		GridBagConstraints gbc_spinnerMinimoRuido = new GridBagConstraints();
		gbc_spinnerMinimoRuido.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinnerMinimoRuido.insets = new Insets(0, 0, 5, 5);
		gbc_spinnerMinimoRuido.gridx = 2;
		gbc_spinnerMinimoRuido.gridy = 5;
		panelPropiedades.add(spinnerMinimoRuido, gbc_spinnerMinimoRuido);

		spinnerMaximoRuido = new JSpinner();
		GridBagConstraints gbc_spinnerMaximoRuido = new GridBagConstraints();
		gbc_spinnerMaximoRuido.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinnerMaximoRuido.insets = new Insets(0, 0, 5, 5);
		gbc_spinnerMaximoRuido.gridx = 2;
		gbc_spinnerMaximoRuido.gridy = 6;
		panelPropiedades.add(spinnerMaximoRuido, gbc_spinnerMaximoRuido);

		int valor_maximo = Integer.valueOf(ServPropiedades.getInstancia().getProperty("Ruido.MAXIMA_DURACION_ALARMA"));
		valor_inicial = Integer.valueOf(ServPropiedades.getInstancia().getProperty("Ruido.MINIMA_DURACION_ALARMA"));

		spinnerMinimoRuido.setModel(new SpinnerNumberModel(valor_inicial, 1, valor_maximo, 1));
		spinnerMinimoRuido.setModel(new SpinnerNumberModel(valor_maximo, 1, valor_maximo, 1));

		JLabel lblTiempoMinimo = new JLabel("tiempo minimo");
		GridBagConstraints gbc_lblTiempoMinimo = new GridBagConstraints();
		gbc_lblTiempoMinimo.anchor = GridBagConstraints.EAST;
		gbc_lblTiempoMinimo.insets = new Insets(0, 0, 5, 5);
		gbc_lblTiempoMinimo.gridx = 1;
		gbc_lblTiempoMinimo.gridy = 5;
		panelPropiedades.add(lblTiempoMinimo, gbc_lblTiempoMinimo);

		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
				groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(panelPropiedades, GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
						.addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(panelPropiedades, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE).addContainerGap(35, Short.MAX_VALUE)));

		/*
		 * etiquetas de texto
		 */
		lblTiempoMaximo = new JLabel("tiempo maximo");
		GridBagConstraints gbc_lblTiempoMaximo = new GridBagConstraints();
		gbc_lblTiempoMaximo.anchor = GridBagConstraints.EAST;
		gbc_lblTiempoMaximo.insets = new Insets(0, 0, 5, 5);
		gbc_lblTiempoMaximo.gridx = 1;
		gbc_lblTiempoMaximo.gridy = 6;
		panelPropiedades.add(lblTiempoMaximo, gbc_lblTiempoMaximo);

		lblConexionABase = new JLabel("conexion a base de datos");
		GridBagConstraints gbc_lblConexionABase = new GridBagConstraints();
		gbc_lblConexionABase.gridwidth = 2;
		gbc_lblConexionABase.insets = new Insets(0, 0, 5, 5);
		gbc_lblConexionABase.gridx = 1;
		gbc_lblConexionABase.gridy = 8;
		panelPropiedades.add(lblConexionABase, gbc_lblConexionABase);

		lblDireccion = new JLabel("direccion");
		GridBagConstraints gbc_lblDireccion = new GridBagConstraints();
		gbc_lblDireccion.anchor = GridBagConstraints.EAST;
		gbc_lblDireccion.insets = new Insets(0, 0, 5, 5);
		gbc_lblDireccion.gridx = 1;
		gbc_lblDireccion.gridy = 9;
		panelPropiedades.add(lblDireccion, gbc_lblDireccion);

		lblPuerto = new JLabel("puerto");
		GridBagConstraints gbc_lblPuerto = new GridBagConstraints();
		gbc_lblPuerto.anchor = GridBagConstraints.EAST;
		gbc_lblPuerto.insets = new Insets(0, 0, 5, 5);
		gbc_lblPuerto.gridx = 3;
		gbc_lblPuerto.gridy = 9;
		panelPropiedades.add(lblPuerto, gbc_lblPuerto);

		lblUsuario = new JLabel("usuario");
		GridBagConstraints gbc_lblUsuario = new GridBagConstraints();
		gbc_lblUsuario.anchor = GridBagConstraints.EAST;
		gbc_lblUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsuario.gridx = 1;
		gbc_lblUsuario.gridy = 10;
		panelPropiedades.add(lblUsuario, gbc_lblUsuario);

		lblContrasea = new JLabel("contrase\u00F1a");
		GridBagConstraints gbc_lblContrasea = new GridBagConstraints();
		gbc_lblContrasea.anchor = GridBagConstraints.EAST;
		gbc_lblContrasea.insets = new Insets(0, 0, 5, 5);
		gbc_lblContrasea.gridx = 3;
		gbc_lblContrasea.gridy = 10;
		panelPropiedades.add(lblContrasea, gbc_lblContrasea);

		/*
		 * texto editable
		 */
		txtDireccion = new JTextField();
		GridBagConstraints gbc_txtDireccion = new GridBagConstraints();
		gbc_txtDireccion.insets = new Insets(0, 0, 5, 5);
		gbc_txtDireccion.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDireccion.gridx = 2;
		gbc_txtDireccion.gridy = 9;
		panelPropiedades.add(txtDireccion, gbc_txtDireccion);
		txtDireccion.setColumns(10);
		txtDireccion.setText(ServPropiedades.getInstancia().getProperty("Conexion.IP"));

		txtPuerto = new JTextField();
		GridBagConstraints gbc_txtPuerto = new GridBagConstraints();
		gbc_txtPuerto.insets = new Insets(0, 0, 5, 5);
		gbc_txtPuerto.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPuerto.gridx = 4;
		gbc_txtPuerto.gridy = 9;
		panelPropiedades.add(txtPuerto, gbc_txtPuerto);
		txtPuerto.setColumns(10);
		txtPuerto.setText(ServPropiedades.getInstancia().getProperty("Conexion.PUERTO"));

		txtUsuario = new JTextField();
		GridBagConstraints gbc_txtUsuario = new GridBagConstraints();
		gbc_txtUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_txtUsuario.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtUsuario.gridx = 2;
		gbc_txtUsuario.gridy = 10;
		panelPropiedades.add(txtUsuario, gbc_txtUsuario);
		txtUsuario.setColumns(10);
		txtUsuario.setText(ServPropiedades.getInstancia().getProperty("Conexion.USUARIO"));

		txtContrasenia = new JTextField();
		GridBagConstraints gbc_txtContrasenia = new GridBagConstraints();
		gbc_txtContrasenia.insets = new Insets(0, 0, 5, 5);
		gbc_txtContrasenia.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtContrasenia.gridx = 4;
		gbc_txtContrasenia.gridy = 10;
		panelPropiedades.add(txtContrasenia, gbc_txtContrasenia);
		txtContrasenia.setColumns(10);
		txtContrasenia.setText(ServPropiedades.getInstancia().getProperty("Conexion.CONTRASENIA"));

		/*
		 * boton confirmar
		 */
		btnConfirmarCambios = new JButton("confirmar cambios");
		btnConfirmarCambios.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.insets = new Insets(0, 0, 0, 5);
		gbc_button.anchor = GridBagConstraints.EAST;
		gbc_button.gridwidth = 2;
		gbc_button.gridx = 3;
		gbc_button.gridy = 12;
		panelPropiedades.add(btnConfirmarCambios, gbc_button);
		setLayout(groupLayout);
	}

	@Override
	public void configEventos() {

		btnConfirmarCambios.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				ServPropiedades.getInstancia().setProperty("Graficos.PORCENTAGE_ACEPTACION_RESPECTO_MEDIA",
						String.valueOf(spinnerAceptacion.getModel().getValue()));

				ServPropiedades.getInstancia().setProperty("Datos.DIRECCION_LECTURA_DATOS",
						txt_direccion_fuente.getText());

				ServPropiedades.getInstancia().setProperty("Ruido.MINIMA_DURACION_ALARMA",
						String.valueOf(spinnerMinimoRuido.getModel().getValue()));

				ServPropiedades.getInstancia().setProperty("Ruido.MAXIMA_DURACION_ALARMA",
						String.valueOf(spinnerMinimoRuido.getModel().getValue()));

				ServPropiedades.guardarCambios();

				frame_etl.dispose();
			}
		});
	}
}