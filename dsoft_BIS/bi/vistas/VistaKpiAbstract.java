/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package vistas;

import graficas.GraficoKPI;

import java.awt.FlowLayout;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public abstract class VistaKpiAbstract extends JPanel implements PanelIniciable {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static final long serialVersionUID = 1L;

	private GroupLayout gl_panelGeneral;

	private JPanel panelIndicador;
	private JPanel panelResumen;

	private JLabel lblTotal;
	private JLabel lblPromedio;
	private JLabel lblActual;

	private JTextField txtTotal;
	private JTextField txtPromedio;
	private JTextField txtActual;

	private GraficoKPI indicador_kpi;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public VistaKpiAbstract() {

		iniciarComponentes();
		indicador_kpi = new GraficoKPI("");

		panelIndicador.add(indicador_kpi);
		panelIndicador.validate();
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	public void iniciarComponentes() {

		// -------------------------------------
		//
		// seccion componentes visuales
		// -------------------------------------

		gl_panelGeneral = new GroupLayout(this);
		panelIndicador = new JPanel();
		panelResumen = new JPanel();

		lblTotal = new JLabel("Total");
		lblPromedio = new JLabel("Promedio");
		lblActual = new JLabel("Actual");

		txtTotal = new JTextField();
		txtPromedio = new JTextField();
		txtActual = new JTextField();

		txtTotal.setEditable(false);
		txtTotal.setBounds(10, 31, 57, 20);

		txtPromedio.setEditable(false);
		txtPromedio.setBounds(10, 82, 57, 20);

		panelResumen.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		lblTotal.setBounds(10, 12, 57, 20);
		panelResumen.add(lblTotal);

		txtTotal.setHorizontalAlignment(SwingConstants.CENTER);
		panelResumen.add(txtTotal);
		txtTotal.setColumns(10);

		lblPromedio.setBounds(10, 62, 57, 20);
		panelResumen.add(lblPromedio);

		txtPromedio.setHorizontalAlignment(SwingConstants.CENTER);
		panelResumen.add(txtPromedio);
		txtPromedio.setColumns(10);

		gl_panelGeneral.setHorizontalGroup(gl_panelGeneral.createParallelGroup(Alignment.LEADING)
				.addGroup(
						gl_panelGeneral.createSequentialGroup().addContainerGap()
								.addComponent(panelResumen, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(panelIndicador, GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
								.addContainerGap()));
		gl_panelGeneral.setVerticalGroup(gl_panelGeneral.createParallelGroup(Alignment.LEADING).addGroup(
				gl_panelGeneral
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								gl_panelGeneral
										.createParallelGroup(Alignment.LEADING)
										.addComponent(panelResumen, GroupLayout.PREFERRED_SIZE, 147,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(panelIndicador, GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE))
						.addContainerGap()));

		panelResumen.add(lblActual);

		txtActual.setHorizontalAlignment(SwingConstants.CENTER);
		txtActual.setEditable(false);
		txtActual.setColumns(10);

		panelResumen.add(txtActual);

		setLayout(gl_panelGeneral);
	}

	public void notificarError(String mensaje) {

		JOptionPane optionPane = new JOptionPane(mensaje, JOptionPane.ERROR_MESSAGE);
		JDialog dialog = optionPane.createDialog("error");
		dialog.setAlwaysOnTop(true);
		dialog.setVisible(true);
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	public GraficoKPI getIndicador_kpi() {
		return indicador_kpi;
	}

	public JTextField getTxtActual() {
		return txtActual;
	}

	public JTextField getTxtPromedio() {
		return txtPromedio;
	}

	public JTextField getTxtTotal() {
		return txtTotal;
	}

	/* ............................................. */
	/* ............................................. */
	/* SET'S ....................................... */
	/* ............................................. */
}