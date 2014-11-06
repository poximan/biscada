/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package vistas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import control_general.GestorBI;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class EventoETL implements ActionListener {

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

		if (evt.getSource() == vista_etl.getBtn_iniciar_bi()) {
			GestorBI.getSingleton().mostrarVentana();
		} else
			if (evt.getSource() == vista_etl.getBtn_guardar()) {
				vista_etl.modificarListas();
			} else
				if (evt.getSource() == vista_etl.getBtn_reiniciar()) {
					vista_etl.restablecerListas();
				} else
					if (evt.getSource() == vista_etl.getBtn_agregar_candidato_extraer()) {
						vista_etl.agregarCandidatoExtraer();
					} else
						if (evt.getSource() == vista_etl.getBtn_agregar_candidato_procesar()) {
							vista_etl.agregarCandidatoProcesar();
						} else
							if (evt.getSource() == vista_etl.getBtn_remover_candidato_extraer()) {
								vista_etl.removerCandidatoExtraer();
							} else
								if (evt.getSource() == vista_etl.getBtn_remover_candidato_procesar()) {
									vista_etl.removerCandidatoProcesar();
								}
	}
}