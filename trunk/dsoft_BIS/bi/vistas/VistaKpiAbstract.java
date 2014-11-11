/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package vistas;

import graficas.GraficoHistorial;
import graficas.GraficoKPI;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import control_general.ServPropiedades;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public abstract class VistaKpiAbstract extends JPanel implements
		PanelIniciable, EventoKPIConfigurable {

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
	private JLabel labelVarianza;
	private JLabel labelDesvEstandar;

	private JTextField txtTotal;
	private JTextField txtPromedio;
	private JTextField txtActual;

	private GraficoKPI indicador_kpi;
	private JSpinner spinner_porcentaje;

	private JScrollPane panelHistograma;
	private GraficoHistorial histo_kpi;
	private JTextField textFieldVarianza;
	private JTextField textFieldDesvEstandar;
	private JLabel lblMedidasDeDispersin;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public VistaKpiAbstract() {
		setBorder(null);

		iniciarComponentes();
		indicador_kpi = new GraficoKPI();

		panelIndicador.add(indicador_kpi);
		panelIndicador.validate();

		histo_kpi = new GraficoHistorial();
		panelHistograma.setViewportView(histo_kpi);
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

		panelIndicador = new JPanel();
		panelResumen = new JPanel();
		panelResumen.setBorder(new LineBorder(new Color(0, 0, 0)));

		JLabel label = new JLabel("%");
		spinner_porcentaje = new JSpinner();

		int valor_inicial = Integer.valueOf(ServPropiedades.getInstancia()
				.getProperty("Graficos.PORCENTAGE_ACEPTACION_RESPECTO_MEDIA"));
		spinner_porcentaje.setModel(new SpinnerNumberModel(valor_inicial, 1,
				100, 1));

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

		lblMedidasDeDispersin = new JLabel("   Medidas de Dispersi\u00F3n     ");
		lblMedidasDeDispersin.setFont(new Font("Tahoma", Font.BOLD, 18));
		panelResumen.add(lblMedidasDeDispersin);

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

		panelHistograma = new JScrollPane();
		panelHistograma.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));

		gl_panelGeneral = new GroupLayout(this);
		gl_panelGeneral
				.setHorizontalGroup(gl_panelGeneral
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panelGeneral
										.createSequentialGroup()
										.addGap(21)
										.addGroup(
												gl_panelGeneral
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																panelHistograma,
																Alignment.TRAILING,
																GroupLayout.DEFAULT_SIZE,
																613,
																Short.MAX_VALUE)
														.addGroup(
																gl_panelGeneral
																		.createSequentialGroup()
																		.addComponent(
																				panelResumen,
																				GroupLayout.PREFERRED_SIZE,
																				272,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(18)
																		.addComponent(
																				panelIndicador,
																				GroupLayout.DEFAULT_SIZE,
																				323,
																				Short.MAX_VALUE)))
										.addGap(23)));
		gl_panelGeneral
				.setVerticalGroup(gl_panelGeneral
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panelGeneral
										.createSequentialGroup()
										.addGroup(
												gl_panelGeneral
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_panelGeneral
																		.createSequentialGroup()
																		.addContainerGap()
																		.addComponent(
																				panelIndicador,
																				GroupLayout.PREFERRED_SIZE,
																				250,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																gl_panelGeneral
																		.createSequentialGroup()
																		.addGap(27)
																		.addComponent(
																				panelResumen,
																				GroupLayout.PREFERRED_SIZE,
																				146,
																				GroupLayout.PREFERRED_SIZE)))
										.addPreferredGap(
												ComponentPlacement.UNRELATED)
										.addComponent(panelHistograma,
												GroupLayout.DEFAULT_SIZE, 158,
												Short.MAX_VALUE)
										.addContainerGap()));

		panelResumen.add(lblActual);

		txtActual.setHorizontalAlignment(SwingConstants.CENTER);
		txtActual.setEditable(false);
		txtActual.setColumns(10);

		panelResumen.add(txtActual);

		labelVarianza = new JLabel("Varianza");
		panelResumen.add(labelVarianza);

		textFieldVarianza = new JTextField();
		textFieldVarianza.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldVarianza.setEditable(false);
		textFieldVarianza.setColumns(10);
		panelResumen.add(textFieldVarianza);

		labelDesvEstandar = new JLabel("Desviaci\u00F3n Estandar");
		panelResumen.add(labelDesvEstandar);

		textFieldDesvEstandar = new JTextField();
		textFieldDesvEstandar.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldDesvEstandar.setEditable(false);
		textFieldDesvEstandar.setColumns(10);
		panelResumen.add(textFieldDesvEstandar);

		panelResumen.add(label);

		panelResumen.add(spinner_porcentaje);

		setLayout(gl_panelGeneral);
	}

	public void notificarError(String mensaje) {

		JOptionPane optionPane = new JOptionPane(mensaje,
				JOptionPane.ERROR_MESSAGE);
		JDialog dialog = optionPane.createDialog("error");
		dialog.setAlwaysOnTop(true);
		dialog.setVisible(true);
	}

	@Override
	public void configEventos(EventoKPI eventos) {

		spinner_porcentaje.getModel().addChangeListener(eventos);

	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	/**
	 * se solicita el grafico instanciado en la superclase y que hasta este
	 * momento no posee datos especificos relacionados con la dimension concreta
	 * que esta realizando la solicitud
	 * 
	 * @return
	 */
	public GraficoKPI getIndicador_kpi() {
		return indicador_kpi;
	}

	public GraficoHistorial getHisto_kpi() {
		return histo_kpi;
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

	public JTextField getTextFieldVarianza() {
		return textFieldVarianza;
	}

	public JTextField getTextFieldDesvEstandar() {
		return textFieldDesvEstandar;
	}

	public JSpinner getSpinner_porcentaje() {
		return spinner_porcentaje;
	}
}