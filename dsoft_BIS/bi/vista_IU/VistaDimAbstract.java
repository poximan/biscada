/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package vista_IU;

import graficas.GraficoBarras;
import graficas.GraficoLineas;
import graficas.GraficoTorta;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import modelo.Alarma;
import modelo.IntervaloFechas;
import vista_evento.EventoConfigurable;
import vista_evento.EventoDim;
import vistas.PanelIniciable;
import control_dimensiones.ServDimAbstract;
import control_dimensiones.ServDimSitio;
import control_dimensiones.ServDimUnidadTiempoAbstract;
import control_dimensiones.ServDimUnidadTiempoAnio;
import control_dimensiones.ServDimUnidadTiempoMes;
import control_dimensiones.ServDimUnidadTiempoQuincena;
import control_dimensiones.ServDimUnidadTiempoTrimestre;
import control_dimensiones.ServIntervaloFechas;
import control_mediciones.ServMedAbstract;
import control_mediciones.ServMedPromedio;
import control_mediciones.ServMedTotal;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public abstract class VistaDimAbstract extends JPanel implements PanelIniciable, EventoConfigurable,
		DimensionCalculable {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static final long serialVersionUID = 1L;

	private JPanel pl_tiempo;
	private JPanel pl_kpi;
	private JPanel pl_priUlt_alarma;

	private GroupLayout gl_contentPane;
	private GroupLayout gl_pl_tiempo;

	private JSplitPane splitPane;
	private JTabbedPane tabPane_grafico;
	private JScrollPane scrPl_tabla;

	private JLabel lbl_dim_tiempo;
	private JLabel lbl_medicion;
	private JLabel lblPrimera;
	private JLabel lblUltima;

	private JTable tbl_medicion;
	private JTable tbl_titulo_filas;

	private List<Alarma> consultas;
	private float[][] datos_tabla;
	private String[] encabezado_tabla;

	private ServDimAbstract serv_dim_vista_seleccionada;
	private ServDimSitio serv_dim_sitio;
	private ServDimUnidadTiempoAbstract serv_unidad_tiempo;
	private ServMedAbstract serv_medicion;
	private ServIntervaloFechas serv_intervalo;

	private JComboBox<ServMedAbstract> cbox_medicion;
	private JComboBox<ServDimUnidadTiempoAbstract> cbox_dim_tiempo;

	private JButton btnCalidadServicio;
	private JButton btnEjecutar;

	private JTextField txtPrimera;
	private JTextField txtUltima;
	private JPanel panel;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public VistaDimAbstract(ServDimAbstract serv_dim_vista_seleccionada, List<Alarma> consultas) {

		this.serv_dim_vista_seleccionada = serv_dim_vista_seleccionada;

		if (serv_dim_vista_seleccionada instanceof ServDimSitio)
			this.serv_dim_sitio = (ServDimSitio) serv_dim_vista_seleccionada;

		serv_intervalo = new ServIntervaloFechas(new IntervaloFechas());
		this.consultas = consultas;

		iniciarComponentes();
	}

	/**
	 * @param consultas
	 * @wbp.parser.constructor
	 */
	public VistaDimAbstract(ServDimAbstract serv_dim_vista_seleccionada, ServDimSitio serv_dim_sitio,
			List<Alarma> consultas) {

		this(serv_dim_vista_seleccionada, consultas);
		this.serv_dim_sitio = serv_dim_sitio;
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	public void iniciarComponentes() {

		// -------------------------------------
		//
		// seccion crear componentes visuales
		// -------------------------------------

		lbl_medicion = new JLabel("Medicion");
		lbl_dim_tiempo = new JLabel("Dimension tiempo");

		splitPane = new JSplitPane();
		splitPane.setDividerSize(8);

		pl_tiempo = new JPanel();

		cbox_medicion = new JComboBox<ServMedAbstract>();
		cbox_dim_tiempo = new JComboBox<ServDimUnidadTiempoAbstract>();

		btnEjecutar = new JButton("ejecutar");

		gl_pl_tiempo = new GroupLayout(pl_tiempo);
		gl_pl_tiempo.setHorizontalGroup(gl_pl_tiempo.createParallelGroup(Alignment.LEADING).addGroup(
				gl_pl_tiempo.createSequentialGroup().addContainerGap().addComponent(lbl_medicion)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(cbox_medicion, 0, 110, Short.MAX_VALUE).addGap(18)
						.addComponent(lbl_dim_tiempo, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(cbox_dim_tiempo, 0, 110, Short.MAX_VALUE).addGap(129).addComponent(btnEjecutar)
						.addContainerGap()));
		gl_pl_tiempo.setVerticalGroup(gl_pl_tiempo.createParallelGroup(Alignment.LEADING).addGroup(
				gl_pl_tiempo
						.createSequentialGroup()
						.addGroup(
								gl_pl_tiempo
										.createParallelGroup(Alignment.LEADING)
										.addGroup(
												gl_pl_tiempo
														.createSequentialGroup()
														.addContainerGap()
														.addGroup(
																gl_pl_tiempo
																		.createParallelGroup(Alignment.BASELINE)
																		.addComponent(cbox_medicion,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)
																		.addComponent(lbl_medicion)
																		.addComponent(btnEjecutar)))
										.addGroup(
												gl_pl_tiempo.createSequentialGroup().addGap(15)
														.addComponent(lbl_dim_tiempo))
										.addGroup(
												gl_pl_tiempo
														.createSequentialGroup()
														.addGap(12)
														.addComponent(cbox_dim_tiempo, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		pl_kpi = new JPanel();
		btnCalidadServicio = new JButton("indicador KPI");

		pl_kpi.setBorder(new TitledBorder(null, "Cuarto nivel evaluacion", TitledBorder.LEADING, TitledBorder.TOP,
				null, null));

		pl_kpi.add(btnCalidadServicio);
		gl_contentPane = new GroupLayout(this);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addGroup(
				gl_contentPane
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								gl_contentPane
										.createParallelGroup(Alignment.LEADING)
										.addGroup(
												Alignment.TRAILING,
												gl_contentPane
														.createSequentialGroup()
														.addComponent(pl_tiempo, GroupLayout.DEFAULT_SIZE, 593,
																Short.MAX_VALUE)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(pl_kpi, GroupLayout.PREFERRED_SIZE, 130,
																GroupLayout.PREFERRED_SIZE))
										.addComponent(splitPane, GroupLayout.DEFAULT_SIZE, 729, Short.MAX_VALUE))
						.addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(
				gl_contentPane
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								gl_contentPane
										.createParallelGroup(Alignment.LEADING, false)
										.addComponent(pl_kpi, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(pl_tiempo, GroupLayout.PREFERRED_SIZE, 58, Short.MAX_VALUE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(splitPane, GroupLayout.DEFAULT_SIZE, 544, Short.MAX_VALUE).addContainerGap()));
		gl_contentPane.linkSize(SwingConstants.VERTICAL, new Component[] { pl_tiempo, pl_kpi });

		pl_tiempo.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Tercer nivel evaluacion",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));

		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);

		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(360);
		tabPane_grafico = new JTabbedPane();
		splitPane.setLeftComponent(tabPane_grafico);

		tabPane_grafico.setTabPlacement(SwingConstants.BOTTOM);

		panel = new JPanel();
		splitPane.setRightComponent(panel);
		lblPrimera = new JLabel("primera:");
		lblUltima = new JLabel("ultima:");
		pl_priUlt_alarma = new JPanel();

		txtPrimera = new JTextField();
		txtUltima = new JTextField();
		pl_priUlt_alarma.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		lblPrimera.setHorizontalAlignment(SwingConstants.RIGHT);
		pl_priUlt_alarma.add(lblPrimera);

		txtPrimera.setEditable(false);
		pl_priUlt_alarma.add(txtPrimera);
		txtPrimera.setColumns(10);

		lblUltima.setHorizontalAlignment(SwingConstants.RIGHT);
		pl_priUlt_alarma.add(lblUltima);

		txtUltima.setEditable(false);
		pl_priUlt_alarma.add(txtUltima);
		txtUltima.setColumns(10);
		scrPl_tabla = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

		// -------------------------------------
		//
		// seccion tablas
		// -------------------------------------

		tbl_titulo_filas = new JTable(new TableModelEntradaFila(new Object[0]));
		tbl_medicion = new JTable(new TableModelMedicionTemporal(new float[0][0], new String[] { "" }));

		tbl_medicion.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrPl_tabla.setViewportView(tbl_medicion);

		tbl_titulo_filas.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		scrPl_tabla.setRowHeaderView(tbl_titulo_filas);

		// recuperar el tamaño preferido en caso que la tabla este contenida en
		// un scroll
		Dimension d = tbl_titulo_filas.getPreferredScrollableViewportSize();
		// define el tamaño preferido de la tabla
		d.width = tbl_titulo_filas.getPreferredSize().width + 90;
		tbl_titulo_filas.setPreferredScrollableViewportSize(d);
		tbl_titulo_filas.setIntercellSpacing(new Dimension(0, 0));

		// recuperar el tamaño preferido en caso que la tabla este contenida en
		// un scroll
		d = tbl_medicion.getPreferredScrollableViewportSize();
		tbl_medicion.setPreferredScrollableViewportSize(d);
		tbl_medicion.setIntercellSpacing(new Dimension(0, 0));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(
				gl_panel.createSequentialGroup().addContainerGap()
						.addComponent(scrPl_tabla, GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(pl_priUlt_alarma, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));
		gl_panel.setVerticalGroup(gl_panel
				.createParallelGroup(Alignment.LEADING)
				.addGroup(
						Alignment.TRAILING,
						gl_panel.createSequentialGroup().addContainerGap()
								.addComponent(scrPl_tabla, GroupLayout.DEFAULT_SIZE, 544, Short.MAX_VALUE)
								.addContainerGap())
				.addGroup(
						gl_panel.createSequentialGroup().addContainerGap()
								.addComponent(pl_priUlt_alarma, GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
								.addContainerGap()));
		panel.setLayout(gl_panel);
		pl_tiempo.setLayout(gl_pl_tiempo);
		pl_tiempo.setLayout(gl_pl_tiempo);
		setLayout(gl_contentPane);
		pl_tiempo.setLayout(gl_pl_tiempo);
		setLayout(gl_contentPane);
	}

	@Override
	public void configEventos(EventoDim eventos) {

		cargarTodasLasMediciones();
		cargarTodasLasUnidadesTiempo();

		btnCalidadServicio.addActionListener(eventos);
		btnEjecutar.addActionListener(eventos);
	}

	private void cargarTodasLasMediciones() {

		cbox_medicion.removeAllItems();
		cbox_medicion.addItem(new ServMedTotal());
		cbox_medicion.addItem(new ServMedPromedio());
	}

	private void cargarTodasLasUnidadesTiempo() {

		ServDimUnidadTiempoMes serv_mes = new ServDimUnidadTiempoMes(serv_intervalo.getIntervalo());

		cbox_dim_tiempo.removeAllItems();

		cbox_dim_tiempo.addItem(new ServDimUnidadTiempoQuincena(serv_intervalo.getIntervalo(), serv_mes));
		cbox_dim_tiempo.addItem(serv_mes);
		cbox_dim_tiempo.addItem(new ServDimUnidadTiempoTrimestre(serv_intervalo.getIntervalo()));
		cbox_dim_tiempo.addItem(new ServDimUnidadTiempoAnio(serv_intervalo.getIntervalo()));
	}

	@Override
	public void ejecutarDimension() {

		serv_medicion = getMedicion();
		serv_unidad_tiempo = getDimensionUnidadTiempo();

		serv_dim_vista_seleccionada.realizarHash(consultas);

		datos_tabla = serv_dim_vista_seleccionada.completarTabla(serv_intervalo, serv_medicion, serv_unidad_tiempo,
				true);

		encabezado_tabla = serv_unidad_tiempo.getEncabezado();

		txtPrimera.setText(serv_intervalo.getIntervalo()
				.getFechaCorta(serv_intervalo.getIntervalo().getPrimer_alarma()));
		txtUltima
				.setText(serv_intervalo.getIntervalo().getFechaCorta(serv_intervalo.getIntervalo().getUltima_alarma()));

		tbl_medicion.setModel(new TableModelMedicionTemporal(datos_tabla, encabezado_tabla));
		tbl_titulo_filas.setModel(new TableModelEntradaFila(serv_dim_vista_seleccionada.getGrupos()));

		armarSolapasGraficas();
	}

	@Override
	public void armarSolapasGraficas() {

		GraficoBarras primer_grafico = new GraficoBarras(datos_tabla, encabezado_tabla, tbl_titulo_filas);
		GraficoLineas tercer_grafico = new GraficoLineas(datos_tabla, encabezado_tabla, tbl_titulo_filas);
		GraficoTorta cuarto_grafico = new GraficoTorta(datos_tabla, tbl_titulo_filas);

		JScrollPane scroll_primer_grafico = primer_grafico.construirPanel();
		JScrollPane scroll_segundo_grafico = tercer_grafico.construirPanel();
		JScrollPane scroll_tercer_grafico = cuarto_grafico.construirPanel();

		if (tabPane_grafico.getTabCount() == 0) {
			tabPane_grafico.addTab("..barras", scroll_primer_grafico);
			tabPane_grafico.addTab("..lineas", scroll_segundo_grafico);
			tabPane_grafico.addTab("..torta", scroll_tercer_grafico);
		} else {
			tabPane_grafico.setComponentAt(0, scroll_primer_grafico);
			tabPane_grafico.setComponentAt(1, scroll_segundo_grafico);
			tabPane_grafico.setComponentAt(2, scroll_tercer_grafico);
		}

		tabPane_grafico.validate();
	}

	private ServDimUnidadTiempoAbstract getDimensionUnidadTiempo() {

		Object entidad = cbox_dim_tiempo.getSelectedItem();

		if (entidad instanceof ServDimUnidadTiempoQuincena)
			return (ServDimUnidadTiempoQuincena) entidad;
		if (entidad instanceof ServDimUnidadTiempoMes)
			return (ServDimUnidadTiempoMes) entidad;
		if (entidad instanceof ServDimUnidadTiempoAnio)
			return (ServDimUnidadTiempoAnio) entidad;
		if (entidad instanceof ServDimUnidadTiempoTrimestre)
			return (ServDimUnidadTiempoTrimestre) entidad;

		return null;
	}

	private ServMedAbstract getMedicion() {

		Object entidad = cbox_medicion.getSelectedItem();

		if (entidad instanceof ServMedTotal)
			return (ServMedTotal) entidad;
		if (entidad instanceof ServMedPromedio)
			return (ServMedPromedio) entidad;

		return null;
	}

	/**
	 * atraves de la vista se entrega el contenido de toda la tabla, propiedad del modelo de la tabla, que debe ser
	 * casteado al tipo exclusivo creado para este dato, al que se le agrego un metodo adicional para devolver su
	 * contenido.
	 * 
	 * @return
	 */
	public float[][] getValoresTabla() {
		return ((TableModelMedicionTemporal) tbl_medicion.getModel()).getDatos();
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	public ServIntervaloFechas getServ_intervalo() {
		return serv_intervalo;
	}

	public ServMedAbstract getServ_medicion() {
		return serv_medicion;
	}

	public ServDimUnidadTiempoAbstract getServ_unidad_tiempo() {
		return serv_unidad_tiempo;
	}

	public JTable getTbl_medicion() {
		return tbl_medicion;
	}

	public JTable getTbl_titulo_filas() {
		return tbl_titulo_filas;
	}

	public JButton getBtnCalidadServicio() {
		return btnCalidadServicio;
	}

	public JButton getBtnEjecutar() {
		return btnEjecutar;
	}

	public ServDimSitio getServ_dim_sitio() {
		return serv_dim_sitio;
	}

	/* ............................................. */
	/* ............................................. */
	/* SET'S ....................................... */
	/* ............................................. */

	public void setServ_intervalo(ServIntervaloFechas serv_intervalo) {
		this.serv_intervalo = serv_intervalo;
	}
}