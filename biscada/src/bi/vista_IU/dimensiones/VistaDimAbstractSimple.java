/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.vista_IU.dimensiones;

import java.awt.Component;
import java.util.Collections;
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
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import bi.controles.dimensiones.ServDimAbstract;
import bi.controles.dimensiones.ServDimSitio;
import bi.controles.mediciones.ServMedAbstract;
import bi.controles.mediciones.ServMedPromedio;
import bi.controles.mediciones.ServMedTotal;
import bi.controles.periodos.ServPeriodoAbstract;
import bi.controles.periodos.ServPeriodoAnio;
import bi.controles.periodos.ServPeriodoMes;
import bi.controles.periodos.ServPeriodoQuincena;
import bi.controles.periodos.ServPeriodoSemestre;
import bi.controles.periodos.ServPeriodoTrimestre;
import bi.graficas.GraficoBarras;
import bi.graficas.GraficoLineas;
import bi.graficas.GraficoTorta;
import bi.modelo.ComponenteTabla;
import bi.modelo.IntervaloFechas;
import bi.vista_IU.consultas.TableModelMedicionTemporal;
import bi.vista_evento.EventoConfigurable;
import bi.vista_evento.EventoDim;
import comunes.modelo.Alarma;
import comunes.vistas.PanelIniciable;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public abstract class VistaDimAbstractSimple extends JPanel
		implements PanelIniciable, EventoConfigurable, DimensionCalculable {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static final long serialVersionUID = 1L;

	private JPanel pl_tiempo;
	private JPanel pl_kpi;

	private GroupLayout gl_contentPane;
	private GroupLayout gl_pl_tiempo;

	private JSplitPane splitPane;
	private JTabbedPane tabPane_grafico;

	private JLabel lbl_dim_tiempo;
	private JLabel lbl_medicion;

	private List<Alarma> consulta;
	private float[][] datos_tabla;
	private String[] encabezado_tabla;

	private ServDimAbstract serv_dim_vista_seleccionada;
	private ServDimSitio serv_dim_sitio;
	private ServPeriodoAbstract serv_periodo;
	private ServMedAbstract serv_medicion;

	private JComboBox<ServMedAbstract> cbox_medicion;
	private JComboBox<ServPeriodoAbstract> cbox_dim_tiempo;

	private JButton btnCalidadServicio;
	private JButton btnEjecutar;

	private IntervaloFechas intervalo;
	private ComponenteTabla componenteTabla;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public VistaDimAbstractSimple(ServDimAbstract serv_dim_vista_seleccionada, List<Alarma> consultas) {

		this.serv_dim_vista_seleccionada = serv_dim_vista_seleccionada;

		if (serv_dim_vista_seleccionada instanceof ServDimSitio)
			this.serv_dim_sitio = (ServDimSitio) serv_dim_vista_seleccionada;

		intervalo = new IntervaloFechas();

		this.consulta = consultas;

		iniciarComponentes();
	}

	/**
	 * @param consultas
	 * @wbp.parser.constructor
	 */
	public VistaDimAbstractSimple(ServDimAbstract serv_dim_vista_seleccionada, ServDimSitio serv_dim_sitio,
			List<Alarma> consultas) {

		this(serv_dim_vista_seleccionada, consultas);
		this.serv_dim_sitio = serv_dim_sitio;
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	public void armarSolapasGraficas() {

		GraficoBarras primer_grafico = new GraficoBarras(datos_tabla, encabezado_tabla,
				componenteTabla.getTbl_titulo_filas());
		GraficoLineas tercer_grafico = new GraficoLineas(datos_tabla, encabezado_tabla,
				componenteTabla.getTbl_titulo_filas());
		GraficoTorta cuarto_grafico = new GraficoTorta(datos_tabla, componenteTabla.getTbl_titulo_filas());

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

	private void cargarTodasLasMediciones() {

		cbox_medicion.removeAllItems();
		cbox_medicion.addItem(new ServMedTotal());
		cbox_medicion.addItem(new ServMedPromedio());
	}

	private void cargarTodasLasUnidadesTiempo() {

		cbox_dim_tiempo.removeAllItems();

		cbox_dim_tiempo.addItem(new ServPeriodoAnio(intervalo));
		ServPeriodoMes serv_mes = new ServPeriodoMes(intervalo);
		cbox_dim_tiempo.addItem(serv_mes);
		cbox_dim_tiempo.addItem(new ServPeriodoQuincena(intervalo, serv_mes));
		cbox_dim_tiempo.addItem(new ServPeriodoSemestre(intervalo));
		cbox_dim_tiempo.addItem(new ServPeriodoTrimestre(intervalo));
	}

	@Override
	public void configEventos(EventoDim eventos) {

		cargarTodasLasMediciones();
		cargarTodasLasUnidadesTiempo();

		btnCalidadServicio.addActionListener(eventos);
		btnEjecutar.addActionListener(eventos);
	}

	@Override
	public void ejecutarDimension() {

		serv_medicion = getMedicion();
		serv_periodo = getDimensionUnidadTiempo();

		serv_dim_vista_seleccionada.realizarHash(consulta);

		intervalo.setPrimer_alarma(Collections.min(consulta).getFecha_inicio());
		intervalo.setUltima_alarma(Collections.max(consulta).getFecha_inicio());

		datos_tabla = serv_dim_vista_seleccionada.completarTabla(intervalo, serv_medicion, serv_periodo);

		encabezado_tabla = serv_periodo.getEncabezado();

		componenteTabla.setIntervalo(intervalo);

		componenteTabla.contruirModeloEntradaFila(serv_dim_vista_seleccionada);
		componenteTabla.contruirModeloEntradaColumnas(datos_tabla, encabezado_tabla);

		armarSolapasGraficas();
	}

	public JButton getBtnCalidadServicio() {
		return btnCalidadServicio;
	}

	public JButton getBtnEjecutar() {
		return btnEjecutar;
	}

	public ComponenteTabla getComponenteTabla() {
		return componenteTabla;
	}

	public List<Alarma> getConsulta() {
		return consulta;
	}

	private ServPeriodoAbstract getDimensionUnidadTiempo() {

		Object entidad = cbox_dim_tiempo.getSelectedItem();

		if (entidad instanceof ServPeriodoQuincena)
			return (ServPeriodoQuincena) entidad;
		if (entidad instanceof ServPeriodoMes)
			return (ServPeriodoMes) entidad;
		if (entidad instanceof ServPeriodoAnio)
			return (ServPeriodoAnio) entidad;
		if (entidad instanceof ServPeriodoTrimestre)
			return (ServPeriodoTrimestre) entidad;

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

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	public ServMedAbstract getMedicionSeleccionada() {
		return getMedicion();
	}

	public ServDimSitio getServ_dim_sitio() {
		return serv_dim_sitio;
	}

	public ServMedAbstract getServ_medicion() {
		return serv_medicion;
	}

	public ServPeriodoAbstract getServ_periodo() {
		return serv_periodo;
	}

	public ServPeriodoAbstract getUnidadTiempoSeleccionada() {
		return getDimensionUnidadTiempo();
	}

	/**
	 * atraves de la vista se entrega el contenido de toda la tabla, propiedad
	 * del modelo de la tabla, que debe ser casteado al tipo exclusivo creado
	 * para este dato, al que se le agrego un metodo adicional para devolver su
	 * contenido.
	 * 
	 * @return
	 */
	public float[][] getValoresTabla() {
		return ((TableModelMedicionTemporal) componenteTabla.getTbl_medicion().getModel()).getDatos();
	}

	/* ............................................. */
	/* ............................................. */
	/* SET'S ....................................... */
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
		cbox_dim_tiempo = new JComboBox<ServPeriodoAbstract>();

		btnEjecutar = new JButton("ejecutar");

		gl_pl_tiempo = new GroupLayout(pl_tiempo);
		gl_pl_tiempo.setHorizontalGroup(gl_pl_tiempo.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pl_tiempo.createSequentialGroup().addContainerGap().addComponent(lbl_medicion)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(cbox_medicion, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(lbl_dim_tiempo, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(cbox_dim_tiempo, GroupLayout.PREFERRED_SIZE, 0, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED, 129, Short.MAX_VALUE).addComponent(btnEjecutar)
						.addContainerGap()));
		gl_pl_tiempo.setVerticalGroup(gl_pl_tiempo.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pl_tiempo.createSequentialGroup()
						.addGroup(gl_pl_tiempo.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_pl_tiempo.createSequentialGroup().addContainerGap()
										.addGroup(gl_pl_tiempo.createParallelGroup(Alignment.BASELINE)
												.addComponent(cbox_medicion, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(lbl_medicion).addComponent(btnEjecutar)))
								.addGroup(gl_pl_tiempo.createSequentialGroup().addGap(15).addComponent(lbl_dim_tiempo))
								.addGroup(gl_pl_tiempo.createSequentialGroup().addGap(12).addComponent(cbox_dim_tiempo,
										GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		gl_pl_tiempo.linkSize(SwingConstants.HORIZONTAL, new Component[] { cbox_medicion, cbox_dim_tiempo });
		pl_kpi = new JPanel();
		btnCalidadServicio = new JButton("indicador KPI");

		pl_kpi.setBorder(
				new TitledBorder(null, "Cuarto nivel evaluacion", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		pl_kpi.add(btnCalidadServicio);
		gl_contentPane = new GroupLayout(this);
		gl_contentPane
				.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
												.addComponent(pl_tiempo, GroupLayout.DEFAULT_SIZE, 593, Short.MAX_VALUE)
												.addPreferredGap(ComponentPlacement.RELATED).addComponent(pl_kpi,
														GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE))
										.addComponent(splitPane, GroupLayout.DEFAULT_SIZE, 729, Short.MAX_VALUE))
								.addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addComponent(pl_kpi, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(pl_tiempo, GroupLayout.PREFERRED_SIZE, 58, Short.MAX_VALUE))
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addComponent(splitPane, GroupLayout.DEFAULT_SIZE, 544, Short.MAX_VALUE).addContainerGap()));
		gl_contentPane.linkSize(SwingConstants.VERTICAL, new Component[] { pl_tiempo, pl_kpi });

		pl_tiempo.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Tercer nivel evaluacion",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));

		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);

		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(300);
		tabPane_grafico = new JTabbedPane();
		splitPane.setLeftComponent(tabPane_grafico);

		tabPane_grafico.setTabPlacement(SwingConstants.BOTTOM);

		componenteTabla = new ComponenteTabla();
		splitPane.setRightComponent(componenteTabla);

		pl_tiempo.setLayout(gl_pl_tiempo);
		setLayout(gl_contentPane);
	}
}