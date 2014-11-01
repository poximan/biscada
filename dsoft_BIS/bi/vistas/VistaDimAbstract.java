/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package vistas;

import graficas.GraficoBarraSegundo;
import graficas.GraficoBarrasPrimero;
import graficas.GraficoLineas;
import graficas.GraficoTorta;

import java.awt.Component;
import java.awt.Dimension;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.RowSorter;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import modelo.Alarma;
import modelo.IntervaloFechas;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.ELProperty;

import control_dimensiones.ServDimAbstract;
import control_dimensiones.ServDimSitio;
import control_dimensiones.ServDimUnidadTiempoAbstract;
import control_dimensiones.ServDimUnidadTiempoAnio;
import control_dimensiones.ServDimUnidadTiempoMes;
import control_dimensiones.ServDimUnidadTiempoQuincena;
import control_dimensiones.ServDimUnidadTiempoTrimestre;
import control_general.ServBusqueda;
import control_general.ServIntervaloFechas;
import control_mediciones.ServMedAbstract;
import control_mediciones.ServMedPromedio;
import control_mediciones.ServMedTotal;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public abstract class VistaDimAbstract extends JPanel implements PanelIniciable, EventoDimensionConfigurable,
		DimensionCalculable {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static final long serialVersionUID = 1L;

	private JPanel pl_tiempo;
	private JPanel pl_grafico;
	private JPanel pl_tabla;
	private JPanel pl_kpi;
	private JPanel pl_priUlt_alarma;
	private JPanel panel;

	private GroupLayout gl_contentPane;
	private GroupLayout gl_pl_tiempo;
	private GroupLayout gl_pl_grafico;
	private GroupLayout gl_pl_tabla;

	private JSplitPane splitPane;
	private JTabbedPane tabPane_grafico;
	private JScrollPane scrPl_tabla;

	private JLabel lbl_dim_tiempo;
	private JLabel lbl_medicion;
	private JLabel lblPrimera;
	private JLabel lblUltima;
	private JLabel lblSeleccioneUnSitio;

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

	private JCheckBox chckbxAlarmaIncompleta;
	private JCheckBox chckbxColumnasNulas;

	private JTextField txtPrimera;
	private JTextField txtUltima;

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
		lblPrimera = new JLabel("primera:");
		lblUltima = new JLabel("ultima:");

		splitPane = new JSplitPane();
		tabPane_grafico = new JTabbedPane();
		scrPl_tabla = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

		pl_tiempo = new JPanel();
		pl_grafico = new JPanel();
		pl_tabla = new JPanel();
		pl_kpi = new JPanel();
		pl_priUlt_alarma = new JPanel();

		cbox_medicion = new JComboBox<ServMedAbstract>();
		cbox_dim_tiempo = new JComboBox<ServDimUnidadTiempoAbstract>();

		chckbxAlarmaIncompleta = new JCheckBox("alarmas incompletas");
		chckbxAlarmaIncompleta
				.setToolTipText("se incluiran para el procesamiento las alarmas cuyos campos de fecha inicio/fin sean nulos");

		btnEjecutar = new JButton("ejecutar");
		btnCalidadServicio = new JButton("indicador KPI");

		gl_pl_tiempo = new GroupLayout(pl_tiempo);

		panel = new JPanel();
		gl_pl_tabla = new GroupLayout(pl_tabla);
		gl_pl_tabla.setHorizontalGroup(gl_pl_tabla.createParallelGroup(Alignment.LEADING).addGroup(
				gl_pl_tabla
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								gl_pl_tabla
										.createParallelGroup(Alignment.LEADING)
										.addComponent(panel, GroupLayout.PREFERRED_SIZE, 469,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(scrPl_tabla, GroupLayout.DEFAULT_SIZE, 730, Short.MAX_VALUE))
						.addContainerGap()));
		gl_pl_tabla.setVerticalGroup(gl_pl_tabla.createParallelGroup(Alignment.LEADING).addGroup(
				gl_pl_tabla.createSequentialGroup()
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(scrPl_tabla, GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE).addContainerGap()));
		panel.setLayout(null);

		chckbxColumnasNulas = new JCheckBox("columnas nulas");
		chckbxColumnasNulas.setBounds(6, 5, 97, 23);
		panel.add(chckbxColumnasNulas);

		lblSeleccioneUnSitio = new JLabel("Seleccione un sitio para obtener Indicador de Calidad de Servicio");
		lblSeleccioneUnSitio.setBounds(150, 9, 309, 14);
		panel.add(lblSeleccioneUnSitio);
		gl_pl_grafico = new GroupLayout(pl_grafico);
		gl_contentPane = new GroupLayout(this);

		txtPrimera = new JTextField();
		txtUltima = new JTextField();

		// -------------------------------------
		//
		// seccion ordenar componentes visuales
		// -------------------------------------

		gl_pl_grafico.setHorizontalGroup(gl_pl_grafico.createParallelGroup(Alignment.TRAILING).addGroup(
				gl_pl_grafico
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(tabPane_grafico, GroupLayout.DEFAULT_SIZE, 731, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(
								gl_pl_grafico
										.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(pl_priUlt_alarma, GroupLayout.PREFERRED_SIZE, 140,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(pl_kpi, GroupLayout.PREFERRED_SIZE, 140,
												GroupLayout.PREFERRED_SIZE)).addContainerGap()));
		gl_pl_grafico.setVerticalGroup(gl_pl_grafico.createParallelGroup(Alignment.TRAILING).addGroup(
				Alignment.LEADING,
				gl_pl_grafico
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								gl_pl_grafico
										.createParallelGroup(Alignment.LEADING)
										.addGroup(
												gl_pl_grafico
														.createSequentialGroup()
														.addComponent(pl_kpi, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(pl_priUlt_alarma, GroupLayout.DEFAULT_SIZE, 74,
																Short.MAX_VALUE))
										.addComponent(tabPane_grafico, GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE))
						.addContainerGap()));
		pl_priUlt_alarma.setLayout(null);

		lblPrimera.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPrimera.setBounds(10, 14, 44, 14);
		pl_priUlt_alarma.add(lblPrimera);

		txtPrimera.setEditable(false);
		txtPrimera.setBounds(57, 11, 73, 20);
		pl_priUlt_alarma.add(txtPrimera);
		txtPrimera.setColumns(10);

		lblUltima.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUltima.setBounds(10, 35, 44, 14);
		pl_priUlt_alarma.add(lblUltima);

		txtUltima.setEditable(false);
		txtUltima.setBounds(57, 32, 73, 20);
		pl_priUlt_alarma.add(txtUltima);
		txtUltima.setColumns(10);

		pl_tiempo.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Tercer nivel evaluacion",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));

		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);

		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(
				Alignment.TRAILING,
				gl_contentPane
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								gl_contentPane
										.createParallelGroup(Alignment.TRAILING)
										.addComponent(splitPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 913,
												Short.MAX_VALUE)
										.addComponent(pl_tiempo, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 913,
												Short.MAX_VALUE)).addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(
				gl_contentPane.createSequentialGroup().addContainerGap()
						.addComponent(pl_tiempo, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(splitPane, GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE).addContainerGap()));

		pl_grafico.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Grafico",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));

		splitPane.setLeftComponent(pl_grafico);
		splitPane.setRightComponent(pl_tabla);

		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(300);

		tabPane_grafico.setTabPlacement(SwingConstants.BOTTOM);

		pl_kpi.setBorder(new TitledBorder(null, "Cuarto nivel evaluacion", TitledBorder.LEADING, TitledBorder.TOP,
				null, null));

		pl_kpi.add(btnCalidadServicio);
		pl_grafico.setLayout(gl_pl_grafico);

		pl_tabla.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Tabla", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		pl_tabla.setLayout(gl_pl_tabla);

		gl_pl_tiempo.setHorizontalGroup(gl_pl_tiempo.createParallelGroup(Alignment.LEADING).addGroup(
				gl_pl_tiempo.createSequentialGroup().addContainerGap().addComponent(lbl_medicion)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(cbox_medicion, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addComponent(lbl_dim_tiempo, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(cbox_dim_tiempo, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
						.addGap(18).addComponent(chckbxAlarmaIncompleta)
						.addPreferredGap(ComponentPlacement.RELATED, 64, Short.MAX_VALUE).addComponent(btnEjecutar)
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
														.addGroup(
																gl_pl_tiempo
																		.createParallelGroup(Alignment.BASELINE)
																		.addComponent(cbox_dim_tiempo,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)
																		.addComponent(chckbxAlarmaIncompleta))))
						.addContainerGap(12, Short.MAX_VALUE)));
		gl_pl_tiempo.linkSize(SwingConstants.HORIZONTAL, new Component[] { cbox_medicion, cbox_dim_tiempo });
		pl_tiempo.setLayout(gl_pl_tiempo);

		pl_tabla.setLayout(gl_pl_tabla);
		pl_tiempo.setLayout(gl_pl_tiempo);
		setLayout(gl_contentPane);

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

		pl_tabla.setLayout(gl_pl_tabla);
		pl_tiempo.setLayout(gl_pl_tiempo);
		setLayout(gl_contentPane);

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
	}

	private void organizarTablas() {

		TableModelMedicionTemporal modelo_tabla_medicion = new TableModelMedicionTemporal(new float[0][0],
				new String[] { "" });

		tbl_medicion = new JTable(modelo_tabla_medicion);
		RowSorter<TableModel> ordenador_filas = new TableRowSorter<TableModel>(modelo_tabla_medicion);

		tbl_medicion.setRowSorter(ordenador_filas);
	}

	@SuppressWarnings({ "rawtypes" })
	public void configBinding() {

		BindingGroup bindingGroup = new BindingGroup();

		// -------------------------------------
		//
		// habilitar boton dimension
		// -------------------------------------

		// ${selectedElement != null}
		Binding binding = Bindings.createAutoBinding(UpdateStrategy.READ, tbl_medicion,
				ELProperty.create("{rowCount > 0}"), btnCalidadServicio, BeanProperty.create("enabled"));

		bindingGroup.addBinding(binding);
	}

	@Override
	public void configEventos(EventoDim eventos) {

		cargarTodasLasMediciones();
		cargarTodasLasUnidadesTiempo();

		btnCalidadServicio.addActionListener(eventos);
		btnEjecutar.addActionListener(eventos);
		chckbxAlarmaIncompleta.addActionListener(eventos);
		tbl_titulo_filas.addMouseListener(eventos);
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
		List<Alarma> consultas_filtradas;

		if (!chckbxAlarmaIncompleta.isSelected())
			consultas_filtradas = ServBusqueda.descartarFechasIncompletas(consultas);
		else
			consultas_filtradas = consultas;

		serv_dim_vista_seleccionada.realizarHash(consultas_filtradas, chckbxAlarmaIncompleta.isSelected());

		datos_tabla = serv_dim_vista_seleccionada.completarTabla(serv_intervalo, serv_medicion, serv_unidad_tiempo,
				chckbxColumnasNulas.isSelected());

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

		GraficoBarrasPrimero primer_grafico = new GraficoBarrasPrimero(datos_tabla, encabezado_tabla, tbl_titulo_filas);
		GraficoBarraSegundo segundo_grafico = new GraficoBarraSegundo(datos_tabla, encabezado_tabla, tbl_titulo_filas);
		GraficoLineas tercer_grafico = new GraficoLineas(datos_tabla, encabezado_tabla, tbl_titulo_filas);
		GraficoTorta cuarto_grafico = new GraficoTorta(datos_tabla, tbl_titulo_filas);

		JScrollPane scroll_primer_grafico = primer_grafico.construirPanel();
		JScrollPane scroll_segundo_grafico = segundo_grafico.construirPanel();
		JScrollPane scroll_tercer_grafico = tercer_grafico.construirPanel();
		JScrollPane scroll_cuarto_grafico = cuarto_grafico.construirPanel();

		if (tabPane_grafico.getTabCount() == 0) {
			tabPane_grafico.addTab("graf barras 1", scroll_primer_grafico);
			tabPane_grafico.addTab("graf barras 2", scroll_segundo_grafico);
			tabPane_grafico.addTab("graf lineas 1", scroll_tercer_grafico);
			tabPane_grafico.addTab("graf torta 1", scroll_cuarto_grafico);
		} else {
			tabPane_grafico.setComponentAt(0, scroll_primer_grafico);
			tabPane_grafico.setComponentAt(1, scroll_segundo_grafico);
			tabPane_grafico.setComponentAt(2, scroll_tercer_grafico);
			tabPane_grafico.setComponentAt(3, scroll_cuarto_grafico);
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

	public JCheckBox getChckbxAlarmaIncompleta() {
		return chckbxAlarmaIncompleta;
	}

	/* ............................................. */
	/* ............................................. */
	/* SET'S ....................................... */
	/* ............................................. */

	public void setServ_intervalo(ServIntervaloFechas serv_intervalo) {
		this.serv_intervalo = serv_intervalo;
	}
}