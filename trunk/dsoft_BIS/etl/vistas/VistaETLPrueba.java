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

public class VistaETLPrueba extends JPanel implements PanelIniciable, EventoConfigurable {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static Logger log = Logger.getLogger(VistaETLPrueba.class);

	private static final long serialVersionUID = 1L;

	private ProcesarMultipleArchivo procesador_archivos;

	private JPanel pl_botones;
	private JScrollPane scrollPane_procesados;

	private JLabel lbl_disponibles;
	private JLabel lbl_procesados;
	private JLabel lblDireccionFuente;

	private ListModelOrdenada model_disponibles;
	private ListModelOrdenada model_procesados;

	private JList<ArchivoDBF> list_disponibles;
	private JList<ArchivoDBF> list_procesados;

	private JButton btn_confirmar_cambios;
	private JButton btn_analisis_datos;
	private JButton btn_restablecer;

	private JTextField txt_disponibles;
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
	public VistaETLPrueba(String direccion_lectura) {

		log.trace("set direccion por defecto para origen de datos");
		this.direccion_lectura = direccion_lectura;

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
		gridBagLayout.columnWidths = new int[] { 0, 0, 150, 150, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 50, 36, 200, 0, 20 };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0, 1.0, 0.0, 0.0 };
		setLayout(gridBagLayout);

		lblDireccionFuente = new JLabel("Direccion fuente:");
		lblDireccionFuente.setHorizontalAlignment(SwingConstants.RIGHT);

		GridBagConstraints gbc_lblDireccionFuente = new GridBagConstraints();
		gbc_lblDireccionFuente.anchor = GridBagConstraints.EAST;
		gbc_lblDireccionFuente.insets = new Insets(0, 0, 5, 5);
		gbc_lblDireccionFuente.gridx = 1;
		gbc_lblDireccionFuente.gridy = 0;
		add(lblDireccionFuente, gbc_lblDireccionFuente);

		txtDireccionFuente = new JTextField();
		GridBagConstraints gbc_txtDireccionFuente = new GridBagConstraints();
		gbc_txtDireccionFuente.gridwidth = 2;
		gbc_txtDireccionFuente.insets = new Insets(0, 0, 5, 5);
		gbc_txtDireccionFuente.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDireccionFuente.gridx = 2;
		gbc_txtDireccionFuente.gridy = 0;
		add(txtDireccionFuente, gbc_txtDireccionFuente);
		txtDireccionFuente.setColumns(10);

		txtDireccionFuente.setText(direccion_lectura);
		btnCambiar = new CompSeleccionarDireccion(txtDireccionFuente);

		GridBagConstraints gbc_btnCambiar = new GridBagConstraints();
		gbc_btnCambiar.anchor = GridBagConstraints.WEST;
		gbc_btnCambiar.insets = new Insets(0, 0, 5, 5);
		gbc_btnCambiar.gridx = 4;
		gbc_btnCambiar.gridy = 0;
		add(btnCambiar, gbc_btnCambiar);

		scrollPane_procesados = new JScrollPane((Component) null);
		GridBagConstraints gbc_scrollPane_procesados = new GridBagConstraints();
		gbc_scrollPane_procesados.gridwidth = 3;
		gbc_scrollPane_procesados.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_procesados.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_procesados.gridx = 3;
		gbc_scrollPane_procesados.gridy = 2;
		add(scrollPane_procesados, gbc_scrollPane_procesados);

		// -------------------------------------
		//
		// listas y model's
		// -------------------------------------

		model_disponibles = new ListModelOrdenada();
		list_disponibles = new JList<ArchivoDBF>(model_disponibles);

		model_procesados = new ListModelOrdenada();
		list_procesados = new JList<ArchivoDBF>(model_procesados);

		scrollPane_procesados.setViewportView(list_procesados);

		// -------------------------------------
		//
		// seccion fuente
		// -------------------------------------

		lbl_disponibles = new JLabel("Disponibles");
		lbl_procesados = new JLabel("Procesados");

