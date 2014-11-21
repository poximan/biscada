/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package vistas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JList;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import modelo.ArchivoDBF;
import control_general.GestorBI;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

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
		else
			if (evt.getSource() == vista_etl.getBtn_procesar())
				vista_etl.actionProcesar();
			else
				if (evt.getSource() == vista_etl.getBtn_extraer())
					vista_etl.actionExtraer();
				else
					if (evt.getSource() == vista_etl.getBtn_restablecer())
						vista_etl.actionRestablecer();
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

	@Override
	@SuppressWarnings({ "unused", "unchecked" })
	public void valueChanged(ListSelectionEvent arg0) {

		JList<ArchivoDBF> elemento = (JList<ArchivoDBF>) arg0.getSource();

		Class<?> c;
		try {
			c = Class.forName("modelo.JListDisponible");
			Constructor<?> cons = c.getConstructor();
			Object object = cons.newInstance();
		}
		catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException excepcion) {
			excepcion.printStackTrace();
		}

		String nombre = elemento.getName();
		vista_etl.getTxt_selDisponibles().setText(String.valueOf(elemento.getSelectedValuesList().size()));
	}
}