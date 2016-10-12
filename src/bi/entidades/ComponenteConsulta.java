/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.entidades;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.RowSorter;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.apache.log4j.Logger;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.ELProperty;
import org.jdesktop.observablecollections.ObservableCollections;
import org.jdesktop.observablecollections.ObservableList;
import org.jdesktop.swingbinding.JTableBinding;
import org.jdesktop.swingbinding.JTableBinding.ColumnBinding;
import org.jdesktop.swingbinding.SwingBindings;

import com.toedter.calendar.JDateChooser;

import bi.controles.ServConsultaDinamica;
import bi.controles.ServConsultaEstatica;
import bi.vistas.eventos.EventoComponenteConsulta;
import bi.vistas.eventos.EventoManejable;
import comunes.entidades.Alarma;
import comunes.entidades.EquipoEnSitio;
import comunes.entidades.Familia;
import comunes.entidades.Sitio;
import comunes.entidades.Suceso;
import comunes.entidades.TipoDeEquipo;
import comunes.vistas.EventoConfigurable;
import comunes.vistas.PanelIniciable;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * Class Responsibility Collaborator (CRC) design:
 * 
 * ==== parte clase =========================
 * 
 * YO REPRESENTO, un componente grafico que se utiliza en la pantalla BI
 * 
 * ==== parte responsabilidad ===============
 * 
 * LO QUE HAGO, doy al usuario la posibilidad de generar una consulta a la base
 * de datos, considerando filtros por todos los campos que la componen
 * 
 * LO QUE CONOZCO, el resultado de la consulta como una lista Observable.
 * disponible para los servicios que la necesiten
 * 
 * ==== parte colaboracion ==================
 * 
 * MI COLABORADOR PRINCIPAL, es bi.controles.ServConsultaDinamica
 * 
 * COMO INTERACTUO CON MI COLABORADOR, puedo delegarle la construccion del
 * CriteriaQuery para obtener los resultados
 *
 * @author hdonato
 * 
 */
public class ComponenteConsulta extends JPanel implements PanelIniciable, EventoConfigurable, EventoManejable {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static Logger log = Logger.getLogger(ComponenteConsulta.class);

	private static final long serialVersionUID = 1L;

	private final ButtonGroup buttonGroupDesde = new ButtonGroup();
	private final ButtonGroup buttonGroupHasta = new ButtonGroup();

	private BindingGroup bindingGroup;

	private GroupLayout groupLayout;
	private GroupLayout gl_panelFechas;
	private GroupLayout gl_panelCampoSimple;

	private ObservableList<Alarma> consultas;

	private JTableBinding<Alarma, ?, JTable> jTableBinding;

	private ComponenteMenuConsulta frame_bi;

	private JPanel panelFiltros;
	private JPanel panelTabla;
	private JPanel panelCampoSimple;
	private JPanel panelFechas;

	private JTable tblConsulta;

	private JLabel lblSitio;
	private JLabel lblFamilia;
	private JLabel lblTipoEquipo;
	private JLabel lblSuceso;
	private JLabel lblHasta;
	private JLabel lblDesde;
	private JLabel lblProcesando;
	private JButton btnBuscar;

	private JScrollPane scrPaneTabla;

	private JComboBox<Sitio> cboxSitio;
	private JComboBox<Familia> cboxFamilia;
	private JComboBox<TipoDeEquipo> cboxTipoEquipo;
	private JComboBox<Suceso> cboxSuceso;

	private JRadioButton rbtnDesdeInicio;
	private JRadioButton rbtnDesdeAck;
	private JRadioButton rbtnDesdeFin;
	private JRadioButton rbtnHastaInicio;
	private JRadioButton rbtnHastaAck;
	private JRadioButton rbtnHastaFin;

	private JDateChooser choosHasta;
	private JDateChooser choosDesde;

	private JTextField txt_reg_encontrados;

	private ServConsultaDinamica serv_consulta;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public ComponenteConsulta(ComponenteMenuConsulta frame_bi) {

		this.frame_bi = frame_bi;
		recargar(new ArrayList<>(0), null); // null -> no existe consulta previa
	}

