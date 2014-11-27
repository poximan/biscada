/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package vistas;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import modelo.ArchivoDBF;
import modelo.ComponenteSeleccionarDireccion;
import modelo.JListDisponible;
import modelo.JListProcesado;

import org.apache.log4j.Logger;

import control_dbf.ProcesarMultipleArchivo;
import control_etl.TransaccionArchivo;
import control_etl.TransaccionBULK;
import control_etl.Transaccionable;
import control_general.ObjetosBorrables;
import control_general.ServPropiedades;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class VistaETL extends JPanel implements PanelIniciable, EventoConfigurable, ObjetosBorrables {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static Logger log = Logger.getLogger(VistaETL.class);

	private static final long serialVersionUID = 1L;

	private ProcesarMultipleArchivo procesador_archivos;

	private JPanel pl_botones;
	private JScrollPane scrollPane_procesados;

	private JLabel lbl_totDisponibles;
	private JLabel lbl_totProcesados;
	private JLabel lblDireccionFuente;
	private JLabel lbl_selDisponibles;
	private JLabel lbl_selProcesados;

	private ListModelOrdenada model_disponibles;
	private ListModelOrdenada model_procesados;

	private JList<ArchivoDBF> list_disponibles;
	private JList<ArchivoDBF> list_procesados;

	private JButton btn_analisis_datos;
	private JButton btn_restablecer;
	private JButton btn_procesar;
	private JButton btn_extraer;

	private JTextField txt_totDisponibles;
	private JTextField txt_totProcesados;
	private JTextField txtDireccionFuente;
	private JTextField txt_selDisponibles;
	private JTextField txt_selProcesados;

	private ComponenteSeleccionarDireccion btn_cambiar_direccion;
	private JCheckBox chckbxInsercionLote;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	/**
	 * construye la vista para gestionar los archivos .dbf de una direccion dada. luego esta direccion podrá ser
	 * cambiada
	 * 
	 * @param direccion_lectura
	 */
	public VistaETL() {

		log.trace("set direccion por defecto para origen de datos");

		log.trace("inicia componentes");
		iniciarComponentes();

		log.trace("carga eventos");
		configEventos();

		log.trace("llenar listas");
		actionRestablecer();
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	public void iniciarComponentes() {

		setBorder(BorderFactory.createEtchedBorder());
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 120, 120, 0, 120, 120 };
		gridBagLayout.rowHeights = new int[] { 40, 30, 38, 42, 70, 70, 0, 20 };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 0.0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 1.0, 0.0, 1.0, 1.0 };
		setLayout(gridBagLayout);

		lblDireccionFuente = new JLabel("Direccion fuente:");
		lblDireccionFuente.setHorizontalAlignment(SwingConstants.RIGHT);

		GridBagConstraints gbc_lblDireccionFuente = new GridBagConstraints();
		gbc_lblDireccionFuente.anchor = GridBagConstraints.SOUTHWEST;
		gbc_lblDireccionFuente.insets = new Insets(0, 0, 5, 5);
		gbc_lblDireccionFuente.gridx = 0;
		gbc_lblDireccionFuente.gridy = 0;
		add(lblDireccionFuente, gbc_lblDireccionFuente);

		txtDireccionFuente = new JTextField();
		GridBagConstraints gbc_txtDireccionFuente = new GridBagConstraints();
		gbc_txtDireccionFuente.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDireccionFuente.gridwidth = 4;
		gbc_txtDireccionFuente.insets = new Insets(0, 0, 5, 5);
		gbc_txtDireccionFuente.gridx = 0;
		gbc_txtDireccionFuente.gridy = 1;
		add(txtDireccionFuente, gbc_txtDireccionFuente);
		txtDireccionFuente.setColumns(10);

		txtDireccionFuente.setText(ServPropiedades.getInstancia().getProperty("Datos.DIRECCION_LECTURA_DATOS"));
		btn_cambiar_direccion = new ComponenteSeleccionarDireccion(txtDireccionFuente);

		GridBagConstraints gbc_btn_cambiar_direccion = new GridBagConstraints();
		gbc_btn_cambiar_direccion.anchor = GridBagConstraints.WEST;
		gbc_btn_cambiar_direccion.insets = new Insets(0, 0, 5, 0);
		gbc_btn_cambiar_direccion.gridx = 4;
		gbc_btn_cambiar_direccion.gridy = 1;
		add(btn_cambiar_direccion, gbc_btn_cambiar_direccion);

		chckbxInsercionLote = new JCheckBox("insercion por lotes");
		GridBagConstraints gbc_chckbxInsercionLote = new GridBagConstraints();
		gbc_chckbxInsercionLote.anchor = GridBagConstraints.SOUTHWEST;
		gbc_chckbxInsercionLote.gridwidth = 2;
		gbc_chckbxInsercionLote.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxInsercionLote.gridx = 0;
		gbc_chckbxInsercionLote.gridy = 2;
		add(chckbxInsercionLote, gbc_chckbxInsercionLote);

		// -------------------------------------
		//
		// seccion fuente
		// -------------------------------------

		lbl_totDisponibles = new JLabel("Disponibles");

		lbl_totDisponibles.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_totDisponibles.setHorizontalTextPosition(SwingConstants.LEADING);

		add(lbl_totDisponibles, new GridBagConstraints(0, 3, 1, 1, 0, 0, GridBagConstraints.SOUTHEAST,
				GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));

		txt_totDisponibles = new JTextField();
		txt_totDisponibles.setEditable(false);
		GridBagConstraints gbc_txt_totDisponibles = new GridBagConstraints();
		gbc_txt_totDisponibles.fill = GridBagConstraints.HORIZONTAL;
		gbc_txt_totDisponibles.anchor = GridBagConstraints.SOUTH;
		gbc_txt_totDisponibles.insets = new Insets(0, 0, 5, 5);
		gbc_txt_totDisponibles.gridx = 1;
		gbc_txt_totDisponibles.gridy = 3;
		add(txt_totDisponibles, gbc_txt_totDisponibles);
		txt_totDisponibles.setColumns(10);

		txt_totProcesados = new JTextField();
		txt_totProcesados.setEditable(false);
		txt_totProcesados.setColumns(10);
		GridBagConstraints gbc_txt_totProcesados = new GridBagConstraints();
		gbc_txt_totProcesados.fill = GridBagConstraints.HORIZONTAL;
		gbc_txt_totProcesados.anchor = GridBagConstraints.SOUTH;
		gbc_txt_totProcesados.insets = new Insets(0, 0, 5, 0);
		gbc_txt_totProcesados.gridx = 4;
		gbc_txt_totProcesados.gridy = 3;
		add(txt_totProcesados, gbc_txt_totProcesados);

		btn_procesar = new JButton(">-->");
		GridBagConstraints gbc_btn_procesar = new GridBagConstraints();
		gbc_btn_procesar.insets = new Insets(0, 0, 5, 5);
		gbc_btn_procesar.gridx = 2;
		gbc_btn_procesar.gridy = 4;
		add(btn_procesar, gbc_btn_procesar);

		scrollPane_procesados = new JScrollPane((Component) null);
		GridBagConstraints gbc_scrollPane_procesados = new GridBagConstraints();
		gbc_scrollPane_procesados.gridheight = 2;
		gbc_scrollPane_procesados.gridwidth = 2;
		gbc_scrollPane_procesados.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane_procesados.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_procesados.gridx = 3;
		gbc_scrollPane_procesados.gridy = 4;
		add(scrollPane_procesados, gbc_scrollPane_procesados);

		// -------------------------------------
		//
		// listas y model's
		// -------------------------------------

		model_disponibles = new ListModelOrdenada();
		list_disponibles = new JListDisponible(model_disponibles);

		model_procesados = new ListModelOrdenada();
		list_procesados = new JListProcesado(model_procesados);

		scrollPane_procesados.setViewportView(list_procesados);
		lbl_totProcesados = new JLabel("Procesados");
		lbl_totProcesados.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_totProcesados.setHorizontalTextPosition(SwingConstants.LEADING);

		GridBagConstraints gbc_lbl_totProcesados = new GridBagConstraints();
		gbc_lbl_totProcesados.anchor = GridBagConstraints.SOUTHEAST;
		gbc_lbl_totProcesados.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_totProcesados.gridx = 3;
		gbc_lbl_totProcesados.gridy = 3;

		add(lbl_totProcesados, gbc_lbl_totProcesados);
		add(new JScrollPane(list_disponibles), new GridBagConstraints(0, 4, 2, 2, .5, 1, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0, 0));

		btn_extraer = new JButton("<--<");
		GridBagConstraints gbc_btn_extraer = new GridBagConstraints();
		gbc_btn_extraer.insets = new Insets(0, 0, 5, 5);
		gbc_btn_extraer.gridx = 2;
		gbc_btn_extraer.gridy = 5;
		add(btn_extraer, gbc_btn_extraer);

		lbl_selDisponibles = new JLabel("Seleccionados:");
		GridBagConstraints gbc_lbl_selDisponibles = new GridBagConstraints();
		gbc_lbl_selDisponibles.anchor = GridBagConstraints.EAST;
		gbc_lbl_selDisponibles.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_selDisponibles.gridx = 0;
		gbc_lbl_selDisponibles.gridy = 6;
		add(lbl_selDisponibles, gbc_lbl_selDisponibles);

		txt_selDisponibles = new JTextField();
		txt_selDisponibles.setEditable(false);
		GridBagConstraints gbc_txt_selDisponibles = new GridBagConstraints();
		gbc_txt_selDisponibles.insets = new Insets(0, 0, 5, 5);
		gbc_txt_selDisponibles.fill = GridBagConstraints.HORIZONTAL;
		gbc_txt_selDisponibles.gridx = 1;
		gbc_txt_selDisponibles.gridy = 6;
		add(txt_selDisponibles, gbc_txt_selDisponibles);
		txt_selDisponibles.setColumns(10);

		lbl_selProcesados = new JLabel("Seleccionados:");
		GridBagConstraints gbc_lbl_selProcesados = new GridBagConstraints();
		gbc_lbl_selProcesados.anchor = GridBagConstraints.EAST;
		gbc_lbl_selProcesados.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_selProcesados.gridx = 3;
		gbc_lbl_selProcesados.gridy = 6;
		add(lbl_selProcesados, gbc_lbl_selProcesados);

		txt_selProcesados = new JTextField();
		txt_selProcesados.setEditable(false);
		GridBagConstraints gbc_txt_selProcesados = new GridBagConstraints();
		gbc_txt_selProcesados.insets = new Insets(0, 0, 5, 0);
		gbc_txt_selProcesados.fill = GridBagConstraints.HORIZONTAL;
		gbc_txt_selProcesados.gridx = 4;
		gbc_txt_selProcesados.gridy = 6;
		add(txt_selProcesados, gbc_txt_selProcesados);
		txt_selProcesados.setColumns(10);

		pl_botones = new JPanel();
		GridBagConstraints gbc_pl_botones = new GridBagConstraints();
		gbc_pl_botones.anchor = GridBagConstraints.SOUTHEAST;
		gbc_pl_botones.gridwidth = 2;
		gbc_pl_botones.gridx = 3;
		gbc_pl_botones.gridy = 7;
		add(pl_botones, gbc_pl_botones);

		btn_restablecer = new JButton("Restablecer");
		pl_botones.add(btn_restablecer);

		btn_analisis_datos = new JButton("Analisis Datos (BI) -->");
		pl_botones.add(btn_analisis_datos);
	}

	@Override
	public void configEventos() {

		EventoETL eventos = new EventoETL(this);

		btn_restablecer.addActionListener(eventos);

		btn_procesar.addActionListener(eventos);
		btn_extraer.addActionListener(eventos);

		btn_analisis_datos.addActionListener(eventos);

		list_disponibles.addListSelectionListener(eventos);
		list_procesados.addListSelectionListener(eventos);

		txtDireccionFuente.getDocument().addDocumentListener(eventos);
	}

	/**
	 * accion ejecutada al presionar el boton restablecer y el evento de cambiar el contenido del texto.
	 * 
	 * el campo de texto asignado para la direccion {origen <-> destino} de los archivos .dbf tiene asociado un evento
	 * para que permitirá lanzar nuevamente la logica de negocio responsable de llenar las listas de la vista ETL.
	 * 
	 * pide las listas del servicio CRUD de archivos dbf y completa en los componentes graficos que corresponden
	 */
	public void actionRestablecer() {

		procesador_archivos = new ProcesarMultipleArchivo(txtDireccionFuente.getText());
		procesador_archivos.buscarNuevosArchivos();

		List<ArchivoDBF> lista_diponible = procesador_archivos.getDbf_servicio_crud().getListaDisponibles();
		List<ArchivoDBF> lista_procesado = procesador_archivos.getDbf_servicio_crud().getListaProcesados();

		borrarTodasLasListas();

		agregarDisponible(lista_diponible.toArray());
		agregarProcesado(lista_procesado.toArray());
	}

	public void actionProcesar() {

		Transaccionable metodo_insercion;

		if (chckbxInsercionLote.isSelected())
			metodo_insercion = new TransaccionBULK();
		else
			metodo_insercion = new TransaccionArchivo();

		if (!list_disponibles.isSelectionEmpty())
			procesador_archivos.insertarArchivosSeleccionados(list_disponibles.getSelectedValuesList(),
					metodo_insercion);

		actionRestablecer();
	}

	public void actionExtraer() {

		Transaccionable metodo_borrado;

		if (chckbxInsercionLote.isSelected())
			metodo_borrado = new TransaccionBULK();
		else
			metodo_borrado = new TransaccionArchivo();

		if (!list_procesados.isSelectionEmpty())
			procesador_archivos.borrarArchivosSeleccionados(list_procesados.getSelectedValuesList(), metodo_borrado);

		actionRestablecer();
	}

	private void borrarTodasLasListas() {

		model_disponibles.clear();
		list_disponibles.clearSelection();
		model_procesados.clear();
		list_procesados.clearSelection();

		txt_totDisponibles.setText("0");
		txt_totProcesados.setText("0");
	}

	/*
	 * agregado y extraccion en lista
	 */
	public void agregarDisponible(Object[] objects) {

		model_disponibles.addAll(objects);
		txt_totDisponibles.setText(String.valueOf(model_disponibles.getSize()));
	}

	public void agregarProcesado(Object[] objects) {

		model_procesados.addAll(objects);
		txt_totProcesados.setText(String.valueOf(model_procesados.getSize()));
	}

	@Override
	public void liberarObjetos() {

		list_disponibles.removeAll();
		list_procesados.removeAll();

		model_disponibles.clear();
		model_procesados.clear();

		procesador_archivos.liberarObjetos();
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	public JButton getBtn_procesar() {
		return btn_procesar;
	}

	public JButton getBtn_extraer() {
		return btn_extraer;
	}

	public JButton getBtn_analisis_datos() {
		return btn_analisis_datos;
	}

	public JButton getBtn_restablecer() {
		return btn_restablecer;
	}

	public JTextField getTxt_selDisponibles() {
		return txt_selDisponibles;
	}

	public JTextField getTxt_selProcesados() {
		return txt_selProcesados;
	}

	/* ............................................. */
	/* ............................................. */
	/* SET'S ....................................... */
	/* ............................................. */
}