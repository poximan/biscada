/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.vistas.kpi;

import java.awt.Color;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import bi.graficas.GraficoHistorial;
import bi.graficas.GraficoKPI;
import bi.vistas.eventos.EventoKPI;
import bi.vistas.eventos.EventoKPIConfigurable;
import comunes.vistas.PanelIniciable;
import propiedades.controles.servicios.ServPropiedades;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.border.TitledBorder;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public abstract class VistaKpiAbstract extends JPanel implements PanelIniciable, EventoKPIConfigurable {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static final long serialVersionUID = 1L;

	private GroupLayout gl_panelGeneral;

	private JPanel panelIndicador;
	private JPanel panelResumen;

	private JLabel lblMaximo;
	private JLabel lblMinimo;
	private JLabel lblTotal;
	private JLabel lblPromedio;
	private JLabel lblActual;
	private JLabel lblVarianza;
	private JLabel lblD_estandar;

	private JTextField txtTotal;
	private JTextField txtPromedio;
	private JTextField txtActual;

	private GraficoKPI indicador_kpi;
	private JSpinner spinner_porcentaje;

	private JScrollPane panelHistograma;
	private GraficoHistorial histo_kpi;
	private JTextField txtVarianza;
	private JTextField txtD_estandar;
	private JTextField txtMinimo;
	private JTextField txtMaximo;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public VistaKpiAbstract() {

		iniciarComponentes();
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	public void configEventos(EventoKPI eventos) {
		spinner_porcentaje.getModel().addChangeListener(eventos);
	}

	@Override
	public void iniciarComponentes() {

		// -------------------------------------
		//
		// seccion componentes visuales
		// -------------------------------------

		indicador_kpi = new GraficoKPI();
		panelIndicador = new JPanel();
		panelIndicador
				.setBorder(new TitledBorder(null, "Indicador", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panelIndicador.add(indicador_kpi);

		panelResumen = new JPanel();
		panelResumen.setBorder(new TitledBorder(null, "Mediciones", TitledBorder.CENTER, TitledBorder.TOP, null, null));

		int valor_inicial = 1;
		try {
			valor_inicial = Integer.valueOf(
					ServPropiedades.getInstancia().getProperty("Graficos.PORCENTAGE_ACEPTACION_RESPECTO_MEDIA"));
		} catch (NumberFormatException e) {
		}

		GridBagLayout gbl_panelResumen = new GridBagLayout();
		gbl_panelResumen.columnWidths = new int[] { 0, 20, 46, 14, 12, 56, 0 };
		gbl_panelResumen.rowHeights = new int[] { 0, 20, 20, 20, 0 };
		gbl_panelResumen.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panelResumen.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelResumen.setLayout(gbl_panelResumen);

		lblMaximo = new JLabel("M\u00E1ximo");
		GridBagConstraints gbc_lblMaximo = new GridBagConstraints();
		gbc_lblMaximo.gridwidth = 2;
		gbc_lblMaximo.anchor = GridBagConstraints.EAST;
		gbc_lblMaximo.insets = new Insets(0, 0, 5, 5);
		gbc_lblMaximo.gridx = 0;
		gbc_lblMaximo.gridy = 0;
		panelResumen.add(lblMaximo, gbc_lblMaximo);

		txtMaximo = new JTextField();
		txtMaximo.setHorizontalAlignment(SwingConstants.CENTER);
		txtMaximo.setEditable(false);
		txtMaximo.setColumns(10);
		GridBagConstraints gbc_txtMaximo = new GridBagConstraints();
		gbc_txtMaximo.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMaximo.anchor = GridBagConstraints.NORTH;
		gbc_txtMaximo.insets = new Insets(0, 0, 5, 5);
		gbc_txtMaximo.gridx = 2;
		gbc_txtMaximo.gridy = 0;
		panelResumen.add(txtMaximo, gbc_txtMaximo);

		lblMinimo = new JLabel("M\u00EDnimo");
		GridBagConstraints gbc_lblMinimo = new GridBagConstraints();
		gbc_lblMinimo.gridwidth = 2;
		gbc_lblMinimo.anchor = GridBagConstraints.EAST;
		gbc_lblMinimo.insets = new Insets(0, 0, 5, 5);
		gbc_lblMinimo.gridx = 3;
		gbc_lblMinimo.gridy = 0;
		panelResumen.add(lblMinimo, gbc_lblMinimo);

		txtMinimo = new JTextField();
		txtMinimo.setHorizontalAlignment(SwingConstants.CENTER);
		txtMinimo.setEditable(false);
		txtMinimo.setColumns(10);
		GridBagConstraints gbc_txtMinimo = new GridBagConstraints();
		gbc_txtMinimo.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMinimo.anchor = GridBagConstraints.NORTH;
		gbc_txtMinimo.insets = new Insets(0, 0, 5, 0);
		gbc_txtMinimo.gridx = 5;
		gbc_txtMinimo.gridy = 0;
		panelResumen.add(txtMinimo, gbc_txtMinimo);

		lblTotal = new JLabel("Total");

		lblTotal.setBounds(10, 12, 57, 20);
		GridBagConstraints gbc_lblTotal = new GridBagConstraints();
		gbc_lblTotal.anchor = GridBagConstraints.EAST;
		gbc_lblTotal.insets = new Insets(0, 0, 5, 5);
		gbc_lblTotal.gridwidth = 2;
		gbc_lblTotal.gridx = 0;
		gbc_lblTotal.gridy = 1;
		panelResumen.add(lblTotal, gbc_lblTotal);

		txtTotal = new JTextField();

		txtTotal.setEditable(false);
		txtTotal.setBounds(10, 31, 57, 20);

		txtTotal.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_txtTotal = new GridBagConstraints();
		gbc_txtTotal.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTotal.anchor = GridBagConstraints.NORTH;
		gbc_txtTotal.insets = new Insets(0, 0, 5, 5);
		gbc_txtTotal.gridx = 2;
		gbc_txtTotal.gridy = 1;
		panelResumen.add(txtTotal, gbc_txtTotal);
		txtTotal.setColumns(10);
		lblPromedio = new JLabel("Promedio");

		lblPromedio.setBounds(10, 62, 57, 20);
		GridBagConstraints gbc_lblPromedio = new GridBagConstraints();
		gbc_lblPromedio.gridwidth = 2;
		gbc_lblPromedio.anchor = GridBagConstraints.EAST;
		gbc_lblPromedio.insets = new Insets(0, 0, 5, 5);
		gbc_lblPromedio.gridx = 3;
		gbc_lblPromedio.gridy = 1;
		panelResumen.add(lblPromedio, gbc_lblPromedio);
		txtPromedio = new JTextField();

		txtPromedio.setEditable(false);
		txtPromedio.setBounds(10, 82, 57, 20);

		txtPromedio.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_txtPromedio = new GridBagConstraints();
		gbc_txtPromedio.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPromedio.anchor = GridBagConstraints.NORTH;
		gbc_txtPromedio.insets = new Insets(0, 0, 5, 0);
		gbc_txtPromedio.gridx = 5;
		gbc_txtPromedio.gridy = 1;
		panelResumen.add(txtPromedio, gbc_txtPromedio);
		txtPromedio.setColumns(10);

		lblVarianza = new JLabel("Varianza");
		GridBagConstraints gbc_lblVarianza = new GridBagConstraints();
		gbc_lblVarianza.anchor = GridBagConstraints.EAST;
		gbc_lblVarianza.insets = new Insets(0, 0, 5, 5);
		gbc_lblVarianza.gridwidth = 2;
		gbc_lblVarianza.gridx = 0;
		gbc_lblVarianza.gridy = 2;
		panelResumen.add(lblVarianza, gbc_lblVarianza);

		txtVarianza = new JTextField();
		txtVarianza.setHorizontalAlignment(SwingConstants.CENTER);
		txtVarianza.setEditable(false);
		txtVarianza.setColumns(10);
		GridBagConstraints gbc_txtVarianza = new GridBagConstraints();
		gbc_txtVarianza.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtVarianza.anchor = GridBagConstraints.NORTH;
		gbc_txtVarianza.insets = new Insets(0, 0, 5, 5);
		gbc_txtVarianza.gridx = 2;
		gbc_txtVarianza.gridy = 2;
		panelResumen.add(txtVarianza, gbc_txtVarianza);

		lblD_estandar = new JLabel("D. estandar");
		GridBagConstraints gbc_lblD_estandar = new GridBagConstraints();
		gbc_lblD_estandar.anchor = GridBagConstraints.EAST;
		gbc_lblD_estandar.insets = new Insets(0, 0, 5, 5);
		gbc_lblD_estandar.gridwidth = 2;
		gbc_lblD_estandar.gridx = 3;
		gbc_lblD_estandar.gridy = 2;
		panelResumen.add(lblD_estandar, gbc_lblD_estandar);

		txtD_estandar = new JTextField();
		txtD_estandar.setHorizontalAlignment(SwingConstants.CENTER);
		txtD_estandar.setEditable(false);
		txtD_estandar.setColumns(10);
		GridBagConstraints gbc_txtD_estandar = new GridBagConstraints();
		gbc_txtD_estandar.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtD_estandar.insets = new Insets(0, 0, 5, 0);
		gbc_txtD_estandar.anchor = GridBagConstraints.NORTH;
		gbc_txtD_estandar.gridx = 5;
		gbc_txtD_estandar.gridy = 2;
		panelResumen.add(txtD_estandar, gbc_txtD_estandar);
		lblActual = new JLabel("Actual");

		GridBagConstraints gbc_lblActual = new GridBagConstraints();
		gbc_lblActual.anchor = GridBagConstraints.EAST;
		gbc_lblActual.insets = new Insets(0, 0, 0, 5);
		gbc_lblActual.gridwidth = 2;
		gbc_lblActual.gridx = 0;
		gbc_lblActual.gridy = 3;
		panelResumen.add(lblActual, gbc_lblActual);
		txtActual = new JTextField();

		txtActual.setHorizontalAlignment(SwingConstants.CENTER);
		txtActual.setEditable(false);
		txtActual.setColumns(10);

		GridBagConstraints gbc_txtActual = new GridBagConstraints();
		gbc_txtActual.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtActual.anchor = GridBagConstraints.NORTH;
		gbc_txtActual.insets = new Insets(0, 0, 0, 5);
		gbc_txtActual.gridx = 2;
		gbc_txtActual.gridy = 3;
		panelResumen.add(txtActual, gbc_txtActual);

		panelHistograma = new JScrollPane();
		panelHistograma.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));

		histo_kpi = new GraficoHistorial();
		histo_kpi.setBorder(new TitledBorder(null, "Histograma", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panelHistograma.setViewportView(histo_kpi);

		gl_panelGeneral = new GroupLayout(this);
		gl_panelGeneral.setHorizontalGroup(gl_panelGeneral.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelGeneral.createSequentialGroup().addGap(21)
						.addGroup(gl_panelGeneral.createParallelGroup(Alignment.LEADING).addComponent(panelHistograma,
								Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE)
								.addGroup(gl_panelGeneral.createSequentialGroup()
										.addComponent(panelResumen, GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(panelIndicador, GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)))
						.addGap(23)));
		gl_panelGeneral.setVerticalGroup(gl_panelGeneral.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelGeneral.createSequentialGroup().addContainerGap()
						.addGroup(gl_panelGeneral.createParallelGroup(Alignment.LEADING)
								.addComponent(panelIndicador, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(panelResumen, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panelHistograma, GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
						.addContainerGap()));

		JLabel lblAceptacion = new JLabel("%");

		GridBagConstraints gbc_lblAceptacion = new GridBagConstraints();
		gbc_lblAceptacion.anchor = GridBagConstraints.EAST;
		gbc_lblAceptacion.insets = new Insets(0, 0, 0, 5);
		gbc_lblAceptacion.gridx = 4;
		gbc_lblAceptacion.gridy = 3;
		panelResumen.add(lblAceptacion, gbc_lblAceptacion);
		spinner_porcentaje = new JSpinner();

		spinner_porcentaje.setModel(new SpinnerNumberModel(valor_inicial, 1, 100, 1));

		GridBagConstraints gbc_spinner_porcentaje = new GridBagConstraints();
		gbc_spinner_porcentaje.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinner_porcentaje.anchor = GridBagConstraints.NORTH;
		gbc_spinner_porcentaje.gridx = 5;
		gbc_spinner_porcentaje.gridy = 3;
		panelResumen.add(spinner_porcentaje, gbc_spinner_porcentaje);

		setLayout(gl_panelGeneral);
	}

	public GraficoKPI getIndicador_kpi() {
		return indicador_kpi;
	}

	public JSpinner getSpinner_porcentaje() {
		return spinner_porcentaje;
	}

	public JTextField getTxtTotal() {
		return txtTotal;
	}

	public JTextField getTxtPromedio() {
		return txtPromedio;
	}

	public JTextField getTxtActual() {
		return txtActual;
	}

	public JTextField getTxtVarianza() {
		return txtVarianza;
	}

	public JTextField getTxtD_estandar() {
		return txtD_estandar;
	}

	public JTextField getTxtMinimo() {
		return txtMinimo;
	}

	public JTextField getTxtMaximo() {
		return txtMaximo;
	}

	public void notificarError(String mensaje) {

		JOptionPane optionPane = new JOptionPane(mensaje, JOptionPane.ERROR_MESSAGE);
		JDialog dialog = optionPane.createDialog("error");
		dialog.setAlwaysOnTop(true);
		dialog.setVisible(true);
	}
}