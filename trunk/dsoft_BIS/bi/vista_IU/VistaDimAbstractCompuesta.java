/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package vista_IU;

import graficas.GraficoBarras;
import graficas.GraficoLineas;
import graficas.GraficoTorta;

import java.awt.Dimension;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableModel;

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

public abstract class VistaDimAbstractCompuesta extends JPanel implements PanelIniciable, DimensionCalculable {

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
	private ServDimUnidadTiempoAbstract serv_unidad_tiempo;
	private ServMedAbstract serv_medicion;
	private ServIntervaloFechas serv_intervalo;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public VistaDimAbstractCompuesta(ServDimAbstract serv_dim_vista_seleccionada, List<Alarma> consulta_interes,
			List<Alarma> consulta_comparador) {

		this.serv_dim_vista_seleccionada = serv_dim_vista_seleccionada;

		if (serv_dim_vista_seleccionada instanceof ServDimSitio)
			this.serv_dim_sitio = (ServDimSitio) serv_dim_vista_seleccionada;

		serv_intervalo = new ServIntervaloFechas(new IntervaloFechas());

		this.consulta_interes = consulta_interes;
		this.consulta_comparador = consulta_comparador;

		iniciarComponentes();
	}

	/**
	 * @param consultas
	 * @wbp.parser.constructor
	 */
	public VistaDimAbstractCompuesta(ServDimAbstract serv_dim_vista_seleccionada, ServDimSitio serv_dim_sitio,
			List<Alarma> consulta_interes, List<Alarma> consulta_comparador) {

		this(serv_dim_vista_seleccionada, consulta_interes, consulta_comparador);
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
		gl_pl_tabla
				.setHorizontalGroup(gl_pl_tabla.createParallelGroup(Alignment.LEADING).addGroup(
						Alignment.TRAILING,
						gl_pl_tabla.createSequentialGroup().addContainerGap().addComponent(splitPane_tablas)
								.addContainerGap()));
		gl_pl_tabla.setVerticalGroup(gl_pl_tabla.createParallelGroup(Alignment.LEADING).addGroup(
				gl_pl_tabla.createSequentialGroup().addContainerGap()
						.addComponent(splitPane_tablas, GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
						.addContainerGap()));
		scrPl_interes = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		splitPane_tablas.setLeftComponent(scrPl_interes);

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
		Dimension d = tbl_filas_interes.getPreferredScrollableViewportSize();
		// define el tamaño preferido de la tabla
		d.width = tbl_filas_interes.getPreferredSize().width + 90;
		tbl_filas_interes.setPreferredScrollableViewportSize(d);
		tbl_filas_interes.setIntercellSpacing(new Dimension(0, 0));

		// recuperar el tamaño preferido en caso que la tabla este contenida en
		// un scroll
		d = tbl_medicion_interes.getPreferredScrollableViewportSize();
		tbl_medicion_interes.setPreferredScrollableViewportSize(d);
		tbl_medicion_interes.setIntercellSpacing(new Dimension(0, 0));

		JScrollPane scrPl_comparador = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		splitPane_tablas.setRightComponent(scrPl_comparador);

		tbl_medicion_comparador = new JTable((TableModel) null);
		tbl_medicion_comparador.setPreferredScrollableViewportSize(new Dimension(450, 400));
		tbl_medicion_comparador.setIntercellSpacing(new Dimension(0, 0));
		tbl_medicion_comparador.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrPl_comparador.setViewportView(tbl_medicion_comparador);

		tbl_filas_comparador = new JTable((TableModel) null);
		tbl_filas_comparador.setPreferredScrollableViewportSize(new Dimension(450, 400));
		tbl_filas_comparador.setIntercellSpacing(new Dimension(0, 0));
		tbl_filas_comparador.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		scrPl_comparador.setRowHeaderView(tbl_filas_comparador);
		gl_contentPane = new GroupLayout(this);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addGroup(
				Alignment.LEADING,
				gl_contentPane.createSequentialGroup().addContainerGap()
						.addComponent(splitPane_graf_tablas, GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
						.addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(
				gl_contentPane.createSequentialGroup().addContainerGap()
						.addComponent(splitPane_graf_tablas, GroupLayout.DEFAULT_SIZE, 613, Short.MAX_VALUE)
						.addContainerGap()));

		splitPane_graf_tablas.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_graf_tablas.setRightComponent(pl_tabla);

		splitPane_graf_tablas.setOneTouchExpandable(true);
		splitPane_graf_tablas.setDividerLocation(300);

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

	@Override
	public void ejecutarDimension() {

		armarTablaInteres();
		armarTablaComparador();

		armarSolapasGraficas();
	}

	private void armarTablaInteres() {

		serv_dim_vista_seleccionada.realizarHash(consulta_interes);

		datos_tabla_interes = serv_dim_vista_seleccionada.completarTabla(serv_intervalo, serv_medicion,
				serv_unidad_tiempo, true);

		encabezado_tabla_interes = serv_unidad_tiempo.getEncabezado();

		tbl_medicion_interes.setModel(new TableModelMedicionTemporal(datos_tabla_interes, encabezado_tabla_interes));
		tbl_filas_interes.setModel(new TableModelEntradaFila(serv_dim_vista_seleccionada.getGrupos()));
	}

	private void armarTablaComparador() {

		serv_dim_vista_seleccionada.realizarHash(consulta_comparador);

		datos_tabla_comparador = serv_dim_vista_seleccionada.completarTabla(serv_intervalo, serv_medicion,
				serv_unidad_tiempo, true);

		encabezado_tabla_comparador = serv_unidad_tiempo.getEncabezado();

		tbl_medicion_comparador.setModel(new TableModelMedicionTemporal(datos_tabla_comparador,
				encabezado_tabla_comparador));
		tbl_filas_comparador.setModel(new TableModelEntradaFila(serv_dim_vista_seleccionada.getGrupos()));
	}

	@Override
	public void armarSolapasGraficas() {

		GraficoBarras primer_grafico = new GraficoBarras(datos_tabla_interes, encabezado_tabla_interes,
				tbl_filas_interes);
		GraficoLineas tercer_grafico = new GraficoLineas(datos_tabla_interes, encabezado_tabla_interes,
				tbl_filas_interes);
		GraficoTorta cuarto_grafico = new GraficoTorta(datos_tabla_interes, tbl_filas_interes);

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
		return tbl_medicion_interes;
	}

	public JTable getTbl_titulo_filas() {
		return tbl_filas_interes;
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