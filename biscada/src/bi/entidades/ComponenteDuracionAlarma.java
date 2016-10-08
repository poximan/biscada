/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.entidades;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import comunes.vistas.EventoConfigurable;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * Class Responsibility Collaborator (CRC) design:
 * 
 * ==== parte clase =========================
 * 
 * YO REPRESENTO, un componente grafico que se utiliza en la pantalla BI
 * 
 * ==== parte responsabilidad ===============
 * 
 * LO QUE HAGO, doy al usuario la posibilidad de limitar la busqueda a alarmas
 * cuya duracion este contenida en un intervalo configurable
 * 
 * LO QUE CONOZCO, la clase javax.swing.JSpinner que me provee un selector para
 * definir el tiempo en segundos
 * 
 * ==== parte colaboracion ==================
 * 
 * MI COLABORADOR PRINCIPAL,
 * 
 * COMO INTERACTUO CON MI COLABORADOR,
 *
 * @author hdonato
 * 
 */
public class ComponenteDuracionAlarma extends JPanel implements EventoConfigurable {

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

	public ComponenteDuracionAlarma(String titulo, int valor_inicial, boolean estado_inicial_check) {

		FlowLayout flowLayout = (FlowLayout) getLayout();
		flowLayout.setVgap(1);
		flowLayout.setHgap(2);

		chckbxSegundos = new JCheckBox(titulo, estado_inicial_check);
		add(chckbxSegundos);

		spinner = new JSpinner();
		// valor inicial, min, max, step
		spinner.setModel(new SpinnerNumberModel(valor_inicial, 1, Integer.MAX_VALUE, 1));
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

		ActionListener cos_check = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (chckbxSegundos.isSelected())
					spinner.setEnabled(true);
				else
					spinner.setEnabled(false);
			}
		};
		chckbxSegundos.addActionListener(cos_check);
		cos_check.actionPerformed(null);
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

	public void setSegundos(Integer segundos) {

		if (spinner.isEnabled())
			spinner.setValue(segundos);
	}
}