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

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class VistaPropiedades extends JPanel {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static final long serialVersionUID = 1L;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public VistaPropiedades() {

		setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Valores actuales",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

		JPanel panelPropiedades = new JPanel();
		panelPropiedades.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panelPropiedades.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		GridBagLayout gbl_panelPropiedades = new GridBagLayout();
		gbl_panelPropiedades.columnWidths = new int[] { 195, 49, 0 };
		gbl_panelPropiedades.rowHeights = new int[] { 20, 20, 20, 0 };
		gbl_panelPropiedades.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_panelPropiedades.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelPropiedades.setLayout(gbl_panelPropiedades);

		JLabel lblAceptacion = new JLabel("% aceptacion respecto de la media");
		GridBagConstraints gbc_lblAceptacion = new GridBagConstraints();
		gbc_lblAceptacion.anchor = GridBagConstraints.EAST;
		gbc_lblAceptacion.insets = new Insets(0, 0, 5, 5);
		gbc_lblAceptacion.gridx = 0;
		gbc_lblAceptacion.gridy = 0;
		panelPropiedades.add(lblAceptacion, gbc_lblAceptacion);

		JSpinner spinnerAceptacion = new JSpinner();
		GridBagConstraints gbc_spinnerAceptacion = new GridBagConstraints();
		gbc_spinnerAceptacion.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinnerAceptacion.anchor = GridBagConstraints.NORTH;
		gbc_spinnerAceptacion.insets = new Insets(0, 0, 5, 0);
		gbc_spinnerAceptacion.gridx = 1;
		gbc_spinnerAceptacion.gridy = 0;
		panelPropiedades.add(spinnerAceptacion, gbc_spinnerAceptacion);
		spinnerAceptacion.setModel(new SpinnerNumberModel(1, 1, 100, 1));

		JLabel lblPisoRuido = new JLabel("piso de ruido alarmas");
		GridBagConstraints gbc_lblPisoRuido = new GridBagConstraints();
		gbc_lblPisoRuido.anchor = GridBagConstraints.EAST;
		gbc_lblPisoRuido.insets = new Insets(0, 0, 5, 5);
		gbc_lblPisoRuido.gridx = 0;
		gbc_lblPisoRuido.gridy = 1;
		panelPropiedades.add(lblPisoRuido, gbc_lblPisoRuido);

		JSpinner spinnerPisoRuido = new JSpinner();
		GridBagConstraints gbc_spinnerPisoRuido = new GridBagConstraints();
		gbc_spinnerPisoRuido.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinnerPisoRuido.anchor = GridBagConstraints.NORTH;
		gbc_spinnerPisoRuido.insets = new Insets(0, 0, 5, 0);
		gbc_spinnerPisoRuido.gridx = 1;
		gbc_spinnerPisoRuido.gridy = 1;
		panelPropiedades.add(spinnerPisoRuido, gbc_spinnerPisoRuido);
		spinnerPisoRuido.setModel(new SpinnerNumberModel(1, 1, 3600, 1));

		JLabel lblTechoRuido = new JLabel("piso de ruido alarmas");
		GridBagConstraints gbc_lblTechoRuido = new GridBagConstraints();
		gbc_lblTechoRuido.anchor = GridBagConstraints.EAST;
		gbc_lblTechoRuido.insets = new Insets(0, 0, 0, 5);
		gbc_lblTechoRuido.gridx = 0;
		gbc_lblTechoRuido.gridy = 2;
		panelPropiedades.add(lblTechoRuido, gbc_lblTechoRuido);

		JSpinner spinnerTechoRuido = new JSpinner();
		GridBagConstraints gbc_spinnerTechoRuido = new GridBagConstraints();
		gbc_spinnerTechoRuido.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinnerTechoRuido.anchor = GridBagConstraints.NORTH;
		gbc_spinnerTechoRuido.gridx = 1;
		gbc_spinnerTechoRuido.gridy = 2;
		panelPropiedades.add(spinnerTechoRuido, gbc_spinnerTechoRuido);
		spinnerTechoRuido.setModel(new SpinnerNumberModel(0, 0, 3600, 1));

		JPanel panelBtnConfirmar = new JPanel();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
				Alignment.TRAILING,
				groupLayout
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								groupLayout
										.createParallelGroup(Alignment.TRAILING)
										.addComponent(panelBtnConfirmar, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
												258, Short.MAX_VALUE)
										.addComponent(panelPropiedades, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
												316, Short.MAX_VALUE)).addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(panelPropiedades, GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(panelBtnConfirmar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE).addContainerGap()));

		JButton btnConfirmarCambios = new JButton("confirmar cambios");
		panelBtnConfirmar.add(btnConfirmarCambios);
		setLayout(groupLayout);
	}
}