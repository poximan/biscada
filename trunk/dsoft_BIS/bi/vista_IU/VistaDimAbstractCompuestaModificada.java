/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package vista_IU;

import graficas.GraficoComparable;

import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import modelo.Alarma;
import modelo.ComponenteTabla;
import modelo.IntervaloFechas;
import vistas.PanelIniciable;
import control_dimensiones.ServDimAbstract;
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

	private JSplitPane splitPane_tablas;
	private JSplitPane splitPane_graf_tablas;
	private JTabbedPane tabPane_grafico;

	private List<Alarma> consulta_interes;
	private List<Alarma> consulta_comparador;

	private ComponenteTabla compTblInteres;
	private ComponenteTabla compTblComparador;

	private ServDimAbstract serv_dim_vista_seleccionada;
	private ServIntervaloFechas serv_intervalo;

	private float[][] datos_tabla_interes;
	private float[][] datos_tabla_comparador;
	private String[] encabezado_tabla_interes;
	private String[] encabezado_tabla_comparador;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public VistaDimAbstractCompuestaModificada(ServDimAbstract serv_dim_vista_seleccionada,
			ServMedAbstract serv_medicion, ServDimUnidadTiempoAbstract serv_unidad_tiempo,
			List<Alarma> consulta_interes, List<Alarma> consulta_comparador) {

		this.serv_dim_vista_seleccionada = serv_dim_vista_seleccionada;

		serv_intervalo = new ServIntervaloFechas();

		this.consulta_interes = consulta_interes;
		this.consulta_comparador = consulta_comparador;

		iniciarComponentes();
		ejecutarDimension(serv_medicion, serv_unidad_tiempo);
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	public void iniciarComponentes() {

		splitPane_graf_tablas = new JSplitPane();
		pl_tabla = new JPanel();

		splitPane_tablas = new JSplitPane();
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

		compTblInteres = new ComponenteTabla();
		splitPane_tablas.setLeftComponent(compTblInteres);

		compTblComparador = new ComponenteTabla();
		splitPane_tablas.setRightComponent(compTblComparador);

		splitPane_tablas.setDividerLocation(130);

		gl_contentPane = new GroupLayout(this);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(
						gl_contentPane.createSequentialGroup().addContainerGap()
								.addComponent(splitPane_graf_tablas, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
								.addGap(10)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(
				gl_contentPane.createSequentialGroup().addContainerGap()
						.addComponent(splitPane_graf_tablas, GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
						.addContainerGap()));

		splitPane_graf_tablas.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_graf_tablas.setRightComponent(pl_tabla);

		splitPane_graf_tablas.setOneTouchExpandable(true);
		splitPane_graf_tablas.setDividerLocation(280);
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

		IntervaloFechas intervalo = new IntervaloFechas();

		datos_tabla_interes = serv_dim_vista_seleccionada.completarTabla(serv_intervalo, intervalo, serv_medicion,
				serv_unidad_tiempo, true);

		encabezado_tabla_interes = serv_unidad_tiempo.getEncabezado();

		compTblInteres.setIntervalo(intervalo);
		compTblInteres.contruirModeloEntradaFila(serv_dim_vista_seleccionada);
		compTblInteres.contruirModeloEntradaColumnas(datos_tabla_interes, encabezado_tabla_interes);
	}

	private void armarTablaComparador(ServMedAbstract serv_medicion, ServDimUnidadTiempoAbstract serv_unidad_tiempo) {

		serv_dim_vista_seleccionada.realizarHash(consulta_comparador);

		IntervaloFechas intervalo = new IntervaloFechas();

		datos_tabla_comparador = serv_dim_vista_seleccionada.completarTabla(serv_intervalo, intervalo, serv_medicion,
				serv_unidad_tiempo, true);

		encabezado_tabla_comparador = serv_unidad_tiempo.getEncabezado();

		compTblComparador.setIntervalo(intervalo);
		compTblComparador.contruirModeloEntradaFila(serv_dim_vista_seleccionada);
		compTblComparador.contruirModeloEntradaColumnas(datos_tabla_comparador, encabezado_tabla_comparador);
	}

	public void armarSolapasGraficas() {

		// Creo la gr�fica y envio los datos
		GraficoComparable graficoDeComparacion = new GraficoComparable(datos_tabla_interes, encabezado_tabla_interes,
				compTblInteres.getTbl_titulo_filas());

		graficoDeComparacion.datosParaComparar(datos_tabla_comparador, encabezado_tabla_comparador,
				compTblComparador.getTbl_titulo_filas());

		JPanel scroll_grafico = GraficoComparable.createDemoPanel();

		if (tabPane_grafico.getTabCount() == 0)
			tabPane_grafico.addTab("..lineas", scroll_grafico);
		else
			tabPane_grafico.setComponentAt(1, scroll_grafico);

		tabPane_grafico.validate();
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	/* ............................................. */
	/* ............................................. */
	/* SET'S ....................................... */
	/* ............................................. */
}