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
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import modelo.ArchivoDBF;

import org.apache.log4j.Logger;

import control_etl.MultipleArchivoETL;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

@SuppressWarnings({ "unchecked" })
public class VistaETL extends JPanel implements PanelIniciable, EventoConsultaConfigurable {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static Logger log = Logger.getLogger(VistaETL.class);

	private static final long serialVersionUID = 1L;
	private static final String BTN_AGREGAR = ">>";
	private static final String BTN_REMOVER = "<<";

	private MultipleArchivoETL lector;

	private JLabel lbl_candidatosExtraer;
	private JLabel lbl_disponibles;
	private JLabel lbl_procesados;
	private JLabel lbl_candidatosProcesar;

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
	private JButton btn_guardar;
	private JButton btn_iniciar_bi;
	private JButton btn_reiniciar;

	private JScrollPane scrollPane_candidatosProcesar;
	private JScrollPane scrollPane_procesados;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public VistaETL(String direccion_lectura) {

		lector = new MultipleArchivoETL(direccion_lectura);
		lector.buscarNuevosArchivos();

		log.trace("se contruyo esquema de BD y se buscaron archivos para agregar");

		iniciarComponentes();
		configEventos();
		restablecerListas();
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
		gridBagLayout.rowHeights = new int[] { 0, 200, 0, 0, 0, 200, 20 };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 0.0, 1.0, 1.0, 0.0, 1.0 };
		setLayout(gridBagLayout);

		scrollPane_candidatosProcesar = new JScrollPane((Component) null);
		GridBagConstraints gbc_scrollPane_candidatosProcesar = new GridBagConstraints();
		gbc_scrollPane_candidatosProcesar.gridheight = 5;
		gbc_scrollPane_candidatosProcesar.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_candidatosProcesar.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_candidatosProcesar.gridx = 2;
		gbc_scrollPane_candidatosProcesar.gridy = 1;
		add(scrollPane_candidatosProcesar, gbc_scrollPane_candidatosProcesar);

		scrollPane_procesados = new JScrollPane((Component) null);
		GridBagConstraints gbc_scrollPane_procesados = new GridBagConstraints();
		gbc_scrollPane_procesados.gridheight = 5;
		gbc_scrollPane_procesados.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane_procesados.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_procesados.gridx = 5;
		gbc_scrollPane_procesados.gridy = 1;
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

