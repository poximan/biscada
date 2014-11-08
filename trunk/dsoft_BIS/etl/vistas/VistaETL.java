/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package vistas;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import modelo.ArchivoDBF;

import org.apache.log4j.Logger;

import control_dbf.ProcesarMultipleArchivo;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class VistaETL extends JPanel implements PanelIniciable, EventoConfigurable {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static Logger log = Logger.getLogger(VistaETL.class);

	private static final long serialVersionUID = 1L;
	private static final String BTN_AGREGAR = ">>";
	private static final String BTN_REMOVER = "<<";

	private ProcesarMultipleArchivo procesador_archivos;

	private JLabel lbl_candidatosExtraer;
	private JLabel lbl_disponibles;
	private JLabel lbl_procesados;
	private JLabel lbl_candidatosProcesar;
	private JLabel lblDireccionFuente;

	private ListModelOrdenada model_disponibles;
	private ListModelOrdenada model_candidatos_procesar;
	private ListModelOrdenada model_candidatos_extraer;
	private ListModelOrdenada model_procesados;

	private JList<ArchivoDBF> list_disponibles;
	private JList<ArchivoDBF> list_candidatos_procesar;
	private JList<ArchivoDBF> list_candidatos_extraer;
	private JList<ArchivoDBF> list_procesados;

	private JPanel pl_botones;

	private JButton btn_agregar_candidato_procesar;
	private JButton btn_remover_candidato_procesar;
	private JButton btn_remover_candidato_extraer;
	private JButton btn_agregar_candidato_extraer;
	private JButton btn_confirmar_cambios;
	private JButton btn_analisis_datos;
	private JButton btn_restablecer;

	private JScrollPane scrollPane_candidatosProcesar;
	private JScrollPane scrollPane_procesados;

	private JTextField txt_disponibles;
	private JTextField txt_candidatos_procesar;
	private JTextField txt_candidatos_extraer;
	private JTextField txt_procesados;
	private JTextField txtDireccionFuente;

	private String direccion_lectura;
	private CompSeleccionarDireccion btnCambiar;

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
	public VistaETL(String direccion_lectura) {

		log.trace("set direccion por defecto para origen de datos");
		this.direccion_lectura = direccion_lectura;

		log.trace("inicia componentes");
		iniciarComponentes();

		log.trace("carga eventos");
		configEventos();

		log.trace("llenar listas");
		actualizarLector();
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	public void iniciarComponentes() {

		setBorder(BorderFactory.createEtchedBorder());
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 150, 0, 150, 150, 0, 150 };
		gridBagLayout.rowHeights = new int[] { 50, 36, 200, 0, 200, 0, 20 };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 0.0, 1.0, 1.0, 0.0, 1.0 };
		setLayout(gridBagLayout);

		lblDireccionFuente = new JLabel("Direccion fuente:");
		lblDireccionFuente.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblDireccionFuente = new GridBagConstraints();
		gbc_lblDireccionFuente.anchor = GridBagConstraints.EAST;
		gbc_lblDireccionFuente.insets = new Insets(0, 0, 5, 5);
		gbc_lblDireccionFuente.gridx = 0;
		gbc_lblDireccionFuente.gridy = 0;
		add(lblDireccionFuente, gbc_lblDireccionFuente);

		txtDireccionFuente = new JTextField();
		txtDireccionFuente.setEditable(false);
		GridBagConstraints gbc_txtDireccionFuente = new GridBagConstraints();
		gbc_txtDireccionFuente.gridwidth = 4;
		gbc_txtDireccionFuente.insets = new Insets(0, 0, 5, 5);
		gbc_txtDireccionFuente.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDireccionFuente.gridx = 1;
		gbc_txtDireccionFuente.gridy = 0;
		add(txtDireccionFuente, gbc_txtDireccionFuente);
		txtDireccionFuente.setColumns(10);
		txtDireccionFuente.setText(direccion_lectura);

		btnCambiar = new CompSeleccionarDireccion(txtDireccionFuente);
		GridBagConstraints gbc_btnCambiar = new GridBagConstraints();
		gbc_btnCambiar.anchor = GridBagConstraints.WEST;
		gbc_btnCambiar.insets = new Insets(0, 0, 5, 0);
		gbc_btnCambiar.gridx = 5;
		gbc_btnCambiar.gridy = 0;
		add(btnCambiar, gbc_btnCambiar);

		// -------------------------------------
		//
		// botones comando
		// -------------------------------------

		btn_agregar_candidato_procesar = new JButton(BTN_AGREGAR);
		btn_agregar_candidato_procesar.setAlignmentX(0.5f);
		add(btn_agregar_candidato_procesar, new GridBagConstraints(1, 2, 1, 1, 0, .25, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));

		scrollPane_candidatosProcesar = new JScrollPane((Component) null);
		GridBagConstraints gbc_scrollPane_candidatosProcesar = new GridBagConstraints();
		gbc_scrollPane_candidatosProcesar.gridheight = 3;
		gbc_scrollPane_candidatosProcesar.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_candidatosProcesar.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_candidatosProcesar.gridx = 2;
		gbc_scrollPane_candidatosProcesar.gridy = 2;
		add(scrollPane_candidatosProcesar, gbc_scrollPane_candidatosProcesar);

		btn_remover_candidato_extraer = new JButton(">>");
		GridBagConstraints gbc_btn_remover_candidato_extraer = new GridBagConstraints();
		gbc_btn_remover_candidato_extraer.insets = new Insets(0, 0, 5, 5);
		gbc_btn_remover_candidato_extraer.gridx = 4;
		gbc_btn_remover_candidato_extraer.gridy = 2;
		add(btn_remover_candidato_extraer, gbc_btn_remover_candidato_extraer);

		scrollPane_procesados = new JScrollPane((Component) null);
		GridBagConstraints gbc_scrollPane_procesados = new GridBagConstraints();
		gbc_scrollPane_procesados.gridheight = 3;
		gbc_scrollPane_procesados.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane_procesados.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_procesados.gridx = 5;
		gbc_scrollPane_procesados.gridy = 2;
		add(scrollPane_procesados, gbc_scrollPane_procesados);

		// -------------------------------------
		//
		// listas y model's
		// -------------------------------------

		model_disponibles = new ListModelOrdenada();
		list_disponibles = new JList<ArchivoDBF>(model_disponibles);

		model_candidatos_procesar = new ListModelOrdenada();
		list_candidatos_procesar = new JList<ArchivoDBF>(model_candidatos_procesar);
		scrollPane_candidatosProcesar.setViewportView(list_candidatos_procesar);

		model_candidatos_extraer = new ListModelOrdenada();
		list_candidatos_extraer = new JList<ArchivoDBF>(model_candidatos_extraer);

		model_procesados = new ListModelOrdenada();
		list_procesados = new JList<ArchivoDBF>(model_procesados);
		scrollPane_procesados.setViewportView(list_procesados);

		// -------------------------------------
		//
		// seccion fuente
		// -------------------------------------

		lbl_disponibles = new JLabel("Sin procesar");
		lbl_procesados = new JLabel("Procesados");
		lbl_candidatosExtraer = new JLabel("Candidatos a extraer");

		lbl_disponibles.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_disponibles.setHorizontalTextPosition(SwingConstants.LEADING);
		lbl_procesados.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_procesados.setHorizontalTextPosition(SwingConstants.LEADING);
		lbl_candidatosExtraer.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_candidatosExtraer.setHorizontalTextPosition(SwingConstants.LEADING);

		add(lbl_disponibles, new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.SOUTH,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 5), 0, 0));

		lbl_candidatosProcesar = new JLabel("Candidatos a procesar");
		lbl_candidatosProcesar.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lbl_candidatosProcesar = new GridBagConstraints();
		gbc_lbl_candidatosProcesar.anchor = GridBagConstraints.SOUTH;
		gbc_lbl_candidatosProcesar.fill = GridBagConstraints.HORIZONTAL;
		gbc_lbl_candidatosProcesar.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_candidatosProcesar.gridx = 2;
		gbc_lbl_candidatosProcesar.gridy = 1;
		add(lbl_candidatosProcesar, gbc_lbl_candidatosProcesar);

		GridBagConstraints gbc_lbl_procesados = new GridBagConstraints();
		gbc_lbl_procesados.anchor = GridBagConstraints.SOUTH;
		gbc_lbl_procesados.fill = GridBagConstraints.HORIZONTAL;
		gbc_lbl_procesados.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_procesados.gridx = 5;
		gbc_lbl_procesados.gridy = 1;

		add(lbl_procesados, gbc_lbl_procesados);
		add(new JScrollPane(list_disponibles), new GridBagConstraints(0, 2, 1, 3, .5, 1, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0, 0));

		add(lbl_candidatosExtraer, new GridBagConstraints(3, 1, 1, 1, 0, 0, GridBagConstraints.SOUTH,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 5), 0, 0));

		add(new JScrollPane(list_candidatos_extraer), new GridBagConstraints(3, 2, 1, 3, .5, 1.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0, 0));

		btn_remover_candidato_procesar = new JButton(BTN_REMOVER);
		add(btn_remover_candidato_procesar, new GridBagConstraints(1, 4, 1, 1, 0, .25, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 0, 0));

		btn_agregar_candidato_extraer = new JButton("<<");
		GridBagConstraints gbc_btn_agregar_candidato_extraer = new GridBagConstraints();
		gbc_btn_agregar_candidato_extraer.insets = new Insets(0, 0, 5, 5);
		gbc_btn_agregar_candidato_extraer.gridx = 4;
		gbc_btn_agregar_candidato_extraer.gridy = 4;
		add(btn_agregar_candidato_extraer, gbc_btn_agregar_candidato_extraer);

		txt_disponibles = new JTextField();
		txt_disponibles.setEditable(false);
		GridBagConstraints gbc_txt_sin_procesar = new GridBagConstraints();
		gbc_txt_sin_procesar.anchor = GridBagConstraints.NORTH;
		gbc_txt_sin_procesar.insets = new Insets(0, 0, 5, 5);
		gbc_txt_sin_procesar.fill = GridBagConstraints.HORIZONTAL;
		gbc_txt_sin_procesar.gridx = 0;
		gbc_txt_sin_procesar.gridy = 5;
		add(txt_disponibles, gbc_txt_sin_procesar);
		txt_disponibles.setColumns(10);

		txt_candidatos_procesar = new JTextField();
		txt_candidatos_procesar.setEditable(false);
		txt_candidatos_procesar.setColumns(10);
		GridBagConstraints gbc_txt_candidatos_procesar = new GridBagConstraints();
		gbc_txt_candidatos_procesar.insets = new Insets(0, 0, 5, 5);
		gbc_txt_candidatos_procesar.fill = GridBagConstraints.HORIZONTAL;
		gbc_txt_candidatos_procesar.gridx = 2;
		gbc_txt_candidatos_procesar.gridy = 5;
		add(txt_candidatos_procesar, gbc_txt_candidatos_procesar);

		txt_candidatos_extraer = new JTextField();
		txt_candidatos_extraer.setEditable(false);
		txt_candidatos_extraer.setColumns(10);
		GridBagConstraints gbc_txt_candidatos_extraer = new GridBagConstraints();
		gbc_txt_candidatos_extraer.insets = new Insets(0, 0, 5, 5);
		gbc_txt_candidatos_extraer.fill = GridBagConstraints.HORIZONTAL;
		gbc_txt_candidatos_extraer.gridx = 3;
		gbc_txt_candidatos_extraer.gridy = 5;
		add(txt_candidatos_extraer, gbc_txt_candidatos_extraer);

		txt_procesados = new JTextField();
		txt_procesados.setEditable(false);
		txt_procesados.setColumns(10);
		GridBagConstraints gbc_txt_procesados = new GridBagConstraints();
		gbc_txt_procesados.insets = new Insets(0, 0, 5, 0);
		gbc_txt_procesados.fill = GridBagConstraints.HORIZONTAL;
		gbc_txt_procesados.gridx = 5;
		gbc_txt_procesados.gridy = 5;
		add(txt_procesados, gbc_txt_procesados);

		pl_botones = new JPanel();
		FlowLayout flowLayout = (FlowLayout) pl_botones.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		GridBagConstraints gbc_pl_botones = new GridBagConstraints();
		gbc_pl_botones.fill = GridBagConstraints.HORIZONTAL;
		gbc_pl_botones.gridwidth = 6;
		gbc_pl_botones.gridx = 0;
		gbc_pl_botones.gridy = 6;
		add(pl_botones, gbc_pl_botones);

		btn_restablecer = new JButton("Restablecer");
		pl_botones.add(btn_restablecer);

		btn_confirmar_cambios = new JButton("Confirmar cambios");
		pl_botones.add(btn_confirmar_cambios);

		btn_analisis_datos = new JButton("Analisis Datos (BI) -->");
		pl_botones.add(btn_analisis_datos);
	}

	@Override
	public void configEventos() {

		EventoETL eventos = new EventoETL(this);

		txtDireccionFuente.getDocument().addDocumentListener(eventos);

		btn_restablecer.addActionListener(eventos);
		btn_confirmar_cambios.addActionListener(eventos);
		btn_analisis_datos.addActionListener(eventos);

		btn_agregar_candidato_procesar.addActionListener(eventos);
		btn_remover_candidato_procesar.addActionListener(eventos);

		btn_agregar_candidato_extraer.addActionListener(eventos);
		btn_remover_candidato_extraer.addActionListener(eventos);
	}

	/**
	 * el campo de texto asignado para la direccion {origen <-> destino} de los archivos .dbf tiene asociado un evento
	 * para que permitirá lanzar nuevamente la logica de negocio responsable de llenar las listas de la vista ETL.
	 * 
	 * @param evt
	 */
	public void actualizarLector() {

		procesador_archivos = new ProcesarMultipleArchivo(txtDireccionFuente.getText());
		procesador_archivos.buscarNuevosArchivos();
		actionReiniciar();
	}

	/**
	 * pide las listas del servicio CRUD de archivos dbf y completa en los componentes graficos que corresponden
	 */
	public void actionReiniciar() {

		List<ArchivoDBF> lista_diponible = procesador_archivos.getDbf_servicio_crud().getListaDisponibles();
		List<ArchivoDBF> lista_procesado = procesador_archivos.getDbf_servicio_crud().getListaProcesados();

		borrarTodasLasListas();

		agregarDisponible((ArchivoDBF[]) lista_diponible.toArray());
		agregarProcesado((ArchivoDBF[]) lista_procesado.toArray());
	}

	public void actionGuardar() {

		int indice_mayor = list_candidatos_procesar.getModel().getSize();
		if (indice_mayor > 0) {
			list_candidatos_procesar.setSelectionInterval(0, indice_mayor - 1);

			procesador_archivos.getDbf_servicio_crud().setListaCandidatosProcesar(
					list_candidatos_procesar.getSelectedValuesList());

			procesador_archivos.insertarArchivosSeleccionados();
		}

		indice_mayor = list_candidatos_extraer.getModel().getSize();
		if (indice_mayor > 0) {
			list_candidatos_extraer.setSelectionInterval(0, indice_mayor - 1);

			procesador_archivos.getDbf_servicio_crud().setListaCandidatosExtraer(
					list_candidatos_extraer.getSelectedValuesList());

			procesador_archivos.borrarArchivosSeleccionados();
		}

		actionReiniciar();
	}

	/**
	 * de la lista de archivos disponibles en la carpeta que no hayan sido agregados a la BD, recupera la sublista de
	 * los seleccionados y los pasa a la lista de candidatos para ingresar a la BD. Hasta que el usuario no confirme no
	 * se realizará la operacion.
	 * 
	 * paso 1 [.w.] [.x.] - [.y.] [.z.] ...............................................................................
	 * paso 2 [(w)] [+=w] - [.y.] [.z.] ...............................................................................
	 * paso 3 [-=w] [(w)] - [.y.] [.z.] ...............................................................................
	 * 
	 */
	public void actionAgregarCandidatoProcesar() {

		ArchivoDBF lista_seleccionados[] = (ArchivoDBF[]) list_disponibles.getSelectedValuesList().toArray();
		agregarCandidatoProcesar(lista_seleccionados);
		borrarDisponible(lista_seleccionados);
	}

	/**
	 * de la lista de archivos candidatos para agregar a la BD, recupera la sublista de los seleccionados y los devuelve
	 * a la lista de disponibles. Hasta que el usuario no confirme no se realizará la operacion.
	 * 
	 * paso 1 [.w.] [.x.] - [.y.] [.z.] ...............................................................................
	 * paso 2 [+=x] [(x)] - [.y.] [.z.] ...............................................................................
	 * paso 3 [(x)] [-=x] - [.y.] [.z.] ...............................................................................
	 * 
	 */
	public void actionRemoverCandidatoProcesar() {

		ArchivoDBF lista_seleccionados[] = (ArchivoDBF[]) list_candidatos_procesar.getSelectedValuesList().toArray();
		agregarDisponible(lista_seleccionados);
		borrarCandidatoProcesar(lista_seleccionados);
	}

	/**
	 * de la lista de archivos que fueron agregados a la BD, recupera la sublista de los seleccionados y los pasa a la
	 * lista de candidatos para ser extraidos de la BD. Hasta que el usuario no confirme no se realizará la operacion.
	 * 
	 * paso 1 [.w.] [.x.] - [.y.] [.z.] ...............................................................................
	 * paso 2 [.w.] [.x.] - [+=z] [(z)] ...............................................................................
	 * paso 3 [.w.] [.x.] - [(z)] [-=z] ...............................................................................
	 * 
	 */
	public void actionAgregarCandidatoExtraer() {

		ArchivoDBF lista_seleccionados[] = (ArchivoDBF[]) list_procesados.getSelectedValuesList().toArray();
		agregarCandidatoExtraer(lista_seleccionados);
		borrarProcesado(lista_seleccionados);
	}

	/**
	 * de la lista de archivos candidatos para extraer de la BD, recupera la sublista de los seleccionados y los
	 * devuelve a la lista de procesados. Hasta que el usuario no confirme no se realizará la operacion.
	 * 
	 * paso 1 [.w.] [.x.] - [.y.] [.z.] ...............................................................................
	 * paso 2 [.w.] [.x.] - [.y.] [+=y] ...............................................................................
	 * paso 3 [.w.] [.x.] - [-=y] [(y)] ...............................................................................
	 * 
	 */
	public void actionRemoverCandidatoExtraer() {

		ArchivoDBF lista_seleccionados[] = (ArchivoDBF[]) list_candidatos_extraer.getSelectedValuesList().toArray();

		agregarProcesado(lista_seleccionados);
		borrarCandidatoExtraer(lista_seleccionados);
	}

	private void borrarTodasLasListas() {

		model_disponibles.clear();
		model_candidatos_procesar.clear();
		model_candidatos_extraer.clear();
		model_procesados.clear();

		txt_disponibles.setText("0");
		txt_candidatos_procesar.setText("0");
		txt_candidatos_extraer.setText("0");
		txt_procesados.setText("0");
	}

	/*
	 * agregado y extraccion en lista
	 */
	public void agregarDisponible(ArchivoDBF elementos[]) {

		model_disponibles.addAll(elementos);
		txt_disponibles.setText(String.valueOf(model_disponibles.getSize()));
	}

	public void agregarCandidatoProcesar(ArchivoDBF elementos[]) {

		model_candidatos_procesar.addAll(elementos);
		txt_candidatos_procesar.setText(String.valueOf(model_candidatos_procesar.getSize()));
	}

	public void agregarCandidatoExtraer(ArchivoDBF elementos[]) {

		model_candidatos_extraer.addAll(elementos);
		txt_candidatos_extraer.setText(String.valueOf(model_candidatos_extraer.getSize()));
	}

	public void agregarProcesado(ArchivoDBF elementos[]) {

		model_procesados.addAll(elementos);
		txt_procesados.setText(String.valueOf(model_procesados.getSize()));
	}

	private void borrarDisponible(ArchivoDBF[] lista_seleccionados) {

		for (int i = lista_seleccionados.length - 1; i >= 0; --i)
			model_disponibles.removeElement(lista_seleccionados[i]);

		list_disponibles.getSelectionModel().clearSelection();
		txt_disponibles.setText(String.valueOf(model_disponibles.getSize()));
	}

	private void borrarCandidatoProcesar(ArchivoDBF[] lista_seleccionados) {

		for (int i = lista_seleccionados.length - 1; i >= 0; --i)
			model_candidatos_procesar.removeElement(lista_seleccionados[i]);

		list_candidatos_procesar.getSelectionModel().clearSelection();
		txt_candidatos_procesar.setText(String.valueOf(model_candidatos_procesar.getSize()));
	}

	private void borrarCandidatoExtraer(ArchivoDBF[] lista_seleccionados) {

		for (int i = lista_seleccionados.length - 1; i >= 0; --i)
			model_candidatos_extraer.removeElement(lista_seleccionados[i]);

		list_candidatos_extraer.getSelectionModel().clearSelection();
		txt_candidatos_extraer.setText(String.valueOf(model_candidatos_extraer.getSize()));
	}

	private void borrarProcesado(ArchivoDBF[] lista_seleccionados) {

		for (int i = lista_seleccionados.length - 1; i >= 0; --i)
			model_procesados.removeElement(lista_seleccionados[i]);

		list_procesados.getSelectionModel().clearSelection();
		txt_procesados.setText(String.valueOf(model_procesados.getSize()));
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	public JButton getBtn_agregar_candidato_procesar() {
		return btn_agregar_candidato_procesar;
	}

	public JButton getBtn_remover_candidato_procesar() {
		return btn_remover_candidato_procesar;
	}

	public JButton getBtn_remover_candidato_extraer() {
		return btn_remover_candidato_extraer;
	}

	public JButton getBtn_agregar_candidato_extraer() {
		return btn_agregar_candidato_extraer;
	}

	public JButton getBtn_guardar() {
		return btn_confirmar_cambios;
	}

	public JButton getBtn_iniciar_bi() {
		return btn_analisis_datos;
	}

	public JButton getBtn_reiniciar() {
		return btn_restablecer;
	}

	/* ............................................. */
	/* ............................................. */
	/* SET'S ....................................... */
	/* ............................................. */
}