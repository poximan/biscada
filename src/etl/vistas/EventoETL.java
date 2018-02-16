/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package etl.vistas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JList;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import bi.controles.GestorBI;
import comunes.entidades.ArchivoDBF;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * Class Responsibility Collaborator (CRC) design:
 * 
 * ==== parte clase =========================
 * 
 * YO REPRESENTO, el gestor de eventos exclusivo de etl.vistas.VistaETL
 * 
 * ==== parte responsabilidad ===============
 * 
 * LO QUE HAGO, cuando sucede un evento en la vista de la que soy responsable,
 * examino sus componentes visuales en busca del que cambio de estado.
 * 
 * cuando lo encuentro, ejecuto la operacion reservada para esa situacion.
 * 
 * LO QUE CONOZCO, la vista de la que soy responsable de gestionar sus eventos.
 * 
 * ==== parte colaboracion ==================
 * 
 * MI COLABORADOR PRINCIPAL, es java.awt.event.ActionListener (y otras de la
 * jerarquia de eventos)
 * 
 * COMO INTERACTUO CON MI COLABORADOR, a traves de esta interfaz soy un gestor
 * autorizado para escuchar eventos.
 *
 * @author hdonato
 * 
 */
public class EventoETL implements ActionListener, DocumentListener, ListSelectionListener {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private VistaETL vista_etl;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public EventoETL(VistaETL vista_etl) {
		this.vista_etl = vista_etl;
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	public void actionPerformed(ActionEvent evt) {

		if (evt.getSource() == vista_etl.getBtn_analisis_datos())
			GestorBI.getSingleton().mostrarVentana();
		else if (evt.getSource() == vista_etl.getBtn_procesar())
			vista_etl.actionProcesar();
		else if (evt.getSource() == vista_etl.getBtn_extraer())
			vista_etl.actionExtraer();
	}

	@Override
	public void changedUpdate(DocumentEvent evt) {
		resolverCambioTextoDireccion(evt);
	}

	/**
	 * el campo de texto asignado para la direccion {origen - destino} de los
	 * archivos .dbf tiene asociado un evento para que permitira lanzar nuevamente
	 * la logica de negocio responsable de llenar las listas de la vista ETL.
	 * 
	 */
	@Override
	public void insertUpdate(DocumentEvent evt) {
		resolverCambioTextoDireccion(evt);
	}

	@Override
	public void removeUpdate(DocumentEvent evt) {
	}

	/**
	 * el campo de texto asignado para la direccion {origen - destino} de los
	 * archivos .dbf tiene asociado un evento que permitira lanzar nuevamente la
	 * logica de negocio responsable de llenar las listas de la vista ETL.
	 * 
	 */
	public void resolverCambioTextoDireccion(DocumentEvent evt) {
		vista_etl.actualizarListas();
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {

		@SuppressWarnings("unchecked")
		JList<ArchivoDBF> elemento = (JList<ArchivoDBF>) arg0.getSource();

		Class<?> clase_propietaria;
		Constructor<?> cons;
		Object objeto_resultante = null;

		try {
			clase_propietaria = Class.forName(elemento.getClass().getName());
			cons = clase_propietaria.getConstructor();
			objeto_resultante = cons.newInstance();

		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException excepcion) {
			excepcion.printStackTrace();
		}

		finally {
			String cantidad = String.valueOf(elemento.getSelectedValuesList().size());

			if (objeto_resultante instanceof JListDisponible)
				vista_etl.getTxt_selDisponibles().setText(cantidad);

			if (objeto_resultante instanceof JListProcesado)
				vista_etl.getTxt_selProcesados().setText(cantidad);
		}
	}
}