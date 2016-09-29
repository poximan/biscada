/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.vistas.kpi;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Arrays;

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
import javax.swing.border.TitledBorder;

import bi.controles.servicios.dimensiones.ServKpiCalidadServicio;
import bi.controles.servicios.periodos.ServPeriodoAbstract;
import bi.graficas.GraficoHistorial;
import bi.graficas.GraficoKPI;
import bi.vistas.eventos.EventoKPI;
import bi.vistas.eventos.EventoKPIConfigurable;
import comunes.vistas.PanelIniciable;
import propiedades.controles.servicios.ServPropiedades;
import javax.swing.UIManager;

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
	private JLabel lblPeriodoMasReciente;

	private ServPeriodoAbstract servPeriodoAbstract;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	/**
	 * @wbp.parser.constructor
	 */
	public VistaKpiAbstract(float datos[][], ServPeriodoAbstract servPeriodoAbstract) {

		this.servPeriodoAbstract = servPeriodoAbstract;
		iniciarComponentes();
		calculosComunes(datos);
	}

	public VistaKpiAbstract(float fila_datos[], ServPeriodoAbstract servPeriodoAbstract) {

		this(convertir(fila_datos), servPeriodoAbstract);
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	/**
	 * convierte a dos dimensiones para tratarlo los datos de la misma forma
	 * 
	 * @param filas_datos
	 * @return
	 */
	private static float[][] convertir(float[] fila_datos) {

		float[][] matriz_datos = new float[1][fila_datos.length];
		matriz_datos[0] = Arrays.copyOf(fila_datos, fila_datos.length);
		return matriz_datos;
	}

	private void calculosComunes(float datos[][]) {

		ServKpiCalidadServicio serv_kpi_calidad_servicio = new ServKpiCalidadServicio(datos);

		int maximo = serv_kpi_calidad_servicio.maximo();
		txtMaximo.setText(String.valueOf(maximo));
		txtMinimo.setText(String.valueOf(serv_kpi_calidad_servicio.minimo()));

		int total = serv_kpi_calidad_servicio.totalAlarmas();
		txtTotal.setText(String.valueOf(total));

		double promedio = serv_kpi_calidad_servicio.promedio();
		txtPromedio.setText(ServKpiCalidadServicio.formatear(promedio));

		txtVarianza.setText(ServKpiCalidadServicio.formatear(serv_kpi_calidad_servicio.varianza()));
		txtD_estandar.setText(ServKpiCalidadServicio.formatear(serv_kpi_calidad_servicio.desviacionEstandar()));

		int actual = serv_kpi_calidad_servicio.actual();
		txtActual.setText(String.valueOf(actual));

		indicador_kpi.cargarDatos(total, actual, promedio);

		histo_kpi.cargarDatos(servPeriodoAbstract.getEncabezadoFecha(), datos[0], total, promedio);		
	}

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

		panelIndicador = new JPanel();
		panelIndicador
				.setBorder(new TitledBorder(null, "Indicador", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		indicador_kpi = new GraficoKPI();
		
		panelIndicador.add(indicador_kpi);
		panelIndicador.validate();

		panelResumen = new JPanel();
		panelResumen.setBorder(new TitledBorder(null, "Mediciones", TitledBorder.CENTER, TitledBorder.TOP, null, null));

		int valor_inicial = 1;
		try {
			valor_inicial = Integer.valueOf(
					ServPropiedades.getInstancia().getProperty("Graficos.PORCENTAGE_ACEPTACION_RESPECTO_MEDIA"));
		} catch (NumberFormatException e) {
		}

		GridBagLayout gbl_panelResumen = new GridBagLayout();
		gbl_panelResumen.columnWidths = new int[] { 20, 80, 12, 56, 0 };
		gbl_panelResumen.rowHeights = new int[] { 20, 20, 0, 39, 3, 0 };
		gbl_panelResumen.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panelResumen.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelResumen.setLayout(gbl_panelResumen);

		lblTotal = new JLabel("Total");
		lblTotal.setToolTipText("total de alarmas en la consulta");

		lblTotal.setBounds(10, 12, 57, 20);
		GridBagConstraints gbc_lblTotal = new GridBagConstraints();
		gbc_lblTotal.anchor = GridBagConstraints.EAST;
		gbc_lblTotal.insets = new Insets(0, 0, 5, 5);
		gbc_lblTotal.gridx = 0;
		gbc_lblTotal.gridy = 0;
		panelResumen.add(lblTotal, gbc_lblTotal);

		txtTotal = new JTextField();

		txtTotal.setEditable(false);
		txtTotal.setBounds(10, 31, 57, 20);

		txtTotal.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_txtTotal = new GridBagConstraints();
		gbc_txtTotal.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTotal.anchor = GridBagConstraints.NORTH;
		gbc_txtTotal.insets = new Insets(0, 0, 5, 5);
		gbc_txtTotal.gridx = 1;
		gbc_txtTotal.gridy = 0;
		panelResumen.add(txtTotal, gbc_txtTotal);
		txtTotal.setColumns(10);
		lblPromedio = new JLabel("m");
		lblPromedio.setToolTipText("promedio");

		lblPromedio.setBounds(10, 62, 57, 20);
		GridBagConstraints gbc_lblPromedio = new GridBagConstraints();
		gbc_lblPromedio.anchor = GridBagConstraints.EAST;
		gbc_lblPromedio.insets = new Insets(0, 0, 5, 5);
		gbc_lblPromedio.gridx = 2;
		gbc_lblPromedio.gridy = 0;
		panelResumen.add(lblPromedio, gbc_lblPromedio);
		txtPromedio = new JTextField();

		txtPromedio.setEditable(false);
		txtPromedio.setBounds(10, 82, 57, 20);

		txtPromedio.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_txtPromedio = new GridBagConstraints();
		gbc_txtPromedio.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPromedio.anchor = GridBagConstraints.NORTH;
		gbc_txtPromedio.insets = new Insets(0, 0, 5, 0);
		gbc_txtPromedio.gridx = 3;
		gbc_txtPromedio.gridy = 0;
		panelResumen.add(txtPromedio, gbc_txtPromedio);
		txtPromedio.setColumns(10);

		lblVarianza = new JLabel("s^2");
		lblVarianza.setToolTipText("varianza");
		GridBagConstraints gbc_lblVarianza = new GridBagConstraints();
		gbc_lblVarianza.anchor = GridBagConstraints.EAST;
		gbc_lblVarianza.insets = new Insets(0, 0, 5, 5);
		gbc_lblVarianza.gridx = 0;
		gbc_lblVarianza.gridy = 1;
		panelResumen.add(lblVarianza, gbc_lblVarianza);

		txtVarianza = new JTextField();
		txtVarianza.setHorizontalAlignment(SwingConstants.CENTER);
		txtVarianza.setEditable(false);
		txtVarianza.setColumns(10);
		GridBagConstraints gbc_txtVarianza = new GridBagConstraints();
		gbc_txtVarianza.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtVarianza.anchor = GridBagConstraints.NORTH;
		gbc_txtVarianza.insets = new Insets(0, 0, 5, 5);
		gbc_txtVarianza.gridx = 1;
		gbc_txtVarianza.gridy = 1;
		panelResumen.add(txtVarianza, gbc_txtVarianza);

		lblD_estandar = new JLabel("s");
		lblD_estandar.setToolTipText("desviacion estandar");
		GridBagConstraints gbc_lblD_estandar = new GridBagConstraints();
		gbc_lblD_estandar.anchor = GridBagConstraints.EAST;
		gbc_lblD_estandar.insets = new Insets(0, 0, 5, 5);
		gbc_lblD_estandar.gridx = 2;
		gbc_lblD_estandar.gridy = 1;
		panelResumen.add(lblD_estandar, gbc_lblD_estandar);

		txtD_estandar = new JTextField();
		txtD_estandar.setHorizontalAlignment(SwingConstants.CENTER);
		txtD_estandar.setEditable(false);
		txtD_estandar.setColumns(10);
		GridBagConstraints gbc_txtD_estandar = new GridBagConstraints();
		gbc_txtD_estandar.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtD_estandar.insets = new Insets(0, 0, 5, 0);
		gbc_txtD_estandar.anchor = GridBagConstraints.NORTH;
		gbc_txtD_estandar.gridx = 3;
		gbc_txtD_estandar.gridy = 1;
		panelResumen.add(txtD_estandar, gbc_txtD_estandar);

		JLabel lblAceptacion = new JLabel("%");
		lblAceptacion.setToolTipText(
				"criterio de aceptacion en % respecto al promedio. de aqui se obtiene el delta (+- el valor del promedio)");

		GridBagConstraints gbc_lblAceptacion = new GridBagConstraints();
		gbc_lblAceptacion.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblAceptacion.insets = new Insets(0, 0, 5, 5);
		gbc_lblAceptacion.gridx = 2;
		gbc_lblAceptacion.gridy = 2;
		panelResumen.add(lblAceptacion, gbc_lblAceptacion);

		spinner_porcentaje = new JSpinner();
		spinner_porcentaje.setModel(new SpinnerNumberModel(valor_inicial, 1, 100, 1));

		GridBagConstraints gbc_spinner_porcentaje = new GridBagConstraints();
		gbc_spinner_porcentaje.insets = new Insets(0, 0, 5, 0);
		gbc_spinner_porcentaje.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinner_porcentaje.anchor = GridBagConstraints.NORTH;
		gbc_spinner_porcentaje.gridx = 3;
		gbc_spinner_porcentaje.gridy = 2;
		panelResumen.add(spinner_porcentaje, gbc_spinner_porcentaje);

		panelHistograma = new JScrollPane();
		panelHistograma.setViewportBorder(null);

		histo_kpi = new GraficoHistorial();
		histo_kpi.setBorder(new TitledBorder(null, "Histograma", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panelHistograma.setViewportView(histo_kpi);

		gl_panelGeneral = new GroupLayout(this);
		gl_panelGeneral.setHorizontalGroup(gl_panelGeneral.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelGeneral.createSequentialGroup().addGap(21)
						.addGroup(gl_panelGeneral.createParallelGroup(Alignment.LEADING)
								.addComponent(panelHistograma, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 479,
										Short.MAX_VALUE)
								.addGroup(gl_panelGeneral.createSequentialGroup()
										.addComponent(panelResumen, GroupLayout.PREFERRED_SIZE, 218,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(panelIndicador, GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)))
						.addGap(23)));
		gl_panelGeneral.setVerticalGroup(gl_panelGeneral.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelGeneral.createSequentialGroup().addContainerGap()
						.addGroup(gl_panelGeneral.createParallelGroup(Alignment.LEADING)
								.addComponent(panelIndicador, GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
								.addComponent(panelResumen, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panelHistograma, GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
						.addContainerGap()));

		lblPeriodoMasReciente = new JLabel("Periodo mas reciente");
		lblPeriodoMasReciente
				.setToolTipText("total de alarmas contadas en ultimo periodo (ultima columan de la consulta)");
		GridBagConstraints gbc_lblPeriodoMasReciente = new GridBagConstraints();
		gbc_lblPeriodoMasReciente.gridwidth = 2;
		gbc_lblPeriodoMasReciente.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblPeriodoMasReciente.insets = new Insets(0, 0, 5, 5);
		gbc_lblPeriodoMasReciente.gridx = 1;
		gbc_lblPeriodoMasReciente.gridy = 3;
		panelResumen.add(lblPeriodoMasReciente, gbc_lblPeriodoMasReciente);
		txtActual = new JTextField();

		txtActual.setHorizontalAlignment(SwingConstants.CENTER);
		txtActual.setEditable(false);
		txtActual.setColumns(10);

		GridBagConstraints gbc_txtActual = new GridBagConstraints();
		gbc_txtActual.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtActual.anchor = GridBagConstraints.NORTH;
		gbc_txtActual.insets = new Insets(0, 0, 5, 0);
		gbc_txtActual.gridx = 3;
		gbc_txtActual.gridy = 3;
		panelResumen.add(txtActual, gbc_txtActual);

		JPanel panelMinMax = new JPanel();
		panelMinMax.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Periodo - Clave",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_panelMinMax = new GridBagConstraints();
		gbc_panelMinMax.anchor = GridBagConstraints.NORTHEAST;
		gbc_panelMinMax.gridwidth = 4;
		gbc_panelMinMax.gridx = 0;
		gbc_panelMinMax.gridy = 4;
		panelResumen.add(panelMinMax, gbc_panelMinMax);
		GridBagLayout gbl_panelMinMax = new GridBagLayout();
		gbl_panelMinMax.columnWidths = new int[] { 36, 57, 0, 54, 0 };
		gbl_panelMinMax.rowHeights = new int[] { 28, 0 };
		gbl_panelMinMax.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panelMinMax.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panelMinMax.setLayout(gbl_panelMinMax);

		lblMaximo = new JLabel("M\u00E1ximo");
		lblMaximo.setToolTipText("[fila-columna] de la consulta con mayor repeticion de alarmas");
		GridBagConstraints gbc_lblMaximo = new GridBagConstraints();
		gbc_lblMaximo.anchor = GridBagConstraints.EAST;
		gbc_lblMaximo.insets = new Insets(0, 0, 0, 5);
		gbc_lblMaximo.gridx = 0;
		gbc_lblMaximo.gridy = 0;
		panelMinMax.add(lblMaximo, gbc_lblMaximo);

		txtMaximo = new JTextField();
		txtMaximo.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_txtMaximo = new GridBagConstraints();
		gbc_txtMaximo.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMaximo.insets = new Insets(0, 0, 0, 5);
		gbc_txtMaximo.gridx = 1;
		gbc_txtMaximo.gridy = 0;
		panelMinMax.add(txtMaximo, gbc_txtMaximo);
		txtMaximo.setEditable(false);
		txtMaximo.setColumns(10);

		lblMinimo = new JLabel("M\u00EDnimo");
		lblMinimo.setToolTipText("[fila-columna] de la consulta con menor repeticion de alarmas");
		GridBagConstraints gbc_lblMinimo = new GridBagConstraints();
		gbc_lblMinimo.anchor = GridBagConstraints.EAST;
		gbc_lblMinimo.insets = new Insets(0, 0, 0, 5);
		gbc_lblMinimo.gridx = 2;
		gbc_lblMinimo.gridy = 0;
		panelMinMax.add(lblMinimo, gbc_lblMinimo);

		txtMinimo = new JTextField();
		GridBagConstraints gbc_txtMinimo = new GridBagConstraints();
		gbc_txtMinimo.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMinimo.gridx = 3;
		gbc_txtMinimo.gridy = 0;
		panelMinMax.add(txtMinimo, gbc_txtMinimo);
		txtMinimo.setHorizontalAlignment(SwingConstants.CENTER);
		txtMinimo.setEditable(false);
		txtMinimo.setColumns(10);

		setLayout(gl_panelGeneral);
	}

	public GraficoKPI getIndicador_kpi() {
		return indicador_kpi;
	}

	public JSpinner getSpinner_porcentaje() {
		return spinner_porcentaje;
	}

	public void notificarError(String mensaje) {

		JOptionPane optionPane = new JOptionPane(mensaje, JOptionPane.ERROR_MESSAGE);
		JDialog dialog = optionPane.createDialog("error");
		dialog.setAlwaysOnTop(true);
		dialog.setVisible(true);
	}
}