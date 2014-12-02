/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package vista_IU;

import graficas.GraficoComparable;

import java.awt.Dimension;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import modelo.Alarma;
import modelo.IntervaloFechas;
import vistas.PanelIniciable;
import control_dimensiones.ServDimAbstract;
import control_dimensiones.ServDimSitio;
import control_dimensiones.ServDimUnidadTiempoAbstract;
import control_dimensiones.ServIntervaloFechas;
import control_mediciones.ServMedAbstract;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public abstract class VistaDimAbstractCompuestaModificada extends JPanel implements PanelIniciable {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static final long serialVersionUID = 1L;
	private JPanel pl_tabla;

	private GroupLayout gl_contentPane;
	private GroupLayout gl_pl_tabla;

	private JSplitPane splitPane_graf_tablas;
	private JTabbedPane tabPane_grafico;
	private JScrollPane scrPl_interes;

	private JTable tbl_medicion_interes;
	private JTable tbl_filas_interes;
	private JTable tbl_medicion_comparador;
	private JTable tbl_filas_comparador;

	private List<Alarma> consulta_interes;
	private List<Alarma> consulta_comparador;

	private float[][] datos_tabla_interes;
	private float[][] datos_tabla_comparador;
	private String[] encabezado_tabla_interes;
	private String[] encabezado_tabla_comparador;

	private ServDimAbstract serv_dim_vista_seleccionada;
	private ServDimSitio serv_dim_sitio;
	private ServIntervaloFechas serv_intervalo;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public VistaDimAbstractCompuestaModificada(ServDimAbstract serv_dim_vista_seleccionada, ServMedAbstract serv_medicion,
			ServDimUnidadTiempoAbstract serv_unidad_tiempo, List<Alarma> consulta_interes,
			List<Alarma> consulta_comparador) {

		this.serv_dim_vista_seleccionada = serv_dim_vista_seleccionada;

		if (serv_dim_vista_seleccionada instanceof ServDimSitio)
			this.serv_dim_sitio = (ServDimSitio) serv_dim_vista_seleccionada;

		serv_intervalo = new ServIntervaloFechas();

		this.consulta_interes = consulta_interes;
		this.consulta_comparador = consulta_comparador;

		iniciarComponentes();
		ejecutarDimension(serv_medicion, serv_unidad_tiempo);
	}

	/**
	 * @param consultas
	 * @wbp.parser.constructor
	 */
	public VistaDimAbstractCompuestaModificada(ServDimAbstract serv_dim_vista_seleccionada, ServDimSitio serv_dim_sitio,
			ServMedAbstract serv_medicion, ServDimUnidadTiempoAbstract serv_unidad_tiempo,
			List<Alarma> consulta_interes, List<Alarma> consulta_comparador) {

		this(serv_dim_vista_seleccionada, serv_medicion, serv_unidad_tiempo, consulta_interes, consulta_comparador);
		this.serv_dim_sitio = serv_dim_sitio;
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	public void iniciarComponentes() {

		splitPane_graf_tablas = new JSplitPane();
		pl_tabla = new JPanel();

		JSplitPane splitPane_tablas = new JSplitPane();
		splitPane_tablas.setOrientation(JSplitPane.VERTICAL_SPLIT);
		gl_pl_tabla = new GroupLayout(pl_tabla);
		gl_pl_tabla.setHorizontalGroup(gl_pl_tabla.createParallelGroup(Alignment.TRAILING).addGroup(
				Alignment.LEADING,
				gl_pl_tabla.createSequentialGroup().addContainerGap()
						.addComponent(splitPane_tablas, GroupLayout.DEFAULT_SIZE, 881, Short.MAX_VALUE)
						.addContainerGap()));
		gl_pl_tabla.setVerticalGroup(gl_pl_tabla.createParallelGroup(Alignment.LEADING).addGroup(
				gl_pl_tabla.createSequentialGroup().addContainerGap()
						.addComponent(splitPane_tablas, GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
						.addContainerGap()));

		JPanel panelConsultaInteres = new JPanel();
		panelConsultaInteres.setBorder(new TitledBorder(null, "Consulta interes", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		splitPane_tablas.setLeftComponent(panelConsultaInteres);
		scrPl_interes = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

		// -------------------------------------
		//
		// seccion tablas
		// -------------------------------------

		tbl_filas_interes = new JTable(new TableModelEntradaFila(new Object[0]));
		tbl_medicion_interes = new JTable(new TableModelMedicionTemporal(new float[0][0], new String[] { "" }));

		tbl_medicion_interes.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrPl_interes.setViewportView(tbl_medicion_interes);

		tbl_filas_interes.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		scrPl_interes.setRowHeaderView(tbl_filas_interes);

		// recuperar el tamaño preferido en caso que la tabla este contenida en
		// un scroll
		Dimension dimension_interes = tbl_filas_interes.getPreferredScrollableViewportSize();
		// define el tamaño preferido de la tabla
		dimension_interes.width = tbl_filas_interes.getPreferredSize().width + 90;
		tbl_filas_interes.setPreferredScrollableViewportSize(dimension_interes);
		tbl_filas_interes.setIntercellSpacing(new Dimension(0, 0));

		// recuperar el tamaño preferido en caso que la tabla este contenida en
		// un scroll
		dimension_interes = tbl_medicion_interes.getPreferredScrollableViewportSize();
		tbl_medicion_interes.setPreferredScrollableViewportSize(dimension_interes);
		tbl_medicion_interes.setIntercellSpacing(new Dimension(0, 0));

		GroupLayout gl_panelConsultaInteres = new GroupLayout(panelConsultaInteres);
		gl_panelConsultaInteres.setHorizontalGroup(gl_panelConsultaInteres.createParallelGroup(Alignment.LEADING)
				.addGroup(
						gl_panelConsultaInteres.createSequentialGroup().addContainerGap()
								.addComponent(scrPl_interes, GroupLayout.DEFAULT_SIZE, 824, Short.MAX_VALUE)
								.addContainerGap()));
		gl_panelConsultaInteres.setVerticalGroup(gl_panelConsultaInteres.createParallelGroup(Alignment.LEADING)
				.addGroup(
						gl_panelConsultaInteres.createSequentialGroup().addContainerGap()
								.addComponent(scrPl_interes, GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
								.addContainerGap()));
		panelConsultaInteres.setLayout(gl_panelConsultaInteres);

		JPanel panelConsultaComparador = new JPanel();
		panelConsultaComparador.setBorder(new TitledBorder(null, "Consulta comparador", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		splitPane_tablas.setRightComponent(panelConsultaComparador);

		JScrollPane scrPl_comparador = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

		tbl_filas_comparador = new JTable(new TableModelEntradaFila(new Object[0]));
		tbl_medicion_comparador = new JTable(new TableModelMedicionTemporal(new float[0][0], new String[] { "" }));

		tbl_medicion_comparador.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrPl_comparador.setViewportView(tbl_medicion_comparador);

		tbl_filas_comparador.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		scrPl_comparador.setRowHeaderView(tbl_filas_comparador);

		// recuperar el tamaño preferido en caso que la tabla este contenida en
		// un scroll
		Dimension dimension_comparador = tbl_filas_comparador.getPreferredScrollableViewportSize();
		// define el tamaño preferido de la tabla
		dimension_comparador.width = tbl_filas_comparador.getPreferredSize().width + 90;
		tbl_filas_comparador.setPreferredScrollableViewportSize(dimension_comparador);
		tbl_filas_comparador.setIntercellSpacing(new Dimension(0, 0));

		// recuperar el tamaño preferido en caso que la tabla este contenida en
		// un scroll
		dimension_comparador = tbl_medicion_comparador.getPreferredScrollableViewportSize();
		tbl_medicion_comparador.setPreferredScrollableViewportSize(dimension_comparador);
		tbl_medicion_comparador.setIntercellSpacing(new Dimension(0, 0));

		GroupLayout gl_panelConsultaComparador = new GroupLayout(panelConsultaComparador);
		gl_panelConsultaComparador.setHorizontalGroup(gl_panelConsultaComparador.createParallelGroup(Alignment.LEADING)
				.addGroup(
						gl_panelConsultaComparador.createSequentialGroup().addContainerGap()
								.addComponent(scrPl_comparador, GroupLayout.DEFAULT_SIZE, 819, Short.MAX_VALUE)
								.addContainerGap()));
		gl_panelConsultaComparador.setVerticalGroup(gl_panelConsultaComparador.createParallelGroup(Alignment.LEADING)
				.addGroup(
						gl_panelConsultaComparador.createSequentialGroup().addContainerGap()
								.addComponent(scrPl_comparador, GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
								.addContainerGap()));
		panelConsultaComparador.setLayout(gl_panelConsultaComparador);
		splitPane_tablas.setDividerLocation(150);

		JScrollPane scrollPane = new JScrollPane();

		gl_contentPane = new GroupLayout(this);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(
				gl_contentPane
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(splitPane_graf_tablas, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE).addGap(2)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(
				gl_contentPane
						.createSequentialGroup()
						.addGroup(
								gl_contentPane
										.createParallelGroup(Alignment.LEADING)
										.addGroup(
												gl_contentPane
														.createSequentialGroup()
														.addContainerGap()
														.addComponent(splitPane_graf_tablas, GroupLayout.DEFAULT_SIZE,
																278, Short.MAX_VALUE))
										.addGroup(
												gl_contentPane
														.createSequentialGroup()
														.addGap(122)
														.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addContainerGap()));

		splitPane_graf_tablas.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_graf_tablas.setRightComponent(pl_tabla);

		splitPane_graf_tablas.setOneTouchExpandable(true);
		splitPane_graf_tablas.setDividerLocation(200);

		pl_tabla.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Tabla", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		pl_tabla.setLayout(gl_pl_tabla);

		pl_tabla.setLayout(gl_pl_tabla);
		setLayout(gl_contentPane);

		pl_tabla.setLayout(gl_pl_tabla);
		tabPane_grafico = new JTabbedPane();
		splitPane_graf_tablas.setLeftComponent(tabPane_grafico);

		tabPane_grafico.setTabPlacement(SwingConstants.BOTTOM);
		setLayout(gl_contentPane);
	}

	public void ejecutarDimension(ServMedAbstract serv_medicion, ServDimUnidadTiempoAbstract serv_unidad_tiempo) {

		armarTablaInteres(serv_medicion, serv_unidad_tiempo);
		armarTablaComparador(serv_medicion, serv_unidad_tiempo);

		armarSolapasGraficas();
	}

	private void armarTablaInteres(ServMedAbstract serv_medicion, ServDimUnidadTiempoAbstract serv_unidad_tiempo) {

		serv_dim_vista_seleccionada.realizarHash(consulta_interes);

		datos_tabla_interes = serv_dim_vista_seleccionada.completarTabla(serv_intervalo, new IntervaloFechas(),
				serv_medicion, serv_unidad_tiempo, true);

		encabezado_tabla_interes = serv_unidad_tiempo.getEncabezado();

		tbl_medicion_interes.setModel(new TableModelMedicionTemporal(datos_tabla_interes, encabezado_tabla_interes));
		tbl_filas_interes.setModel(new TableModelEntradaFila(serv_dim_vista_seleccionada.getGrupos()));
	}

	private void armarTablaComparador(ServMedAbstract serv_medicion, ServDimUnidadTiempoAbstract serv_unidad_tiempo) {

		serv_dim_vista_seleccionada.realizarHash(consulta_comparador);

		datos_tabla_comparador = serv_dim_vista_seleccionada.completarTabla(serv_intervalo, new IntervaloFechas(),
				serv_medicion, serv_unidad_tiempo, true);

		encabezado_tabla_comparador = serv_unidad_tiempo.getEncabezado();

		tbl_medicion_comparador.setModel(new TableModelMedicionTemporal(datos_tabla_comparador,
				encabezado_tabla_comparador));
		tbl_filas_comparador.setModel(new TableModelEntradaFila(serv_dim_vista_seleccionada.getGrupos()));
		System.out.println("imprimo " + tbl_filas_comparador.getRowCount());
	}

	public void armarSolapasGraficas() {

		// Creo la gráfica y envio los datos
		GraficoComparable graficoDeComparacion = new GraficoComparable(datos_tabla_interes, encabezado_tabla_interes,
				tbl_filas_interes);

		graficoDeComparacion.datosParaComparar(datos_tabla_comparador, encabezado_tabla_comparador,
				tbl_filas_comparador);

		JPanel scroll_grafico = GraficoComparable.createDemoPanel();

		if (tabPane_grafico.getTabCount() == 0) {
			tabPane_grafico.addTab("..lineas", scroll_grafico);
		} else {
			tabPane_grafico.setComponentAt(1, scroll_grafico);
		}

		tabPane_grafico.validate();
	}

	/**
	 * atraves de la vista se entrega el contenido de toda la tabla, propiedad del modelo de la tabla, que debe ser
	 * casteado al tipo exclusivo creado para este dato, al que se le agrego un metodo adicional para devolver su
	 * contenido.
	 * 
	 * @return
	 */
	public float[][] getValoresTabla() {
		return ((TableModelMedicionTemporal) tbl_medicion_interes.getModel()).getDatos();
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

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