	public void buscar(ActionEvent avt) {

		/*
		 * hilo consulta -------------------
		 */
		Runnable consulta = new Runnable() {
			@Override
			public void run() {

				log.info("colectando datos desde IU");
				DatosConsulta datos_consulta = new DatosConsulta(//
						choosDesde.getCalendar(), //
						rbtnDesdeInicio, rbtnDesdeAck, rbtnDesdeFin, //
						choosHasta.getCalendar(), //
						rbtnHastaInicio, rbtnHastaAck, rbtnHastaFin, //

						(Familia) cboxFamilia.getSelectedItem(), (Sitio) cboxSitio.getSelectedItem(),
						(TipoDeEquipo) cboxTipoEquipo.getSelectedItem(), (Suceso) cboxSuceso.getSelectedItem(),

						frame_bi.getComponente_ruido_minimo().getSegundos(),
						frame_bi.getComponente_ruido_maximo().getSegundos(),

						frame_bi.getComponente_ini_incompleta().isSelected(),
						frame_bi.getComponente_ack_incompleta().isSelected(),
						frame_bi.getComponente_fin_incompleta().isSelected());

				ComponenteConsulta.this.removeAll();
				recargar(serv_consulta.buscAlarma(datos_consulta), datos_consulta);
			}
		};
		final Thread hilo_consulta = new Thread(consulta);
		hilo_consulta.start();

		/*
		 * hilo indicador
		 */
		Runnable indicador = new Runnable() {
			@Override
			public void run() {

				while (hilo_consulta.isAlive()) {
					try {
						lblProcesando.setVisible(true);
						Thread.sleep(200);

						lblProcesando.setVisible(false);
						Thread.sleep(40);
					} catch (InterruptedException excepcion) {
						lblProcesando.setVisible(false);
					}
				}
			}
		};
		Thread hilo_indicador = new Thread(indicador);
		hilo_indicador.start();
	}

	private void cargarConfiguracionConsultaPrevia(DatosConsulta datos_consulta) {

		choosDesde.setCalendar(datos_consulta.getCalendar_desde());
		rbtnDesdeInicio.setSelected(datos_consulta.getDesde_inicio());
		rbtnDesdeAck.setSelected(datos_consulta.getDesde_ack());
		rbtnDesdeFin.setSelected(datos_consulta.getDesde_fin());

		choosHasta.setCalendar(datos_consulta.getCalendar_hasta());
		rbtnHastaInicio.setSelected(datos_consulta.getHasta_inicio());
		rbtnHastaAck.setSelected(datos_consulta.getHasta_ack());
		rbtnHastaFin.setSelected(datos_consulta.getHasta_fin());

		cboxFamilia.getModel().setSelectedItem(datos_consulta.getFamilia_elegida());
		cboxSitio.getModel().setSelectedItem(datos_consulta.getSitio_elegido());
		cboxTipoEquipo.getModel().setSelectedItem(datos_consulta.getTipo_de_equipo_elegido());
		cboxSuceso.getModel().setSelectedItem(datos_consulta.getSuceso_elegido());

		frame_bi.getComponente_ruido_minimo().setSegundos(datos_consulta.getDuracion_minima());
		frame_bi.getComponente_ruido_maximo().setSegundos(datos_consulta.getDuracion_maxima());

		frame_bi.getComponente_ini_incompleta().setSelected(datos_consulta.isIncluir_ini_incompleta());
		frame_bi.getComponente_ack_incompleta().setSelected(datos_consulta.isIncluir_ack_incompleta());
		frame_bi.getComponente_fin_incompleta().setSelected(datos_consulta.isIncluir_fin_incompleta());
	}

	public void cargarTodasLasFamilias() {

		// construir lista con objetos actuales en bd que deberan estar en la
		// lista
		List<Familia> familia = ServConsultaEstatica.getListaFamilia();
		cboxFamilia.removeAllItems();

		cboxFamilia.addItem(null);

		for (Familia familia_actual : familia)
			cboxFamilia.addItem(familia_actual); // cargar la list
	}

	public void cargarTodosLosEquipos() {

		// construir lista con objetos actuales en bd que deberan estar en la
		// lista
		List<TipoDeEquipo> sucesos = ServConsultaEstatica.getListaEquipos();
		cboxTipoEquipo.removeAllItems();

		cboxTipoEquipo.addItem(null);

		for (TipoDeEquipo equipo_actual : sucesos)
			cboxTipoEquipo.addItem(equipo_actual); // cargar la lista
	}