		add(lbl_disponibles, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 5), 0, 0));

		lbl_candidatosProcesar = new JLabel("Candidatos a procesar");
		lbl_candidatosProcesar.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lbl_candidatosProcesar = new GridBagConstraints();
		gbc_lbl_candidatosProcesar.fill = GridBagConstraints.HORIZONTAL;
		gbc_lbl_candidatosProcesar.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_candidatosProcesar.gridx = 2;
		gbc_lbl_candidatosProcesar.gridy = 0;
		add(lbl_candidatosProcesar, gbc_lbl_candidatosProcesar);

		GridBagConstraints gbc_lbl_procesados = new GridBagConstraints();
		gbc_lbl_procesados.fill = GridBagConstraints.HORIZONTAL;
		gbc_lbl_procesados.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_procesados.gridx = 5;
		gbc_lbl_procesados.gridy = 0;

		add(lbl_procesados, gbc_lbl_procesados);
		add(new JScrollPane(list_disponibles), new GridBagConstraints(0, 1, 1, 5, .5, 1, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0, 0));

		add(lbl_candidatosExtraer, new GridBagConstraints(3, 0, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 5), 0, 0));

		add(new JScrollPane(list_candidatos_extraer), new GridBagConstraints(3, 1, 1, 5, .5, 1.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0, 0));

		// -------------------------------------
		//
		// botones comando
		// -------------------------------------

		btn_agregar_candidato_procesar = new JButton(BTN_AGREGAR);
		btn_agregar_candidato_procesar.setAlignmentX(0.5f);
		add(btn_agregar_candidato_procesar, new GridBagConstraints(1, 2, 1, 1, 0, .25, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));

		btn_remover_candidato_extraer = new JButton(">>");
		GridBagConstraints gbc_btn_remover_candidato_extraer = new GridBagConstraints();
		gbc_btn_remover_candidato_extraer.insets = new Insets(0, 0, 5, 5);
		gbc_btn_remover_candidato_extraer.gridx = 4;
		gbc_btn_remover_candidato_extraer.gridy = 2;
		add(btn_remover_candidato_extraer, gbc_btn_remover_candidato_extraer);

		btn_remover_candidato_procesar = new JButton(BTN_REMOVER);
		add(btn_remover_candidato_procesar, new GridBagConstraints(1, 4, 1, 1, 0, .25, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 0, 0));

		btn_agregar_candidato_extraer = new JButton("<<");
		GridBagConstraints gbc_btn_agregar_candidato_extraer = new GridBagConstraints();
		gbc_btn_agregar_candidato_extraer.insets = new Insets(0, 0, 5, 5);
		gbc_btn_agregar_candidato_extraer.gridx = 4;
		gbc_btn_agregar_candidato_extraer.gridy = 4;
		add(btn_agregar_candidato_extraer, gbc_btn_agregar_candidato_extraer);

		pl_botones = new JPanel();
		FlowLayout flowLayout = (FlowLayout) pl_botones.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		GridBagConstraints gbc_pl_botones = new GridBagConstraints();
		gbc_pl_botones.fill = GridBagConstraints.HORIZONTAL;
		gbc_pl_botones.gridwidth = 6;
		gbc_pl_botones.gridx = 0;
		gbc_pl_botones.gridy = 6;
		add(pl_botones, gbc_pl_botones);

		btn_reiniciar = new JButton("Reiniciar");
		pl_botones.add(btn_reiniciar);

		btn_guardar = new JButton("Guardar");
		pl_botones.add(btn_guardar);

		btn_iniciar_bi = new JButton("Iniciar BI");
		pl_botones.add(btn_iniciar_bi);
	}

	@Override
	public void configEventos() {

		EventoETL eventos = new EventoETL(this);

		btn_guardar.addActionListener(eventos);
		btn_iniciar_bi.addActionListener(eventos);
		btn_reiniciar.addActionListener(eventos);
		btn_agregar_candidato_extraer.addActionListener(eventos);
		btn_agregar_candidato_procesar.addActionListener(eventos);
		btn_remover_candidato_extraer.addActionListener(eventos);
		btn_remover_candidato_procesar.addActionListener(eventos);
	}

	public void restablecerListas() {

		List<ArchivoDBF> lista_diponible = lector.getDbf_servicio_crud().getLista_nuevos();
		List<ArchivoDBF> lista_procesado = lector.getDbf_servicio_crud().getLista_anteriores();

		borrarModelDisponibles();
		borrarModelCandidatosProcesar();
		borrarModelCandidatosExtraer();
		borrarModelProcesados();

		agregarElementoDisponible(lista_diponible.toArray());
		agregarElementoProcesado(lista_procesado.toArray());
	}

	public void modificarListas() {

		int indice_mayor = list_candidatos_procesar.getModel().getSize() - 1;
		if (indice_mayor >= 0) {
			list_candidatos_procesar.setSelectionInterval(0, indice_mayor);
			lector.getDbf_servicio_crud().setLista_nuevos(list_candidatos_procesar.getSelectedValuesList());
		}

		indice_mayor = list_candidatos_extraer.getModel().getSize() - 1;
		if (indice_mayor >= 0) {
			list_candidatos_extraer.setSelectionInterval(0, indice_mayor);
			lector.getDbf_servicio_crud().setLista_borrar(list_candidatos_extraer.getSelectedValuesList());
		}

		lector.procesarArchivosEncontrados();
		restablecerListas();
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
	public void agregarCandidatoProcesar() {

		Object lista_seleccionados[] = list_disponibles.getSelectedValuesList().toArray();
		agregarElementoCandidatoProcesar(lista_seleccionados);
		borrarElementoListDisponibles();
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
	public void removerCandidatoProcesar() {

		Object selected[] = list_candidatos_extraer.getSelectedValuesList().toArray();
		agregarElementoDisponible(selected);
		borrarElementoListCandidatoProcesar();
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
	public void agregarCandidatoExtraer() {

		Object selected[] = list_procesados.getSelectedValuesList().toArray();
		agregarElementoCandidatoExtraer(selected);
		borrarElementoListProcesado();
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
	public void removerCandidatoExtraer() {

		Object selected[] = list_candidatos_extraer.getSelectedValuesList().toArray();
		agregarElementoProcesado(selected);
		borrarElementoListCandidatoExtraer();
	}

	/*
	 * agregado y extraccion en lista
	 */
	public void agregarElementoDisponible(Object elementos[]) {
		llenarListModel(model_disponibles, elementos);
	}

	public void agregarElementoCandidatoProcesar(Object elementos[]) {
		llenarListModel(model_candidatos_procesar, elementos);
	}

	public void agregarElementoCandidatoExtraer(Object elementos[]) {
		llenarListModel(model_candidatos_extraer, elementos);
	}

	public void agregarElementoProcesado(Object elementos[]) {
		llenarListModel(model_procesados, elementos);
	}

	/**
	 * agrega nuevos elementos a una lista existente
	 * 
	 * @param model_lista
	 *            de esta forma se puede utilizar el metodo en mas de una lista.
	 * @param nuevos_valores
	 *            a agregar, del tipo de la lista
	 */
	private void llenarListModel(ListModelOrdenada model_lista, Object nuevos_valores[]) {
		model_lista.addAll(nuevos_valores);
	}

	private void borrarElementoListCandidatoProcesar() {

		Object selected[] = list_candidatos_procesar.getSelectedValuesList().toArray();
		for (int i = selected.length - 1; i >= 0; --i) {
			model_candidatos_procesar.removeElement(selected[i]);
		}
		list_candidatos_procesar.getSelectionModel().clearSelection();
	}

	private void borrarElementoListDisponibles() {

		Object selected[] = list_disponibles.getSelectedValuesList().toArray();
		for (int i = selected.length - 1; i >= 0; --i) {
			model_disponibles.removeElement(selected[i]);
		}
		list_disponibles.getSelectionModel().clearSelection();
	}

	private void borrarElementoListCandidatoExtraer() {

		Object selected[] = list_candidatos_extraer.getSelectedValuesList().toArray();
		for (int i = selected.length - 1; i >= 0; --i) {
			model_candidatos_extraer.removeElement(selected[i]);
		}
		list_candidatos_extraer.getSelectionModel().clearSelection();
	}

	private void borrarElementoListProcesado() {

		Object selected[] = list_procesados.getSelectedValuesList().toArray();
		for (int i = selected.length - 1; i >= 0; --i) {
			model_procesados.removeElement(selected[i]);
		}
		list_procesados.getSelectionModel().clearSelection();
	}

	/*
	 * model
	 */
	public void borrarModelDisponibles() {
		model_candidatos_extraer.clear();
	}

	public void borrarModelCandidatosProcesar() {
		model_candidatos_procesar.clear();
	}

	public void borrarModelCandidatosExtraer() {
		model_candidatos_extraer.clear();
	}

	public void borrarModelProcesados() {
		model_procesados.clear();
	}

	/*
	 * iteradores
	 */
	public Iterator<ArchivoDBF> getIteratorCandidatos() {
		return model_candidatos_extraer.iterator();
	}

	public Iterator<ArchivoDBF> getIteratorDisponibles() {
		return model_disponibles.iterator();
	}

	public Iterator<ArchivoDBF> getIteratorProcesados() {
		return model_disponibles.iterator();
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
		return btn_guardar;
	}

	public JButton getBtn_iniciar_bi() {
		return btn_iniciar_bi;
	}

	public JButton getBtn_reiniciar() {
		return btn_reiniciar;
	}

	/* ............................................. */
	/* ............................................. */
	/* SET'S ....................................... */
	/* ............................................. */
}