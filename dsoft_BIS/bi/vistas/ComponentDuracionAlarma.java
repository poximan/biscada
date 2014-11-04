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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class ComponentDuracionAlarma extends JPanel implements EventoConfigurable {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static final long serialVersionUID = 1L;

	private JSpinner spinner;
	private JCheckBox chckbxSegundos;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public ComponentDuracionAlarma(String titulo, boolean estado_inicial_check) {

		FlowLayout flowLayout = (FlowLayout) getLayout();
		flowLayout.setVgap(1);
		flowLayout.setHgap(2);

		chckbxSegundos = new JCheckBox(titulo, estado_inicial_check);
		add(chckbxSegundos);

		spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(2, 0, 1800, 1));
		add(spinner);

		JLabel lblSeg = new JLabel("seg.");
		add(lblSeg);

		configEventos();
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	public void configEventos() {
		chckbxSegundos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (chckbxSegundos.isEnabled())
					spinner.setEnabled(false);
				else
					spinner.setEnabled(true);
			}
		});
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	public int getSegundos() {

		if (spinner.isEnabled())
			return ((Integer) spinner.getValue()).intValue();
		return 0;
	}

	/* ............................................. */
	/* ............................................. */
	/* SET'S ....................................... */
	/* ............................................. */
}