	public void cargarTodosLosSitios() {

		// construir lista con objetos actuales en bd que deberan estar en la
		// lista
		List<Sitio> sitios = ServConsultaEstatica.getListaSitios();
		cboxSitio.removeAllItems();

		cboxSitio.addItem(null);

		for (Sitio sitio_actual : sitios)
			cboxSitio.addItem(sitio_actual); // cargar la lista
	}

	public void cargarTodosLosSucesos() {

		// construir lista con objetos actuales en bd que deberan estar en la
		// lista
		List<Suceso> sucesos = ServConsultaEstatica.getListaSucesos();
		cboxSuceso.removeAllItems();

		cboxSuceso.addItem(null);

		for (Suceso suceso_actual : sucesos)
			cboxSuceso.addItem(suceso_actual); // cargar la lista
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void configBinding() {

		// -------------------------------------
		//
		// binding master
		// -------------------------------------

		jTableBinding = SwingBindings.createJTableBinding(UpdateStrategy.READ, consultas, tblConsulta);

		// -------------------------------------
		//
		// atributos master
		// -------------------------------------

		ColumnBinding columnBinding = jTableBinding.addColumnBinding(ELProperty.create("${fecha_inicio}"));
		columnBinding.setColumnName("Inicio");
		columnBinding.setColumnClass(Calendar.class);
		columnBinding.setEditable(false);

		columnBinding = jTableBinding.addColumnBinding(ELProperty.create("${fecha_ack}"));
		columnBinding.setColumnName("Ack");
		columnBinding.setColumnClass(Calendar.class);
		columnBinding.setEditable(false);

		columnBinding = jTableBinding.addColumnBinding(ELProperty.create("${fecha_finalizacion}"));
		columnBinding.setColumnName("Fin");
		columnBinding.setColumnClass(Calendar.class);
		columnBinding.setEditable(false);

		columnBinding = jTableBinding.addColumnBinding(ELProperty.create("${ack_usuario}"));
		columnBinding.setColumnName("Usuario");
		columnBinding.setColumnClass(String.class);
		columnBinding.setEditable(false);

		columnBinding = jTableBinding.addColumnBinding(ELProperty.create("${familia.descripcion}"));
		columnBinding.setColumnName("Familia");
		columnBinding.setColumnClass(String.class);
		columnBinding.setEditable(false);

		columnBinding = jTableBinding.addColumnBinding(ELProperty.create("${sitio.descripcion}"));
		columnBinding.setColumnName("Sitio");
		columnBinding.setColumnClass(String.class);
		columnBinding.setEditable(false);

		columnBinding = jTableBinding.addColumnBinding(ELProperty.create("${equipo_en_sitio}"));
		columnBinding.setColumnName("Tipo de Equipo");
		columnBinding.setColumnClass(EquipoEnSitio.class);
		columnBinding.setEditable(false);

		columnBinding = jTableBinding.addColumnBinding(ELProperty.create("${suceso.descripcion}"));
		columnBinding.setColumnName("Suceso");
		columnBinding.setColumnClass(String.class);
		columnBinding.setEditable(false);

		/*
		 * obligatorio dejarlo asi!, cuando el origen de los datos es ilegible
		 * (cosa que sucede cuando no se ha elegido un master) no es posible
		 * definir un detalle
		 */
		jTableBinding.setSourceUnreadableValue(Collections.emptyList());

		// -------------------------------------
		//
		// binding listo para empaquetar
		// -------------------------------------

		jTableBinding.bind();
		bindingGroup.addBinding(jTableBinding);
		bindingGroup.bind();
	}

	@Override
	public void configEventos() {

		EventoComponenteConsulta eventos = new EventoComponenteConsulta(this);

		btnBuscar.addActionListener(eventos);
		try {
			cargarTodosLosCampos();
			txt_reg_encontrados.setText(Integer.toString(consultas.size()));
		} catch (NullPointerException e) {
			notificarError("No se pudo conectar a BD. Verificar actividad del servicio");
		}
	}

	public void notificarError(String mensaje) {

		JOptionPane optionPane = new JOptionPane(mensaje, JOptionPane.ERROR_MESSAGE);
		JDialog dialog = optionPane.createDialog("error");
		dialog.setAlwaysOnTop(true);
		dialog.setVisible(true);
	}

	public JButton getBtnBuscar() {
		return btnBuscar;
	}

	public List<Alarma> getConsultas() {
		return consultas;
	}

	@Override
	public void iniciarComponentes() {

		serv_consulta = new ServConsultaDinamica();

		panelCampoSimple = new JPanel();
		gl_panelCampoSimple = new GroupLayout(panelCampoSimple);

		btnBuscar = new JButton("Buscar");
		scrPaneTabla = new JScrollPane();
		tblConsulta = new JTable();
		panelFechas = new JPanel();
		lblSitio = new JLabel("Sitio:");
		cboxSitio = new JComboBox<Sitio>();
		lblFamilia = new JLabel("Familia:");
		cboxFamilia = new JComboBox<Familia>();
		lblTipoEquipo = new JLabel("Tipo equipo:");
		cboxTipoEquipo = new JComboBox<TipoDeEquipo>();
		lblSuceso = new JLabel("Suceso:");
		cboxSuceso = new JComboBox<Suceso>();
		rbtnDesdeInicio = new JRadioButton("inicio");
		rbtnDesdeAck = new JRadioButton("ack");
		rbtnDesdeFin = new JRadioButton("fin");
		lblHasta = new JLabel("hasta:");
		lblDesde = new JLabel("desde:");
		rbtnHastaInicio = new JRadioButton("inicio");
		rbtnHastaAck = new JRadioButton("ack");
		rbtnHastaFin = new JRadioButton("fin");
		choosHasta = new JDateChooser();
		choosDesde = new JDateChooser();

		// -------------------------------------
		//
		// seccion componentes visuales
		// -------------------------------------

		panelFiltros = new JPanel();
		panelTabla = new JPanel();
		panelTabla.setBorder(
				new TitledBorder(null, "Resultado consulta", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		groupLayout = new GroupLayout(this);
		groupLayout
				.setHorizontalGroup(
						groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup().addContainerGap()
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addComponent(panelTabla, GroupLayout.DEFAULT_SIZE, 430,
														Short.MAX_VALUE)
												.addComponent(panelFiltros, Alignment.TRAILING,
														GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE))
										.addContainerGap()));
		groupLayout
				.setVerticalGroup(
						groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup().addContainerGap()
										.addComponent(panelFiltros, GroupLayout.PREFERRED_SIZE, 183,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(panelTabla, GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
										.addContainerGap()));

		buttonGroupHasta.add(rbtnHastaInicio);
		buttonGroupHasta.add(rbtnHastaAck);
		buttonGroupHasta.add(rbtnHastaFin);

		gl_panelFechas = new GroupLayout(panelFechas);
		gl_panelFechas.setHorizontalGroup(
				gl_panelFechas.createParallelGroup(Alignment.LEADING).addGroup(gl_panelFechas.createSequentialGroup()
						.addContainerGap().addGroup(gl_panelFechas.createParallelGroup(Alignment.LEADING).addGroup(
								gl_panelFechas
										.createParallelGroup(Alignment.TRAILING, false).addGroup(
												gl_panelFechas.createSequentialGroup()
														.addComponent(lblDesde, GroupLayout.PREFERRED_SIZE, 43,
																GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.RELATED,
																GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(choosDesde, GroupLayout.PREFERRED_SIZE, 100,
																GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_panelFechas.createSequentialGroup().addComponent(rbtnDesdeInicio)
												.addPreferredGap(ComponentPlacement.RELATED).addComponent(rbtnDesdeAck)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(rbtnDesdeFin)))
								.addGroup(gl_panelFechas.createParallelGroup(Alignment.LEADING, false)
										.addGroup(gl_panelFechas.createSequentialGroup()
												.addComponent(lblHasta, GroupLayout.PREFERRED_SIZE, 42,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED).addComponent(choosHasta,
														GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE))
										.addGroup(gl_panelFechas.createSequentialGroup()
												.addComponent(rbtnHastaInicio, GroupLayout.PREFERRED_SIZE, 49,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(rbtnHastaAck, GroupLayout.PREFERRED_SIZE, 49,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(rbtnHastaFin, GroupLayout.PREFERRED_SIZE, 49,
														GroupLayout.PREFERRED_SIZE))))
						.addContainerGap(74, Short.MAX_VALUE)));
		gl_panelFechas.setVerticalGroup(gl_panelFechas.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelFechas.createSequentialGroup()
						.addGroup(gl_panelFechas.createParallelGroup(Alignment.LEADING)
								.addComponent(choosDesde, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panelFechas.createSequentialGroup().addGap(3).addComponent(lblDesde)))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_panelFechas.createParallelGroup(Alignment.BASELINE).addComponent(rbtnDesdeInicio)
								.addComponent(rbtnDesdeAck).addComponent(rbtnDesdeFin))
						.addGap(18)
						.addGroup(gl_panelFechas.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panelFechas.createSequentialGroup().addGap(3).addComponent(lblHasta))
								.addComponent(choosHasta, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_panelFechas.createParallelGroup(Alignment.LEADING).addComponent(rbtnHastaInicio)
								.addComponent(rbtnHastaAck).addComponent(rbtnHastaFin))
						.addContainerGap(19, Short.MAX_VALUE)));
		gl_panelFechas.linkSize(SwingConstants.HORIZONTAL, new Component[] { choosHasta, choosDesde });
		gl_panelFechas.linkSize(SwingConstants.HORIZONTAL, new Component[] { lblHasta, lblDesde });
		gl_panelFechas.linkSize(SwingConstants.HORIZONTAL, new Component[] { rbtnDesdeInicio, rbtnDesdeAck,
				rbtnDesdeFin, rbtnHastaInicio, rbtnHastaAck, rbtnHastaFin });

		gl_panelCampoSimple.setHorizontalGroup(gl_panelCampoSimple.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelCampoSimple.createSequentialGroup().addContainerGap()
						.addGroup(gl_panelCampoSimple
								.createParallelGroup(Alignment.LEADING).addGroup(gl_panelCampoSimple
										.createSequentialGroup()
										.addComponent(lblSitio, GroupLayout.PREFERRED_SIZE, 67,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(cboxSitio, 0, 174, Short.MAX_VALUE)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(lblFamilia, GroupLayout.PREFERRED_SIZE, 67,
												GroupLayout.PREFERRED_SIZE)
										.addGap(4).addComponent(cboxFamilia, 0, 173, Short.MAX_VALUE))
								.addGroup(gl_panelCampoSimple.createSequentialGroup()
										.addComponent(lblTipoEquipo, GroupLayout.PREFERRED_SIZE, 67,
												GroupLayout.PREFERRED_SIZE)
										.addGap(4).addComponent(cboxTipoEquipo, 0, 174, Short.MAX_VALUE)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(lblSuceso, GroupLayout.PREFERRED_SIZE, 67,
												GroupLayout.PREFERRED_SIZE)
										.addGap(4).addComponent(cboxSuceso, 0, 173, Short.MAX_VALUE)))
						.addGap(14)));
		gl_panelCampoSimple.setVerticalGroup(gl_panelCampoSimple.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelCampoSimple.createSequentialGroup().addContainerGap().addGroup(gl_panelCampoSimple
						.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelCampoSimple.createSequentialGroup().addGap(3).addComponent(lblFamilia))
						.addComponent(cboxFamilia, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panelCampoSimple.createParallelGroup(Alignment.BASELINE).addComponent(lblSitio)
								.addComponent(cboxSitio, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)))
						.addGap(18)
						.addGroup(gl_panelCampoSimple.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panelCampoSimple.createSequentialGroup().addGap(3).addComponent(
										lblTipoEquipo))
								.addComponent(cboxTipoEquipo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panelCampoSimple.createSequentialGroup().addGap(3).addComponent(lblSuceso))
								.addComponent(cboxSuceso, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(25, Short.MAX_VALUE)));

		panelFiltros.setBorder(
				new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Primer nivel evaluacion - Consulta",
						TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

		txt_reg_encontrados = new JTextField();
		txt_reg_encontrados.setEditable(false);
		txt_reg_encontrados.setColumns(10);

		JLabel lbl_reg_encontrados = new JLabel("Registros encontrados:");

		GroupLayout gl_panelTabla = new GroupLayout(panelTabla);
		gl_panelTabla.setHorizontalGroup(gl_panelTabla.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelTabla.createSequentialGroup().addContainerGap()
						.addGroup(gl_panelTabla.createParallelGroup(Alignment.TRAILING)
								.addComponent(scrPaneTabla, GroupLayout.DEFAULT_SIZE, 774, Short.MAX_VALUE)
								.addGroup(gl_panelTabla.createSequentialGroup().addComponent(lbl_reg_encontrados)
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(txt_reg_encontrados,
												GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)))
						.addContainerGap()));
		gl_panelTabla
				.setVerticalGroup(gl_panelTabla.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelTabla.createSequentialGroup().addGap(5)
								.addComponent(scrPaneTabla, GroupLayout.DEFAULT_SIZE, 194,
										Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_panelTabla.createParallelGroup(Alignment.BASELINE)
										.addComponent(txt_reg_encontrados, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(lbl_reg_encontrados))
								.addGap(6)));
		panelTabla.setLayout(gl_panelTabla);

		scrPaneTabla.setViewportView(tblConsulta);

		panelFechas.setBorder(new TitledBorder(null, "Fecha", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		panelCampoSimple.setBorder(
				new TitledBorder(null, "Campos simples", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		lblProcesando = new JLabel("procesando...");
		lblProcesando.setVisible(false);
		lblProcesando.setForeground(Color.BLUE);

		GroupLayout gl_panelFiltros = new GroupLayout(panelFiltros);
		gl_panelFiltros.setHorizontalGroup(gl_panelFiltros.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelFiltros.createSequentialGroup().addContainerGap()
						.addComponent(panelFechas, GroupLayout.PREFERRED_SIZE, 181, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_panelFiltros.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_panelFiltros.createSequentialGroup().addComponent(lblProcesando)
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnBuscar,
												GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE))
								.addComponent(panelCampoSimple, GroupLayout.DEFAULT_SIZE, 828, Short.MAX_VALUE))
						.addContainerGap()));
		gl_panelFiltros.setVerticalGroup(gl_panelFiltros.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelFiltros.createSequentialGroup().addContainerGap().addGroup(gl_panelFiltros
						.createParallelGroup(Alignment.BASELINE)
						.addGroup(gl_panelFiltros.createSequentialGroup()
								.addComponent(panelCampoSimple, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(gl_panelFiltros.createParallelGroup(Alignment.BASELINE)
										.addComponent(btnBuscar).addComponent(lblProcesando)))
						.addGroup(gl_panelFiltros.createSequentialGroup().addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(panelFechas, GroupLayout.PREFERRED_SIZE, 134,
										GroupLayout.PREFERRED_SIZE)))
						.addContainerGap()));

		lblSitio.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFamilia.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTipoEquipo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSuceso.setHorizontalAlignment(SwingConstants.RIGHT);
		panelCampoSimple.setLayout(gl_panelCampoSimple);

		buttonGroupDesde.add(rbtnDesdeInicio);
		buttonGroupDesde.add(rbtnDesdeAck);
		buttonGroupDesde.add(rbtnDesdeFin);

		lblHasta.setHorizontalAlignment(SwingConstants.RIGHT);

		lblDesde.setHorizontalAlignment(SwingConstants.RIGHT);
		panelFechas.setLayout(gl_panelFechas);
		panelFiltros.setLayout(gl_panelFiltros);

		setLayout(groupLayout);

		bindingGroup = new BindingGroup();
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	private void ordenarTabla() {

		RowSorter<TableModel> ordenador_filas = new TableRowSorter<TableModel>(tblConsulta.getModel());
		tblConsulta.setRowSorter(ordenador_filas);
	}

	private void recargar(List<Alarma> consultas, DatosConsulta datos_consulta) {

		this.consultas = ObservableCollections.observableList(consultas);

		iniciarComponentes();
		configBinding();

		// -------------------------------------
		//
		// editar visualizacion calendar
		// -------------------------------------

		TableCellRenderer tableCellRenderer = new DefaultTableCellRenderer() {

			private static final long serialVersionUID = 1L;

			SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss ms");

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {

				if (value instanceof Calendar) {
					value = f.format(((Calendar) value).getTime());
				}
				return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			}
		};

		tblConsulta.getColumnModel().getColumn(0).setCellRenderer(tableCellRenderer);
		tblConsulta.getColumnModel().getColumn(1).setCellRenderer(tableCellRenderer);
		tblConsulta.getColumnModel().getColumn(2).setCellRenderer(tableCellRenderer);

		configEventos();
		ordenarTabla();

		if (datos_consulta != null)
			cargarConfiguracionConsultaPrevia(datos_consulta);
	}

	@Override
	public void resolver() {

	}

	public void cargarTodosLosCampos() {

		cargarTodasLasFamilias();
		cargarTodosLosSitios();
		cargarTodosLosSucesos();
		cargarTodosLosEquipos();
	}
}