		lbl_disponibles.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_disponibles.setHorizontalTextPosition(SwingConstants.LEADING);
		lbl_procesados.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_procesados.setHorizontalTextPosition(SwingConstants.LEADING);

		add(lbl_disponibles, new GridBagConstraints(0, 1, 3, 1, 0, 0, GridBagConstraints.SOUTH,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 5), 0, 0));

		GridBagConstraints gbc_lbl_procesados = new GridBagConstraints();
		gbc_lbl_procesados.gridwidth = 3;
		gbc_lbl_procesados.anchor = GridBagConstraints.SOUTH;
		gbc_lbl_procesados.fill = GridBagConstraints.HORIZONTAL;
		gbc_lbl_procesados.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_procesados.gridx = 3;
		gbc_lbl_procesados.gridy = 1;

		add(lbl_procesados, gbc_lbl_procesados);
		add(new JScrollPane(list_disponibles), new GridBagConstraints(0, 2, 3, 1, .5, 1, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0, 0));

		txt_disponibles = new JTextField();
		txt_disponibles.setEditable(false);
		GridBagConstraints gbc_txt_sin_procesar = new GridBagConstraints();
		gbc_txt_sin_procesar.anchor = GridBagConstraints.NORTH;
		gbc_txt_sin_procesar.insets = new Insets(0, 0, 5, 5);
		gbc_txt_sin_procesar.fill = GridBagConstraints.HORIZONTAL;
		gbc_txt_sin_procesar.gridx = 1;
		gbc_txt_sin_procesar.gridy = 3;
		add(txt_disponibles, gbc_txt_sin_procesar);
		txt_disponibles.setColumns(10);

		txt_procesados = new JTextField();
		txt_procesados.setEditable(false);
		txt_procesados.setColumns(10);
		GridBagConstraints gbc_txt_procesados = new GridBagConstraints();
		gbc_txt_procesados.insets = new Insets(0, 0, 5, 5);
		gbc_txt_procesados.fill = GridBagConstraints.HORIZONTAL;
		gbc_txt_procesados.gridx = 4;
		gbc_txt_procesados.gridy = 3;
		add(txt_procesados, gbc_txt_procesados);

		pl_botones = new JPanel();
		GridBagConstraints gbc_pl_botones = new GridBagConstraints();
		gbc_pl_botones.insets = new Insets(0, 0, 0, 5);
		gbc_pl_botones.fill = GridBagConstraints.HORIZONTAL;
		gbc_pl_botones.gridwidth = 2;
		gbc_pl_botones.gridx = 2;
		gbc_pl_botones.gridy = 4;
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

		EventoETLPrueba eventos = new EventoETLPrueba(this);

		btn_restablecer.addActionListener(eventos);
		btn_confirmar_cambios.addActionListener(eventos);
		btn_analisis_datos.addActionListener(eventos);

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

	/**
	 * accion ejecutada al presionar el boton guardar
	 */
	public void actionConfirmarCambios() {

		if (!list_disponibles.isSelectionEmpty())
			procesador_archivos.insertarArchivosSeleccionados(list_disponibles.getSelectedValuesList());

		if (!list_procesados.isSelectionEmpty())
			procesador_archivos.borrarArchivosSeleccionados(list_procesados.getSelectedValuesList());

		actionRestablecer();
	}

	private void borrarTodasLasListas() {

		model_disponibles.clear();
		list_disponibles.clearSelection();
		model_procesados.clear();
		list_procesados.clearSelection();

		txt_disponibles.setText("0");
		txt_procesados.setText("0");
	}

	/*
	 * agregado y extraccion en lista
	 */
	public void agregarDisponible(Object[] objects) {

		model_disponibles.addAll(objects);
		txt_disponibles.setText(String.valueOf(model_disponibles.getSize()));
	}

	public void agregarProcesado(Object[] objects) {

		model_procesados.addAll(objects);
		txt_procesados.setText(String.valueOf(model_procesados.getSize()));
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

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