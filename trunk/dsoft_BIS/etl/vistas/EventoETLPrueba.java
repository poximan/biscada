/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package vistas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import control_general.GestorBI;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class EventoETLPrueba implements ActionListener, DocumentListener {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private VistaETLPrueba vista_etl;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public EventoETLPrueba(VistaETLPrueba vista_etl) {
		this.vista_etl = vista_etl;
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	public void actionPerformed(ActionEvent evt) {

		if (evt.getSource() == vista_etl.getBtn_iniciar_bi()) {
			GestorBI.getSingleton().mostrarVentana();
		} else
			if (evt.getSource() == vista_etl.getBtn_guardar()) {
				vista_etl.actionConfirmarCambios();
			} else
				if (evt.getSource() == vista_etl.getBtn_reiniciar()) {
					vista_etl.actionRestablecer();
				}
	}

	@Override
	public void changedUpdate(DocumentEvent evt) {
		resolverCambioTextoDireccion(evt);
	}

	@Override
	public void removeUpdate(DocumentEvent evt) {
	}

	/**
	 * el campo de texto asignado para la direccion {origen <-> destino} de los archivos .dbf tiene asociado un evento
	 * para que permitirá lanzar nuevamente la logica de negocio responsable de llenar las listas de la vista ETL.
	 * 
	 * @param evt
	 */
	@Override
	public void insertUpdate(DocumentEvent evt) {
		resolverCambioTextoDireccion(evt);
	}

	/**
	 * el campo de texto asignado para la direccion {origen <-> destino} de los archivos .dbf tiene asociado un evento
	 * para que permitirá lanzar nuevamente la logica de negocio responsable de llenar las listas de la vista ETL.
	 * 
	 * @param evt
	 */
	public void resolverCambioTextoDireccion(DocumentEvent evt) {
		vista_etl.actionRestablecer();
	}
}