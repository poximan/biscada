/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package vistas;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class CompDuracionAlarma extends JPanel implements EventoConfigurable {

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

	public CompDuracionAlarma(String titulo, int valor_inicial, boolean estado_inicial_check) {

		FlowLayout flowLayout = (FlowLayout) getLayout();
		flowLayout.setVgap(1);
		flowLayout.setHgap(2);

		chckbxSegundos = new JCheckBox(titulo, estado_inicial_check);
		add(chckbxSegundos);

		spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(1, 1, 1800, 1));
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
				if (chckbxSegundos.isSelected())
					spinner.setEnabled(true);
				else
					spinner.setEnabled(false);
			}
		});
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	public Integer getSegundos() {

		if (spinner.isEnabled())
			return (Integer) spinner.getValue();
		return null;
	}

	/* ............................................. */
	/* ............................................. */
	/* SET'S ....................................... */
	/* ............................................. */
}