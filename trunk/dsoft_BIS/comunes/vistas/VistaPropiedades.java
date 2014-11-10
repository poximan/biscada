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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
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

	private CompSeleccionarDireccion btnCambiar;
	private JTextField txt_direccion_fuente;
	private JButton btnConfirmarCambios;

	private JSpinner spinnerAceptacion;
	private JSpinner spinnerPisoRuido;

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
		gbl_panelPropiedades.columnWidths = new int[] { 195, 110, 110, 110, 0 };
		gbl_panelPropiedades.rowHeights = new int[] { 20, 20, 20, 20, 0, 0 };
		gbl_panelPropiedades.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panelPropiedades.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
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
		txt_direccion_fuente.setColumns(10);

		btnCambiar = new CompSeleccionarDireccion(txt_direccion_fuente);

		GridBagConstraints gbc_btnCambiar = new GridBagConstraints();
		gbc_btnCambiar.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCambiar.insets = new Insets(0, 0, 5, 0);
		gbc_btnCambiar.gridx = 3;
		gbc_btnCambiar.gridy = 1;
		panelPropiedades.add(btnCambiar, gbc_btnCambiar);

		JLabel lblAceptacion = new JLabel("% aceptacion respecto de la media");
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

		int valor_inicial = Integer.valueOf(ServPropiedades.getInstancia().getProperty(
				"Graficos.PORCENTAGE_ACEPTACION_RESPECTO_MEDIA"));
		spinnerAceptacion.setModel(new SpinnerNumberModel(valor_inicial, 1, 100, 1));

		spinnerPisoRuido = new JSpinner();
		GridBagConstraints gbc_spinnerPisoRuido = new GridBagConstraints();
		gbc_spinnerPisoRuido.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinnerPisoRuido.insets = new Insets(0, 0, 5, 5);
		gbc_spinnerPisoRuido.gridx = 1;
		gbc_spinnerPisoRuido.gridy = 3;
		panelPropiedades.add(spinnerPisoRuido, gbc_spinnerPisoRuido);

		valor_inicial = Integer.valueOf(ServPropiedades.getInstancia().getProperty("Ruido.PISO_RUIDO_ALARMA"));
		spinnerPisoRuido.setModel(new SpinnerNumberModel(valor_inicial, 1, 3600, 1));

		JLabel lblPisoRuido = new JLabel("piso de ruido alarmas");
		GridBagConstraints gbc_lblPisoRuido = new GridBagConstraints();
		gbc_lblPisoRuido.anchor = GridBagConstraints.EAST;
		gbc_lblPisoRuido.insets = new Insets(0, 0, 5, 5);
		gbc_lblPisoRuido.gridx = 0;
		gbc_lblPisoRuido.gridy = 3;
		panelPropiedades.add(lblPisoRuido, gbc_lblPisoRuido);

		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(panelPropiedades, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE).addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
				groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(panelPropiedades, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(103, Short.MAX_VALUE)));

		btnConfirmarCambios = new JButton("confirmar cambios");
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.gridwidth = 2;
		gbc_button.gridx = 2;
		gbc_button.gridy = 4;
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

				ServPropiedades.getInstancia().setProperty("Ruido.PISO_RUIDO_ALARMA",
						String.valueOf(spinnerPisoRuido.getModel().getValue()));

				ServPropiedades.guardarCambios();
			}
		});
	}
}