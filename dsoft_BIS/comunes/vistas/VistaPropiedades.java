/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package vistas;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
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

	private JLabel lblTiempoMaximo;
	private JLabel lblTiempoMinimo;

	private JPanel panelConexionBD;
	private JPanel panelRuidoAlarma;

	private CompSeleccionarDireccion btnCambiarDireccion;

	private JButton btnPorDefecto;
	private JButton btnConfirmar;

	private JSpinner spinnerAceptacion;
	private JSpinner spinnerTiempoMinimo;
	private JSpinner spinnerTiempoMaximo;

	private JTextField txt_direccion_fuente;
	private JTextField txtURL;
	private JTextField txtContrasenia;
	private JTextField txtUsuario;

	private GridBagConstraints gbc_btnPorDefecto_1;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public VistaPropiedades() {

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
		gbl_panelPropiedades.columnWidths = new int[] { 92, 64, 92, 66, 0 };
		gbl_panelPropiedades.rowHeights = new int[] { 20, 20, 0, 90, 0 };
		gbl_panelPropiedades.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_panelPropiedades.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelPropiedades.setLayout(gbl_panelPropiedades);

		JLabel lblDireccionFuente = new JLabel("direccion fuente");

		GridBagConstraints gbc_lblDireccionFuente = new GridBagConstraints();
		gbc_lblDireccionFuente.anchor = GridBagConstraints.EAST;
		gbc_lblDireccionFuente.insets = new Insets(0, 0, 5, 5);
		gbc_lblDireccionFuente.gridx = 0;
		gbc_lblDireccionFuente.gridy = 0;
		panelPropiedades.add(lblDireccionFuente, gbc_lblDireccionFuente);

		txt_direccion_fuente = new JTextField(ServPropiedades.getInstancia().getProperty(
				"Datos.DIRECCION_LECTURA_DATOS"));
		txt_direccion_fuente.setEditable(false);

		GridBagConstraints gbc_txt_direccion_fuente = new GridBagConstraints();
		gbc_txt_direccion_fuente.fill = GridBagConstraints.HORIZONTAL;
		gbc_txt_direccion_fuente.gridwidth = 3;
		gbc_txt_direccion_fuente.insets = new Insets(0, 0, 5, 0);
		gbc_txt_direccion_fuente.gridx = 1;
		gbc_txt_direccion_fuente.gridy = 0;
		panelPropiedades.add(txt_direccion_fuente, gbc_txt_direccion_fuente);

		lblTiempoMaximo = new JLabel("tiempo minimo");
		lblTiempoMaximo.setHorizontalAlignment(SwingConstants.RIGHT);

		lblTiempoMinimo = new JLabel("tiempo maximo");
		lblTiempoMinimo.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel lblURL = new JLabel("URL");
		lblURL.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel lblContrasenia = new JLabel("contrase\u00F1a");
		lblContrasenia.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel lblUsuario = new JLabel("usuario");
		lblUsuario.setHorizontalAlignment(SwingConstants.RIGHT);

		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);

		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								groupLayout
										.createParallelGroup(Alignment.TRAILING)
										.addComponent(panelPropiedades, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
												335, Short.MAX_VALUE)
										.addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 374,
												Short.MAX_VALUE)).addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
				Alignment.TRAILING,
				groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(panelPropiedades, GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE).addGap(10)));

		btnPorDefecto = new JButton("por defecto");
		panel.add(btnPorDefecto);

		btnConfirmar = new JButton("confirmar");
		panel.add(btnConfirmar);

		int valor_inicial = Integer.valueOf(ServPropiedades.getInstancia().getProperty(
				"Graficos.PORCENTAGE_ACEPTACION_RESPECTO_MEDIA"));

		btnCambiarDireccion = new CompSeleccionarDireccion(txt_direccion_fuente);

		GridBagConstraints gbc_btnCambiar = new GridBagConstraints();
		gbc_btnCambiar.anchor = GridBagConstraints.WEST;
		gbc_btnCambiar.insets = new Insets(0, 0, 5, 0);
		gbc_btnCambiar.gridx = 3;
		gbc_btnCambiar.gridy = 1;
		panelPropiedades.add(btnCambiarDireccion, gbc_btnCambiar);

		JLabel lblAceptacion = new JLabel("% aceptacion KPI");
		GridBagConstraints gbc_lblAceptacion = new GridBagConstraints();
		gbc_lblAceptacion.anchor = GridBagConstraints.EAST;
		gbc_lblAceptacion.insets = new Insets(0, 0, 5, 5);
		gbc_lblAceptacion.gridx = 0;
		gbc_lblAceptacion.gridy = 2;
		panelPropiedades.add(lblAceptacion, gbc_lblAceptacion);

		spinnerAceptacion = new JSpinner();
		GridBagConstraints gbc_spinnerAceptacion = new GridBagConstraints();
		gbc_spinnerAceptacion.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinnerAceptacion.insets = new Insets(0, 0, 5, 5);
		gbc_spinnerAceptacion.gridx = 1;
		gbc_spinnerAceptacion.gridy = 2;
		panelPropiedades.add(spinnerAceptacion, gbc_spinnerAceptacion);

		spinnerAceptacion.setModel(new SpinnerNumberModel(valor_inicial, 1, 100, 1)); // valor inicial, min, max, step

		panelRuidoAlarma = new JPanel();
		panelRuidoAlarma.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
				"rango valido de una alarma", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_panelRuidoAlarma = new GridBagConstraints();
		gbc_panelRuidoAlarma.gridwidth = 2;
		gbc_panelRuidoAlarma.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelRuidoAlarma.insets = new Insets(0, 0, 5, 0);
		gbc_panelRuidoAlarma.gridx = 2;
		gbc_panelRuidoAlarma.gridy = 2;
		panelPropiedades.add(panelRuidoAlarma, gbc_panelRuidoAlarma);

		spinnerTiempoMinimo = new JSpinner();
		spinnerTiempoMaximo = new JSpinner();

		int valor_maximo = Integer.valueOf(ServPropiedades.getInstancia().getProperty("Ruido.MAXIMA_DURACION_ALARMA"));
		valor_inicial = Integer.valueOf(ServPropiedades.getInstancia().getProperty("Ruido.MINIMA_DURACION_ALARMA"));

		// valor inicial, min, max, step
		spinnerTiempoMinimo.setModel(new SpinnerNumberModel(valor_inicial, 1, Integer.MAX_VALUE, 1));

		// valor inicial, min, max, step
		spinnerTiempoMaximo.setModel(new SpinnerNumberModel(valor_maximo, 1, Integer.MAX_VALUE, 1));

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
										.addComponent(spinnerTiempoMinimo, GroupLayout.DEFAULT_SIZE, 217,
												Short.MAX_VALUE)
										.addComponent(spinnerTiempoMaximo, GroupLayout.DEFAULT_SIZE, 217,
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
		gl_panelRuidoAlarma.linkSize(SwingConstants.VERTICAL, new Component[] { lblTiempoMaximo, lblTiempoMinimo });
		gl_panelRuidoAlarma.linkSize(SwingConstants.VERTICAL, new Component[] { spinnerTiempoMinimo,
				spinnerTiempoMaximo });
		gl_panelRuidoAlarma.linkSize(SwingConstants.HORIZONTAL, new Component[] { lblTiempoMaximo, lblTiempoMinimo });
		panelRuidoAlarma.setLayout(gl_panelRuidoAlarma);

		panelConexionBD = new JPanel();
		panelConexionBD.setBorder(new TitledBorder(null, "conexion a base de datos", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panelConexionBD = new GridBagConstraints();
		gbc_panelConexionBD.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelConexionBD.gridwidth = 4;
		gbc_panelConexionBD.gridx = 0;
		gbc_panelConexionBD.gridy = 3;
		panelPropiedades.add(panelConexionBD, gbc_panelConexionBD);
		txtURL = new JTextField();
		txtURL.setText(ServPropiedades.getInstancia().getProperty("Conexion.URL"));

		txtContrasenia = new JTextField();
		txtContrasenia.setText(ServPropiedades.getInstancia().getProperty("Conexion.CONTRASENIA"));

		txtUsuario = new JTextField();
		txtUsuario.setText(ServPropiedades.getInstancia().getProperty("Conexion.USUARIO"));

		GroupLayout gl_panelConexionBD = new GroupLayout(panelConexionBD);
		gl_panelConexionBD.setHorizontalGroup(gl_panelConexionBD.createParallelGroup(Alignment.LEADING).addGroup(
				gl_panelConexionBD
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								gl_panelConexionBD
										.createParallelGroup(Alignment.LEADING)
										.addGroup(
												gl_panelConexionBD
														.createSequentialGroup()
														.addComponent(lblUsuario, GroupLayout.PREFERRED_SIZE, 35,
																GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(txtUsuario, GroupLayout.DEFAULT_SIZE, 130,
																Short.MAX_VALUE)
														.addGap(4)
														.addComponent(lblContrasenia, GroupLayout.PREFERRED_SIZE, 66,
																GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(txtContrasenia, GroupLayout.DEFAULT_SIZE, 130,
																Short.MAX_VALUE))
										.addGroup(
												gl_panelConexionBD
														.createSequentialGroup()
														.addComponent(lblURL, GroupLayout.PREFERRED_SIZE, 52,
																GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(txtURL, GroupLayout.DEFAULT_SIZE, 286,
																Short.MAX_VALUE))).addGap(3)));
		gl_panelConexionBD
				.setVerticalGroup(gl_panelConexionBD
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panelConexionBD
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_panelConexionBD
														.createParallelGroup(Alignment.LEADING)
														.addComponent(lblURL)
														.addGroup(
																gl_panelConexionBD
																		.createSequentialGroup()
																		.addComponent(txtURL,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(ComponentPlacement.RELATED)
																		.addGroup(
																				gl_panelConexionBD
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addGroup(
																								gl_panelConexionBD
																										.createSequentialGroup()
																										.addGap(7)
																										.addGroup(
																												gl_panelConexionBD
																														.createParallelGroup(
																																Alignment.BASELINE)
																														.addComponent(
																																txtUsuario,
																																GroupLayout.PREFERRED_SIZE,
																																GroupLayout.DEFAULT_SIZE,
																																GroupLayout.PREFERRED_SIZE)
																														.addComponent(
																																lblUsuario)))
																						.addGroup(
																								gl_panelConexionBD
																										.createSequentialGroup()
																										.addGap(10)
																										.addComponent(
																												lblContrasenia))
																						.addGroup(
																								gl_panelConexionBD
																										.createSequentialGroup()
																										.addGap(7)
																										.addComponent(
																												txtContrasenia,
																												GroupLayout.PREFERRED_SIZE,
																												GroupLayout.DEFAULT_SIZE,
																												GroupLayout.PREFERRED_SIZE)))))
										.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		gl_panelConexionBD.linkSize(SwingConstants.HORIZONTAL, new Component[] { lblURL, lblUsuario });
		gl_panelConexionBD.setHonorsVisibility(false);
		panelConexionBD.setLayout(gl_panelConexionBD);

		gbc_btnPorDefecto_1 = new GridBagConstraints();
		gbc_btnPorDefecto_1.anchor = GridBagConstraints.SOUTHWEST;
		gbc_btnPorDefecto_1.gridx = 3;
		gbc_btnPorDefecto_1.gridy = 4;

		setLayout(groupLayout);
	}

	@Override
	public void configEventos() {

		EventoPropiedades eventos = new EventoPropiedades(this, spinnerAceptacion, spinnerTiempoMaximo,
				spinnerTiempoMinimo, txt_direccion_fuente, txtURL, txtUsuario, txtContrasenia);

		btnConfirmar.addActionListener(eventos);
		btnPorDefecto.addActionListener(eventos);
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	public JButton getBtnPorDefecto() {
		return btnPorDefecto;
	}

	public JButton getBtnConfirmar() {
		return btnConfirmar;
	}
}