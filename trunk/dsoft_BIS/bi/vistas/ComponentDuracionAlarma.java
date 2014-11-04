/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package vistas;

import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JLabel;
import java.awt.FlowLayout;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class ComponentDuracionAlarma extends JPanel {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static final long serialVersionUID = 1L;

	private JSpinner spinner;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public ComponentDuracionAlarma(String titulo, boolean estado_inicial_check) {
		FlowLayout flowLayout = (FlowLayout) getLayout();
		flowLayout.setVgap(1);
		flowLayout.setHgap(2);

		JCheckBox chckbxSegundos = new JCheckBox(titulo, estado_inicial_check);
		add(chckbxSegundos);

		spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(2, 0, 1800, 2));
		add(spinner);

		JLabel lblSeg = new JLabel("seg.");
		add(lblSeg);
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	public int getSegundos() {
		return ((Integer) spinner.getValue()).intValue();
	}

	/* ............................................. */
	/* ............................................. */
	/* SET'S ....................................... */
	/* ............................................. */
}