/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package vistas;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.beans.Beans;
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
import javax.swing.JLabel;
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

import modelo.Alarma;
import modelo.EquipoEnSitio;
import modelo.Familia;
import modelo.Sitio;
import modelo.Suceso;
import modelo.TipoDeEquipo;

import org.apache.log4j.Logger;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.ELProperty;
import org.jdesktop.observablecollections.ObservableCollections;
import org.jdesktop.swingbinding.JTableBinding;
import org.jdesktop.swingbinding.JTableBinding.ColumnBinding;
import org.jdesktop.swingbinding.SwingBindings;

import com.toedter.calendar.JDateChooser;

import control_general.ObjetosBorrables;
import control_general.ServConsulta;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class VistaConsultas extends JPanel implements PanelIniciable, EventoConfigurable, ObjetosBorrables {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static Logger log = Logger.getLogger(VistaConsultas.class);

	private static final long serialVersionUID = 1L;

	private final ButtonGroup buttonGroupDesde = new ButtonGroup();
	private final ButtonGroup buttonGroupHasta = new ButtonGroup();

	private BindingGroup bindingGroup;

	private GroupLayout groupLayout;
	private GroupLayout gl_panelFechas;
	private GroupLayout gl_panelCampoSimple;

	private List<Alarma> consultas;

	private CompMenu frame_bi;

	private JPanel panelFiltros;
	private JPanel panelTabla;
	private JPanel panelDimensiones;
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

	private JButton btnTiempoDespeje;
	private JButton btnSuceso;
	private JButton btnSitio;
	private JButton btnBuscar;
	private JButton btnTemporada;

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

	private ServConsulta serv_consulta;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public VistaConsultas(CompMenu frame_bi) {

		this.frame_bi = frame_bi;
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
		organizarTablas();

		serv_consulta = new ServConsulta();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void iniciarComponentes() {

		choosHasta = new JDateChooser();
		choosDesde = new JDateChooser();

		// -------------------------------------
		//
		// seccion binding
		// -------------------------------------

		bindingGroup = new BindingGroup();
		consultas = (List<Alarma>) (Beans.isDesignTime() ? Collections.emptyList() : ObservableCollections
				.observableList(new ArrayList<Alarma>()));

		// -------------------------------------
		//
		// seccion componentes visuales
		// -------------------------------------

		panelFiltros = new JPanel();
		panelTabla = new JPanel();
		panelTabla.setBorder(new TitledBorder(null, "Resultado consulta", TitledBorder.LEADING, TitledBorder.TOP, null,
				null));
		panelDimensiones = new JPanel();
		panelDimensiones.setAlignmentX(Component.RIGHT_ALIGNMENT);

		groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(
				Alignment.LEADING,
				groupLayout
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								groupLayout
										.createParallelGroup(Alignment.LEADING)
										.addComponent(panelDimensiones, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE,
												794, Short.MAX_VALUE)
										.addComponent(panelTabla, GroupLayout.DEFAULT_SIZE, 794, Short.MAX_VALUE)
										.addComponent(panelFiltros, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 794,
												Short.MAX_VALUE)).addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
				groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(panelFiltros, GroupLayout.PREFERRED_SIZE, 183, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panelTabla, GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panelDimensiones, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));

		btnBuscar = new JButton("Buscar");
		btnTiempoDespeje = new JButton("tiempo despeje");
		btnTiempoDespeje.setHorizontalAlignment(SwingConstants.RIGHT);
		btnTiempoDespeje.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		btnTiempoDespeje.setAlignmentX(Component.RIGHT_ALIGNMENT);
		btnSuceso = new JButton("suceso");
		btnSuceso.setHorizontalAlignment(SwingConstants.RIGHT);
		btnSuceso.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		btnSuceso.setAlignmentX(Component.RIGHT_ALIGNMENT);
		btnSitio = new JButton("sitio");
		btnSitio.setHorizontalAlignment(SwingConstants.RIGHT);
		btnSitio.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		btnSitio.setAlignmentX(Component.RIGHT_ALIGNMENT);
		scrPaneTabla = new JScrollPane();
		tblConsulta = new JTable();
		panelFechas = new JPanel();
		panelCampoSimple = new JPanel();
		lblSitio = new JLabel("Sitio:");
		cboxSitio = new JComboBox<Sitio>();
		lblFamilia = new JLabel("Familia:");
		cboxFamilia = new JComboBox<Familia>();
		lblTipoEquipo = new JLabel("Tipo equipo:");
		cboxTipoEquipo = new JComboBox<TipoDeEquipo>();
		lblSuceso = new JLabel("Suceso:");
		cboxSuceso = new JComboBox<Suceso>();
		gl_panelCampoSimple = new GroupLayout(panelCampoSimple);
		rbtnDesdeInicio = new JRadioButton("inicio");
		rbtnDesdeAck = new JRadioButton("ack");
		rbtnDesdeFin = new JRadioButton("fin");
		lblHasta = new JLabel("hasta:");
		lblDesde = new JLabel("desde:");
		rbtnHastaInicio = new JRadioButton("inicio");
		rbtnHastaAck = new JRadioButton("ack");
		rbtnHastaFin = new JRadioButton("fin");

		buttonGroupHasta.add(rbtnHastaInicio);
		buttonGroupHasta.add(rbtnHastaAck);
		buttonGroupHasta.add(rbtnHastaFin);

		gl_panelFechas = new GroupLayout(panelFechas);
		gl_panelFechas.setHorizontalGroup(gl_panelFechas.createParallelGroup(Alignment.LEADING).addGroup(
				gl_panelFechas
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								gl_panelFechas
										.createParallelGroup(Alignment.LEADING)
										.addGroup(
												gl_panelFechas
														.createParallelGroup(Alignment.TRAILING, false)
														.addGroup(
																gl_panelFechas
																		.createSequentialGroup()
																		.addComponent(lblDesde,
																				GroupLayout.PREFERRED_SIZE, 43,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(ComponentPlacement.RELATED,
																				GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)
																		.addComponent(choosDesde,
																				GroupLayout.PREFERRED_SIZE, 100,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																gl_panelFechas.createSequentialGroup()
																		.addComponent(rbtnDesdeInicio)
																		.addPreferredGap(ComponentPlacement.RELATED)
																		.addComponent(rbtnDesdeAck)
																		.addPreferredGap(ComponentPlacement.RELATED)
																		.addComponent(rbtnDesdeFin)))
										.addGroup(
												gl_panelFechas
														.createParallelGroup(Alignment.LEADING, false)
														.addGroup(
																gl_panelFechas
																		.createSequentialGroup()
																		.addComponent(lblHasta,
																				GroupLayout.PREFERRED_SIZE, 42,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(ComponentPlacement.RELATED)
																		.addComponent(choosHasta,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE))
														.addGroup(
																gl_panelFechas
																		.createSequentialGroup()
																		.addComponent(rbtnHastaInicio,
																				GroupLayout.PREFERRED_SIZE, 49,
																				GroupLayout.PREFERRED_SIZE)
																		.addComponent(rbtnHastaAck,
																				GroupLayout.PREFERRED_SIZE, 49,
																				GroupLayout.PREFERRED_SIZE)
																		.addComponent(rbtnHastaFin,
																				GroupLayout.PREFERRED_SIZE, 49,
																				GroupLayout.PREFERRED_SIZE))))
						.addContainerGap(74, Short.MAX_VALUE)));
		gl_panelFechas.setVerticalGroup(gl_panelFechas.createParallelGroup(Alignment.LEADING)
				.addGroup(
						gl_panelFechas
								.createSequentialGroup()
								.addGroup(
										gl_panelFechas
												.createParallelGroup(Alignment.LEADING)
												.addComponent(choosDesde, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addGroup(
														gl_panelFechas.createSequentialGroup().addGap(3)
																.addComponent(lblDesde)))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(
										gl_panelFechas.createParallelGroup(Alignment.BASELINE)
												.addComponent(rbtnDesdeInicio).addComponent(rbtnDesdeAck)
												.addComponent(rbtnDesdeFin))
								.addGap(18)
								.addGroup(
										gl_panelFechas
												.createParallelGroup(Alignment.LEADING)
												.addGroup(
														gl_panelFechas.createSequentialGroup().addGap(3)
																.addComponent(lblHasta))
												.addComponent(choosHasta, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(
										gl_panelFechas.createParallelGroup(Alignment.LEADING)
												.addComponent(rbtnHastaInicio).addComponent(rbtnHastaAck)
												.addComponent(rbtnHastaFin)).addContainerGap(19, Short.MAX_VALUE)));
		gl_panelFechas.linkSize(SwingConstants.HORIZONTAL, new Component[] { choosHasta, choosDesde });
		gl_panelFechas.linkSize(SwingConstants.HORIZONTAL, new Component[] { lblHasta, lblDesde });
		gl_panelFechas.linkSize(SwingConstants.HORIZONTAL, new Component[] { rbtnDesdeInicio, rbtnDesdeAck,
				rbtnDesdeFin, rbtnHastaInicio, rbtnHastaAck, rbtnHastaFin });

		gl_panelCampoSimple.setHorizontalGroup(gl_panelCampoSimple.createParallelGroup(Alignment.LEADING)
				.addGroup(
						gl_panelCampoSimple
								.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										gl_panelCampoSimple
												.createParallelGroup(Alignment.LEADING)
												.addGroup(
														gl_panelCampoSimple
																.createSequentialGroup()
																.addComponent(lblSitio, GroupLayout.PREFERRED_SIZE, 67,
																		GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(ComponentPlacement.RELATED)
																.addComponent(cboxSitio, 0, 174, Short.MAX_VALUE)
																.addPreferredGap(ComponentPlacement.UNRELATED)
																.addComponent(lblFamilia, GroupLayout.PREFERRED_SIZE,
																		67, GroupLayout.PREFERRED_SIZE).addGap(4)
																.addComponent(cboxFamilia, 0, 173, Short.MAX_VALUE))
												.addGroup(
														gl_panelCampoSimple
																.createSequentialGroup()
																.addComponent(lblTipoEquipo,
																		GroupLayout.PREFERRED_SIZE, 67,
																		GroupLayout.PREFERRED_SIZE)
																.addGap(4)
																.addComponent(cboxTipoEquipo, 0, 174, Short.MAX_VALUE)
																.addPreferredGap(ComponentPlacement.UNRELATED)
																.addComponent(lblSuceso, GroupLayout.PREFERRED_SIZE,
																		67, GroupLayout.PREFERRED_SIZE).addGap(4)
																.addComponent(cboxSuceso, 0, 173, Short.MAX_VALUE)))
								.addGap(14)));
		gl_panelCampoSimple.setVerticalGroup(gl_panelCampoSimple.createParallelGroup(Alignment.LEADING).addGroup(
				gl_panelCampoSimple
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								gl_panelCampoSimple
										.createParallelGroup(Alignment.LEADING)
										.addGroup(
												gl_panelCampoSimple.createSequentialGroup().addGap(3)
														.addComponent(lblFamilia))
										.addComponent(cboxFamilia, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addGroup(
												gl_panelCampoSimple
														.createParallelGroup(Alignment.BASELINE)
														.addComponent(lblSitio)
														.addComponent(cboxSitio, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGap(18)
						.addGroup(
								gl_panelCampoSimple
										.createParallelGroup(Alignment.LEADING)
										.addGroup(
												gl_panelCampoSimple.createSequentialGroup().addGap(3)
														.addComponent(lblTipoEquipo))
										.addComponent(cboxTipoEquipo, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addGroup(
												gl_panelCampoSimple.createSequentialGroup().addGap(3)
														.addComponent(lblSuceso))
										.addComponent(cboxSuceso, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)).addContainerGap(25, Short.MAX_VALUE)));

		panelFiltros.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Primer nivel evaluacion",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelDimensiones.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
				"Segundo nivel evaluacion", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelDimensiones.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		panelDimensiones.add(btnSitio);
		panelDimensiones.add(btnSuceso);
		panelDimensiones.add(btnTiempoDespeje);

		btnTemporada = new JButton("temporada");
		btnTemporada.setHorizontalAlignment(SwingConstants.RIGHT);
		btnTemporada.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		btnTemporada.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panelDimensiones.add(btnTemporada);

		txt_reg_encontrados = new JTextField();
		txt_reg_encontrados.setEditable(false);
		txt_reg_encontrados.setColumns(10);

		JLabel lbl_reg_encontrados = new JLabel("Registros encontrados:");

		GroupLayout gl_panelTabla = new GroupLayout(panelTabla);
		gl_panelTabla.setHorizontalGroup(gl_panelTabla.createParallelGroup(Alignment.LEADING).addGroup(
				gl_panelTabla
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								gl_panelTabla
										.createParallelGroup(Alignment.TRAILING)
										.addComponent(scrPaneTabla, GroupLayout.DEFAULT_SIZE, 774, Short.MAX_VALUE)
										.addGroup(
												gl_panelTabla
														.createSequentialGroup()
														.addComponent(lbl_reg_encontrados)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(txt_reg_encontrados, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addContainerGap()));
		gl_panelTabla.setVerticalGroup(gl_panelTabla.createParallelGroup(Alignment.LEADING).addGroup(
				gl_panelTabla
						.createSequentialGroup()
						.addGap(5)
						.addComponent(scrPaneTabla, GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(
								gl_panelTabla
										.createParallelGroup(Alignment.BASELINE)
										.addComponent(txt_reg_encontrados, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(lbl_reg_encontrados)).addGap(6)));
		panelTabla.setLayout(gl_panelTabla);

		scrPaneTabla.setViewportView(tblConsulta);

		panelFechas.setBorder(new TitledBorder(null, "Fecha", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		panelCampoSimple.setBorder(new TitledBorder(null, "Campos simples", TitledBorder.LEADING, TitledBorder.TOP,
				null, null));

		lblProcesando = new JLabel("procesando...");
		lblProcesando.setVisible(false);
		lblProcesando.setForeground(Color.BLUE);

		GroupLayout gl_panelFiltros = new GroupLayout(panelFiltros);
		gl_panelFiltros.setHorizontalGroup(gl_panelFiltros.createParallelGroup(Alignment.LEADING)
				.addGroup(
						gl_panelFiltros
								.createSequentialGroup()
								.addContainerGap()
								.addComponent(panelFechas, GroupLayout.PREFERRED_SIZE, 181, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(
										gl_panelFiltros
												.createParallelGroup(Alignment.TRAILING)
												.addGroup(
														gl_panelFiltros
																.createSequentialGroup()
																.addComponent(lblProcesando)
																.addPreferredGap(ComponentPlacement.RELATED)
																.addComponent(btnBuscar, GroupLayout.PREFERRED_SIZE,
																		118, GroupLayout.PREFERRED_SIZE))
												.addComponent(panelCampoSimple, GroupLayout.DEFAULT_SIZE, 828,
														Short.MAX_VALUE)).addContainerGap()));
		gl_panelFiltros.setVerticalGroup(gl_panelFiltros.createParallelGroup(Alignment.TRAILING).addGroup(
				gl_panelFiltros
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								gl_panelFiltros
										.createParallelGroup(Alignment.BASELINE)
										.addGroup(
												gl_panelFiltros
														.createSequentialGroup()
														.addComponent(panelCampoSimple, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.RELATED,
																GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addGroup(
																gl_panelFiltros.createParallelGroup(Alignment.BASELINE)
																		.addComponent(btnBuscar)
																		.addComponent(lblProcesando)))
										.addGroup(
												gl_panelFiltros
														.createSequentialGroup()
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(panelFechas, GroupLayout.PREFERRED_SIZE, 134,
																GroupLayout.PREFERRED_SIZE))).addContainerGap()));

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
	}

	private void organizarTablas() {

		RowSorter<TableModel> ordenador_filas = new TableRowSorter<TableModel>(tblConsulta.getModel());
		tblConsulta.setRowSorter(ordenador_filas);
	}

	public void buscar(ActionEvent avt) {

		/*
		 * hilo consulta -------------------
		 */
		Runnable consulta = new Runnable() {
			@Override
			public void run() {

				liberarObjetos();
				List<Alarma> nueva_lista = getListaAlarmas();
				consultas.addAll(nueva_lista);
				txt_reg_encontrados.setText(Integer.toString(consultas.size()));
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
					}
					catch (InterruptedException excepcion) {
						lblProcesando.setVisible(false);
					}
				}
			}
		};
		Thread hilo_indicador = new Thread(indicador);
		hilo_indicador.start();
	}

	public void cargarTodasLasFamilias() {

		// construir lista con objetos actuales en bd que deberan estar en la
		// lista
		List<Familia> familia = ServConsulta.getListaFamilia();
		cboxFamilia.removeAllItems();

		cboxFamilia.addItem(null);

		for (Familia familia_actual : familia)
			cboxFamilia.addItem(familia_actual); // cargar la list
	}

	public void cargarTodosLosEquipos() {

		// construir lista con objetos actuales en bd que deberan estar en la
		// lista
		List<TipoDeEquipo> sucesos = ServConsulta.getListaEquipos();
		cboxTipoEquipo.removeAllItems();

		cboxTipoEquipo.addItem(null);

		for (TipoDeEquipo equipo_actual : sucesos)
			cboxTipoEquipo.addItem(equipo_actual); // cargar la lista
	}

	public void cargarTodosLosSitios() {

		// construir lista con objetos actuales en bd que deberan estar en la
		// lista
		List<Sitio> sitios = ServConsulta.getListaSitios();
		cboxSitio.removeAllItems();

		cboxSitio.addItem(null);

		for (Sitio sitio_actual : sitios)
			cboxSitio.addItem(sitio_actual); // cargar la lista
	}

	public void cargarTodosLosSucesos() {

		// construir lista con objetos actuales en bd que deberan estar en la
		// lista
		List<Suceso> sucesos = ServConsulta.getListaSucesos();
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

		JTableBinding jTableBinding = SwingBindings.createJTableBinding(UpdateStrategy.READ, consultas, tblConsulta);

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
		 * obligatorio dejarlo asi!, cuando el origen de los datos es ilegible (cosa que sucede cuando no se ha elegido
		 * un master) no es posible definir un detalle
		 */
		jTableBinding.setSourceUnreadableValue(Collections.emptyList());

		// -------------------------------------
		//
		// habilitar boton dimension
		// -------------------------------------

		// Binding binding = Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ, tblConsultas,
		// ELProperty.create("{rowCount > 0}"), btnDimSitio, BeanProperty.create("enabled"));
		//
		// bindingGroup.addBinding(binding);

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

		EventoConsulta eventos = new EventoConsulta(this);

		btnBuscar.addActionListener(eventos);
		btnSitio.addActionListener(eventos);
		btnSuceso.addActionListener(eventos);
		btnTiempoDespeje.addActionListener(eventos);
		btnTemporada.addActionListener(eventos);

		try {
			cargarTodasLasFamilias();
			cargarTodosLosSitios();
			cargarTodosLosSucesos();
			cargarTodosLosEquipos();
		}
		catch (NullPointerException excepcion) {
			log.error("no esta habilitado el servicio MySQL");
		}
	}

	@Override
	public void liberarObjetos() {

		consultas.clear();
		System.gc();
	}

	private List<Alarma> getListaAlarmas() {

		Calendar fecha_desde = choosDesde.getCalendar();
		Calendar fecha_hasta = choosHasta.getCalendar();

		Familia familia = (Familia) cboxFamilia.getSelectedItem();
		Sitio sitio = (Sitio) cboxSitio.getSelectedItem();
		TipoDeEquipo tipo_de_equipo = (TipoDeEquipo) cboxTipoEquipo.getSelectedItem();
		Suceso suceso = (Suceso) cboxSuceso.getSelectedItem();

		Integer ruido_minimo = frame_bi.getComponente_ruido_minimo().getSegundos();
		Integer ruido_maximo = frame_bi.getComponente_ruido_maximo().getSegundos();

		boolean incluir_ini_incompleta = frame_bi.getComponente_ini_incompleta().isSelected();
		boolean incluir_ack_incompleta = frame_bi.getComponente_ack_incompleta().isSelected();
		boolean incluir_fin_incompleta = frame_bi.getComponente_fin_incompleta().isSelected();

		return serv_consulta.buscAlarma(fecha_desde, rbtnDesdeInicio, rbtnDesdeAck, rbtnDesdeFin, fecha_hasta,
				rbtnHastaInicio, rbtnHastaAck, rbtnHastaFin, familia, sitio, tipo_de_equipo, suceso, ruido_minimo,
				ruido_maximo, incluir_ini_incompleta, incluir_ack_incompleta, incluir_fin_incompleta);
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	public JButton getBtnTiempoDespeje() {
		return btnTiempoDespeje;
	}

	public JButton getBtnSuceso() {
		return btnSuceso;
	}

	public JButton getBtnSitio() {
		return btnSitio;
	}

	public JButton getBtnBuscar() {
		return btnBuscar;
	}

	public JButton getBtnTemporada() {
		return btnTemporada;
	}

	public List<Alarma> getConsultas() {
		return consultas;
	}
}